package com.technocrats.workboxutility.services;

import org.springframework.web.bind.annotation.*;

@RestController
public class Akshay {

    @PostMapping("/akshay/csv2pdf")
    String sayHello(@RequestBody() String body){
        return "Hello World";
    }
}
