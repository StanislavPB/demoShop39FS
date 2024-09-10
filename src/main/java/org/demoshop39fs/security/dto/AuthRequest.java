package org.demoshop39fs.security.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String userName;
    private String password;
}
