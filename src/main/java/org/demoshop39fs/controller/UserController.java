package org.demoshop39fs.controller;

import org.demoshop39fs.controller.api.UserApi;
import org.demoshop39fs.dto.UpdateUserRequest;
import org.demoshop39fs.dto.UserResponse;
import org.demoshop39fs.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @PutMapping("/updatePhotoLink")
    public ResponseEntity<Void> updatePhotoLink(@RequestParam Integer userId, @RequestParam String photoLink) {
        userService.updatePhotoLink(userId, photoLink);
        return ResponseEntity.ok().build();
    }
}
