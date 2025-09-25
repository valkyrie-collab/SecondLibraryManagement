package com.valkyrie.entity.config;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("BOOK")
public interface BookFeignController {
    
}
