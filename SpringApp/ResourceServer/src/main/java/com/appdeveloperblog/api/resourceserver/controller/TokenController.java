package com.appdeveloperblog.api.resourceserver.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @GetMapping
    public Jwt getToken(@AuthenticationPrincipal Jwt jwt){
        //Con AuthenticationPrincipal obtengo el client autenticado en un JWT
        return jwt;
    }

}
