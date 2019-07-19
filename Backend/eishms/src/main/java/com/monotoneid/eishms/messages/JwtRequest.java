package com.monotoneid.eishms.messages;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class JwtRequest {
    @NotBlank
    @Size(min=3, max = 60)
    private String username;
 
    @NotBlank
    @Size(min = 4, max = 250)
    private String password;
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
}