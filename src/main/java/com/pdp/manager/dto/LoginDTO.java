package com.pdp.manager.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Title: UserDTO
 * @Description:
 * @author: LIXr
 * @version: 1.0
 */
@Getter
@Setter
public class LoginDTO {
    private String username;

    private String password;

    private String rememberMe;
    
    private String code;
    
    private String oper;

	@Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rememberMe='" + rememberMe + '\'' +
                '}';
    }
}
