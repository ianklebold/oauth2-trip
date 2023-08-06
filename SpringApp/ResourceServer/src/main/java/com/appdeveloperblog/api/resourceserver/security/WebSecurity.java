package com.appdeveloperblog.api.resourceserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The type Web security.
 */
@Configuration
@EnableWebSecurity //Habilitamos la seguridad de spring
@EnableMethodSecurity(securedEnabled = true) //Habilitamos los metodos de seguridad (PreAuthorize,PostAuthorize y Secured)
public class WebSecurity {


    private final KeyCloakRoleConverter keyCloakRoleConverter;

    @Autowired
    public WebSecurity(KeyCloakRoleConverter keyCloakRoleConverter) {
        this.keyCloakRoleConverter = keyCloakRoleConverter;
    }

    /**
     * Configure security filter chain.
     *
     * @param httpSecurity Es el encargado de configurar la cadena de seguridad. Es un Builder
     * @return El builder finalmente retorna la implemetnacion de la interfaz SecurityFilterChain
     * @throws Exception the exception
     */
    @Bean //El objeto que se devuelve, se lo guarda en el contexto de spring para que otros componentes lo puedan usar
    SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(keyCloakRoleConverter);

        httpSecurity.authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.GET,"/users/status/check")
                                //.hasAuthority("SCOPE_profile") Definimos el scope o autorizacion
                                //.hasAnyRole("developer","user") En caso de tener mas de un rol lo podes declarar asi
                                .hasRole("user")
                                .anyRequest().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(customizer -> customizer.jwtAuthenticationConverter(jwtAuthenticationConverter)));//Esta linea dice "Hey ahora somo un servidor de autenticacion, seguimos las reglas de oauth2 y esperamos decibir jwt tokens
                    //Le registramos un convertidor del JWT para obtener el rol del user



        return httpSecurity.build();
    }
}
