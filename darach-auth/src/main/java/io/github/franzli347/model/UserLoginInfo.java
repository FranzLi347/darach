package io.github.franzli347.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserLoginInfo {
    private Integer id;
    private String email;
    private Set<String> roles;
    private LocalDateTime loginTime;
    private String loginIp;
    private String loginMac;
    private String loginUA;
}
