package com.valkyrie.entity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valkyrie.entity.model.Users;
import com.valkyrie.entity.model.UsersDTO;
import com.valkyrie.entity.service.EntityService;

@RestController
@RequestMapping("/entity")
public class EntityController {
    private EntityService entityService;

    @Autowired
    private void setEntityService(EntityService entityService) {
        this.entityService = entityService;
    }

    @PostMapping("/save-entity")
    public ResponseEntity<String> save(@RequestParam String token,
                                       @RequestBody Users entityField) {return entityService.save(token, entityField);}

    @PostMapping("/update-entity")
    public ResponseEntity<String> update(@RequestParam String token,
                                         @RequestBody Users entityField) {return entityService.update(token, entityField);}

    @GetMapping("/find-entity")
    public ResponseEntity<UsersDTO> find(@RequestParam String id) {return entityService.findMember(id);}

    @GetMapping("/find-entity-for-search") 
    public ResponseEntity<UsersDTO> findForSearch(@RequestParam String token) {
        return entityService.findMemberByToken(token);
    }

    @DeleteMapping("/remove-member")
    public ResponseEntity<String> remove(@RequestParam String id) {return entityService.removeMember(id);}
    
    @PostMapping("/borrow-book")
    public ResponseEntity<String> borrow(@RequestParam String token, @RequestBody List<String> bookIds) {
        return entityService.borrowBook(token, bookIds);
    }

    @GetMapping("/find-by-username")
    public ResponseEntity<List<UsersDTO>> findById(@RequestParam String id) {
        return entityService.findMembersBySubstring(id);
    }

}