package com.valkyrie.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.valkyrie.transaction.config.BookFeignController;
import com.valkyrie.transaction.config.EntityFeignController;
import com.valkyrie.transaction.config.FineFeignController;
import com.valkyrie.transaction.config.TokenConfig;
import com.valkyrie.transaction.model.BookDTO;
import com.valkyrie.transaction.model.Fine;
import com.valkyrie.transaction.model.Transaction;
import com.valkyrie.transaction.model.TransactionDTO;
import com.valkyrie.transaction.model.UsersDTO;
import com.valkyrie.transaction.repository.TransactionRepository;

import java.util.*;

@Service
public class TransactionService {
    private TransactionRepository repo;
    @Autowired
    private void setRepo(TransactionRepository repo) {this.repo = repo;}

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    private EntityFeignController feign;
    @Autowired
    private void setFeign(EntityFeignController feign) {this.feign = feign;}

    private FineFeignController fineFeign;
    @Autowired
    private void setFineFeign(FineFeignController fineFeign) {this.fineFeign = fineFeign;}

    private BookFeignController bookFeign;
    @Autowired
    private void setBookFeign(BookFeignController bookFeign) {this.bookFeign = bookFeign;}

//    private AuthenticationFeignController authFeign;
//    @Autowired
//    private void setAuthFeign(AuthenticationFeignController authFeign) {this.authFeign = authFeign;}

    public ResponseEntity<String> save(String username, String token,Transaction transaction) {
//        String username = config.getUsername(token);

//        if (!authFeign.getUser(username).getStatusCode().equals(HttpStatusCode.valueOf(200))) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }

//        String encodedUsername = Base64.getEncoder().encodeToString(username.getBytes());
        // System.out.println(username);
        ResponseEntity<UsersDTO> response = feign.find(
            username != null? username : 
            Base64.getEncoder().encodeToString(config.getUsername(token).getBytes()));

        username = username != null? 
            new String(Base64.getDecoder().decode(username)) : 
            config.getUsername(token);

        if (!response.getStatusCode().equals(HttpStatusCode.valueOf(200)) || response.getBody() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("member not present");
        }

        String uuid = UUID.randomUUID().toString();
        transaction = transaction.setId(uuid).setBorrowerId(username).setStatus(false);
        repo.save(transaction);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("This is your transaction ID: " + transaction.getId());
    }

    public ResponseEntity<String> bookReturn(String transactionId, String lateReason) {
//        String username = config.getUsername(token);
//        bookId = new String(Base64.getDecoder().decode(bookId));
        transactionId = new String(Base64.getDecoder().decode(transactionId));
        Transaction transaction = repo.findById(transactionId).orElse(null);

        if (transaction == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("transaction with such Id not present...");
        }

        Date dueDate = new Date(System.currentTimeMillis());
        repo.updateDueDate(dueDate, transactionId);
        repo.updateStatus(true, transactionId);
//        transaction.setDueDate(dueDate);

        long days = (dueDate.getTime() -
                transaction.getReturnDate().getTime()) / (1000 * 60 * 60 * 24);

        if (days > 0) {
            String uuid = UUID.randomUUID().toString();
            Fine fine = new Fine().setMemberId(transaction.getBorrowerId())
                    .setId(uuid).setAmount(11.9 * days).setReason(lateReason).setPaidStatus(false);
            fineFeign.save(fine);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("fine given.... with Id: " + fine.getId());
        }

//        repo.deleteById(transactionId);
//
//        return repo.findById(transactionId).orElse(null) == null?
//                ResponseEntity.status(HttpStatus.OK).body("The transaction removed successfully...") :
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction not removed.....");
        return ResponseEntity.status(HttpStatus.OK).body("Successfully.. book returned");
    }


    //continue next time, controller left not done,
    // git remove first one after 50 history, entity and transaction .properties
    public ResponseEntity<List<TransactionDTO>> numberOfBooksBorrowed(String token) {
        String username = config.getUsername(token);
        List<Transaction> transactions = repo.findAllByBorrowerId(username);
        //  List<String> bookIds = transactions.stream().flatMap(t -> t.getBookIds().stream()).toList();
        List<TransactionDTO> transactionDTOs = new ArrayList<>();

        if (transactions.size() > 50) {
            repo.deleteById(transactions.getFirst().getId());
        }

        for (Transaction transaction : transactions) {
            List<BookDTO> bookDTOs = new ArrayList<>();

            for (String bookId : transaction.getBookIds()) {
                ResponseEntity<BookDTO> response = bookFeign.borrowBook(Base64.getEncoder().encodeToString(bookId.getBytes()));

                if (response == null || response.getBody() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
                }

                BookDTO bookDTO = response.getBody();
                bookDTOs.add(bookDTO);
            }

            transactionDTOs.add(
                    new TransactionDTO().setBooks(bookDTOs).setId(transaction.getId())
                            .setBorrowerId(transaction.getBorrowerId())
                            .setDueDate(transaction.getDueDate())
                            .setIssueDate(transaction.getIssueDate())
                            .setReturnDate(transaction.getReturnDate())
                            .setNumberOfDueDate(transaction.getNumberOfDueDate())
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(transactionDTOs);
    }

    public ResponseEntity<List<BookDTO>> getNonReturnedBooks(String memberId) {
        List<String> bookIdList = repo.getBooksIds(
            new String(Base64.getDecoder().decode(memberId))
        );
        System.out.println(bookIdList + " and " + new String(Base64.getDecoder().decode(memberId)));
        List<BookDTO> bookDTOs = new LinkedList<>();

        for (String bookId : bookIdList) {
            ResponseEntity<BookDTO> response = bookFeign.borrowBook(
                Base64.getEncoder().encodeToString(bookId.getBytes())
            );

            if (response == null || response.getBody() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            bookDTOs.add(response.getBody());
        }

        return ResponseEntity.status(HttpStatus.OK).body(bookDTOs);
    }
}
