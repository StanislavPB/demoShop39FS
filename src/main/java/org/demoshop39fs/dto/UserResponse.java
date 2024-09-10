package org.demoshop39fs.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.demoshop39fs.entity.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String state;
    private String photoLink;

//    // Метод маппинга
//
//    public static UserResponse fromUser(User user){
//        return UserResponse.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .email(user.getEmail())
//                .role(user.getRole().toString())
//                .state(user.getState().toString())
//                .photoLink(user.getPhotoLink())
//                .build();
//    }
}
