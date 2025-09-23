package com.valkyrie.transaction.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.valkyrie.transaction.model.UsersDTO;

@FeignClient("ENTITY")
public interface EntityFeignController {

    @GetMapping("/entity/find-entity")
    ResponseEntity<UsersDTO> find(@RequestParam String id); //encoded data is accepted; status code 200

}
