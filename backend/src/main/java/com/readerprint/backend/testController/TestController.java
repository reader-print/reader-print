package com.readerprint.backend.testController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000") // cors 임시 설정
    public String test(){
        System.out.println("test");
        return "Hello from Backend \uD83E\uDD16";
    }
}
