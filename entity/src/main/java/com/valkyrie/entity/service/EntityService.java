package com.valkyrie.entity.service;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// import com.valkyrie.entity.config.BookFeignController;
import com.valkyrie.entity.config.TokenConfig;
import com.valkyrie.entity.model.Users;
import com.valkyrie.entity.model.UsersDTO;
import com.valkyrie.entity.repository.EntityRepository;

@Service
public class EntityService {
    private EntityRepository entityRepo;
    // private BookFeignController feign;
    private TokenConfig config;

    @Autowired
    private void setEntityRepo(EntityRepository entityRepo) {
        this.entityRepo = entityRepo;
    }
  
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    // @Autowired
    // private void setFeign(BookFeignController feign) {this.feign = feign;}

    public ResponseEntity<String> save(String token, Users entityField) {
        entityField = entityField.setId(config.getUsername(token));
        entityRepo.save(entityField);
        //repo.save(entityField.setId(token));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Saved successfully...");
    }

    public ResponseEntity<String> update(String token, Users entityField) {
        String username = config.getUsername(token);
        Users presentEntity = entityRepo.findById(username).orElse(null);

        if (presentEntity == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user is not present");
        }

        entityField = entityField.setId(username);

        if (!entityField.toString().equals(presentEntity.toString())) {
            entityRepo.save(entityField);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("update successful...");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This update is not do able");
    }

    public ResponseEntity<String> borrowBook(String token, List<String> bookIds) {
        System.out.println(bookIds);
        // bookId = new String(Base64.getDecoder().decode(bookId));
        String username = config.getUsername(token);
        Users entity = entityRepo.findById(username).orElse(null);

        if (entity == null) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entity not found..");}

        if (!entity.getBookIds().isEmpty()) {
            List<String> initialBookIds = entity.getBookIds();

            for (String id : bookIds) {initialBookIds.add(id);}

            entityRepo.save(entity.setBookIds(initialBookIds));

        } else {
            entityRepo.save(entity.setBookIds(bookIds));
        }

        return entityRepo.findById(username).orElse(null).getBookIds().isEmpty()?
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Books not updated....") : 
            ResponseEntity.status(HttpStatus.ACCEPTED).body("Borrow book successful...");

    }

//    public ResponseEntity<String> borrowBook(String token, String bookId) {
//        bookId = new String(Base64.getDecoder().decode(bookId));
//        String username = config.getUsername(token);
//        ResponseEntity<Book> book = feign.borrowBook(bookId);
//
//        if (repo.findById(username).orElse(null) == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        if (!book.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        repo.updateBookBorrow(bookId, username);
//
//        String bId = repo.getBookId(username);
//
//        return bId == null || bId.isEmpty()?
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) :
//                ResponseEntity.status(HttpStatus.ACCEPTED).body("updated successfully...");
//    }

    public ResponseEntity<UsersDTO> findMember(String id) {
        // System.out.println(id);
        id = new String(Base64.getDecoder().decode(id));
        Users field = entityRepo.findById(id).orElse(null);

        if (field == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            new UsersDTO().setId(field.getId())
                .setName(field.getName())
                .setEmail(field.getEmail())
                .setNumber(field.getNumber())
                .setBookDTOs(null)//List of book ids he borrowed
            );
    }

    public ResponseEntity<UsersDTO> findMemberByToken(String token) {
        String id = config.getUsername(token);

        Users field = entityRepo.findById(id).orElse(null);

        if (field == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
            new UsersDTO().setId(field.getId())
                .setName(field.getName())
                .setEmail(field.getEmail())
                .setNumber(field.getNumber())
                .setBookDTOs(null)//List of book ids he borrowed
        );

    }

    public ResponseEntity<String> removeMember(String id) {
        id = new String(Base64.getDecoder().decode(id));

        if (entityRepo.findById(id).orElse(null) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already deleted");
        }

        entityRepo.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("delete successfully...");
    }

    public ResponseEntity<List<UsersDTO>> findMembersBySubstring(String username) {
        username = new String(Base64.getDecoder().decode(username));
        System.out.println(username);
        List<Users> users = entityRepo.findUsersByUsername(username.toLowerCase());
        List<UsersDTO> userDTOs = new LinkedList<>();

        for (Users user : users) {
            userDTOs.add(
                new UsersDTO().setBookDTOs(null)
                    .setEmail(user.getEmail()).setId(user.getId())
                    .setName(user.getName()).setNumber(user.getNumber())
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDTOs);

    }

}