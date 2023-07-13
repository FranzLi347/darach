package io.github.franzli347.darach.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册验证码uuid
     */
    private String uuid;

    /**
     * 注册验证码
     *
     */
    private String validateCode;
}
