package io.github.franzli347.darach.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Franz
 */
@RequestMapping("/ver")
@RestController
public class VersionController {

    @Value("${version}")
    String version;

    @RequestMapping
    public String version(){
        return version;
    }
}
