package io.github.franzli347.darach.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/version")
@RestController
public class VersionController {

    @GetMapping
    public String version(){
        return "1.0beta";
    }
}
