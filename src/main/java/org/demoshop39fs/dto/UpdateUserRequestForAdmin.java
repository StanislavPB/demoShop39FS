package org.demoshop39fs.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.demoshop39fs.entity.ConfirmationCode;
import org.demoshop39fs.entity.User;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestForAdmin {
    @NotBlank
    private Integer id;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]){8,}$")
    private String password;

    private String photoLink;

    @Size(min = 2, max = 20)
    private String firstName;

    @Size(min = 3, max = 25)
    private String lastName;

    private String role;

    private String state;

}
