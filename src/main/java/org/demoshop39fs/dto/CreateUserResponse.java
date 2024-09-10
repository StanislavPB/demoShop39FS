package org.demoshop39fs.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String state;

}
