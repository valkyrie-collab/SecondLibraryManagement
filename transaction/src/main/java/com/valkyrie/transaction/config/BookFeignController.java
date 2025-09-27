package com.valkyrie.transaction.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.valkyrie.transaction.model.BookDTO;

@FeignClient("BOOK")
public interface BookFeignController {

    @GetMapping("/book/borrow-book")
    public ResponseEntity<BookDTO> borrowBook(@RequestParam String bookId);

}
