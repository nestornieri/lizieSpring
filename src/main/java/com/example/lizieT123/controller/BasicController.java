package com.example.lizieT123.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//verificar que este funcionando
@RestController
/*
Permite el scaneo por parte de spring boot, para exponer los servicios como url.
*/
@RequestMapping("/api1")       // ... localhost:8080/api1/publico
public class BasicController {

    @GetMapping("/publico")
    public String publico() 	{
        return "Bienvenidos API PUBLICO (cualquier usuario)";
    }

    @GetMapping("/publico/items")
    public String publicoItems() 	{
        return "Listado publico de items";
    }

    @GetMapping("/publico/nombres")
    public String publicoNombres() 	{
        return "Listado publico de nombres";
    }

    @GetMapping("/privado")
    public String privado() 	{
        return "Bienvenidos API PRIVADO (para usuarios basicos)";
    }

    @GetMapping("/privado/items")
    public String itemsPrivate() 	{
        return "Bienvenidos API PRIVADO (ver items privados)";
    }

    @GetMapping("/user")
    public String user() 	{
        return "Api para validar roles para usuarios";
    }

    @GetMapping("/admin")
    public String admin() 	{
        return "Api para validar roles para administradores";
    }


}
