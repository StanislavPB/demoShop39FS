package org.demoshop39fs.service;

import org.demoshop39fs.dto.CreateRequestUser;
import org.demoshop39fs.dto.UserResponse;
import org.demoshop39fs.entity.User;
import org.demoshop39fs.mapper.UserMapper;
import org.demoshop39fs.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;


    @Test
    void testRegisterUserSuccess(){
        CreateRequestUser createRequestUser = CreateRequestUser.builder()
                .firstName("testUser")
                .lastName("testUser")
                .email("testuser@company.com")
                .password("password")
                .build();

        User user = userMapper.toEntity(createRequestUser);

        UserResponse response = userService.registerUser(createRequestUser);

        assertNotNull(response);
        assertEquals("testUser",response.getFirstName());
        assertEquals("NOT_CONFIRMED",response.getState());
        verify(userRepository, times(1)).save(user);


    }
}