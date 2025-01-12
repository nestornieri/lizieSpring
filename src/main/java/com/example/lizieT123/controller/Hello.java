package com.example.lizieT123.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//verificar que este funcionando
@RestController
/*
Permite el scaneo por parte de spring boot, para exponer los servicios como url.
*/
public class Hello {

    @GetMapping("/hola")
    public String saludar() 	{
        return "Bienvenidos API rest";
    }


}
