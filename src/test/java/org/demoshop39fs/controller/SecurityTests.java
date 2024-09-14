package org.demoshop39fs.controller;

import org.demoshop39fs.dto.CreateRequestUser;
import org.demoshop39fs.entity.ConfirmationCode;
import org.demoshop39fs.entity.User;
import org.demoshop39fs.exceptions.RestException;
import org.demoshop39fs.repository.ConfirmationCodeRepository;
import org.demoshop39fs.repository.UserRepository;
import org.demoshop39fs.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;

    @Autowired
    private UserService userService;


    @BeforeEach
    void setUp() {
        User testUser = new User();
        testUser.setFirstName("user1");
        testUser.setLastName("user1");
        testUser.setEmail("user1@company.com");
        testUser.setPassword("Qwerty007!");
        testUser.setRole(User.Role.ADMIN);
        testUser.setState(User.State.NOT_CONFIRMED);
        testUser.setPhotoLink("");
        User savedUser = userRepository.save(testUser);

        ConfirmationCode code = new ConfirmationCode();
        code.setCode("someConfirmationCode");
        code.setUser(savedUser);
        code.setExpiredDateTime(LocalDateTime.now().plusDays(1));
        confirmationCodeRepository.save(code);
    }

    @AfterEach
    void drop() {
        confirmationCodeRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void whenNoAuthenticationThenReturn401() throws Exception {
        mockMvc.perform(get("/api/admin/find/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenNoAuthorizeThenReturn403() throws Exception {
        mockMvc.perform(get("/api/admin/find/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user1@company.com", roles = {"ADMIN"})
    void whenAuthorizeThenReturn200() throws Exception {
        mockMvc.perform(get("/api/admin/find/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("user1"));

    }

    @Test
    void return409CheckExceptionForExistEmail() throws Exception {

        assertThrows(RestException.class, () -> {
            userService.registerUser(new CreateRequestUser("John", "User", "user1@company.com", "password", ""));
        });

    }
}
