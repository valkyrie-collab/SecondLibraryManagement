package com.valkyrie.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.valkyrie.transaction.model.BookDTO;
import com.valkyrie.transaction.model.Transaction;
import com.valkyrie.transaction.model.TransactionDTO;
import com.valkyrie.transaction.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService service;
    @Autowired
    private void setService(TransactionService service) {this.service = service;}

    @PostMapping("/save-transaction")
    public ResponseEntity<String> save(@RequestParam String username,
                                       @RequestBody Transaction transaction) {return service.save(username, transaction);}

    @PostMapping("/return-book")
    public ResponseEntity<String> returnBook(@RequestParam String transactionId, @RequestParam String lateReason) {
        return service.bookReturn(transactionId, lateReason);
    }

    @GetMapping("/borrowed-book")
    public ResponseEntity<List<TransactionDTO>> getBooks(@RequestParam String token) {
        return service.numberOfBooksBorrowed(token);
    }

    @GetMapping("/find-non-returned-books")
    public ResponseEntity<List<BookDTO>> getNonReturnedBook(@RequestParam String memberId) {
        return service.getNonReturnedBooks(memberId);
    }
}
