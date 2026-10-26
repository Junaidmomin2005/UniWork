package com.example.UniWork.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {
    @GetMapping("/health")
    public String privateHealthCheck(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return "Privately OK";
        }
        catch (Exception e){
            return "Failed to check health";
        }
    }
}
