package com.appdeveloperblog.api.resourceserver.controller;

import com.appdeveloperblog.api.resourceserver.response.UserRest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/status/check")
    public String status(){
        return "Working...";
    }

    //Autorizamos a los usuarios de rol user o al current user que tenga el mismo id la de la pathvariable
    @PreAuthorize("hasAuthority('ROLE_user') or #id == #jwt.subject") //Control de la autorizacion antes de ejecutar el metodo
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
        //Con AuthenticationPrincipal obtengo el client autenticado en un JWT
        return "Deleted user with id" + id;
    }

    //Autorizamos a los usuarios de rol user o al current user que tenga el mismo id del objeto que se retorna
    @PostAuthorize("hasAuthority('ROLE_user') or returnObject.userId == #jwt.subject") //Control de la autorizacion despues de ejecutar el metodo
    @GetMapping("/{id}")
    public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt){
        //returnObject es el objeto que se retorna, tiene todos los atributos del mismo
        return new UserRest("Ian","Fernandez","");
    }


}
