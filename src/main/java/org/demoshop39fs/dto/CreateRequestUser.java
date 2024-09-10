package org.demoshop39fs.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.demoshop39fs.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRequestUser {
    private String firstName;
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]){8,}$")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")

    private String password;

    private String photoLink;

}
