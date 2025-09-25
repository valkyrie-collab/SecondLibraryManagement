package com.valkyrie.transaction.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.valkyrie.transaction.model.Fine;

@FeignClient("FINE")
public interface FineFeignController {

    @PostMapping("/fine/add-fine")
    ResponseEntity<String> save(@RequestBody Fine fine);
    
}
