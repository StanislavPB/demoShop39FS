package org.demoshop39fs.controller;

import org.demoshop39fs.entity.ConfirmationCode;
import org.demoshop39fs.entity.User;
import org.demoshop39fs.exceptions.RestException;
import org.demoshop39fs.repository.ConfirmationCodeRepository;
import org.demoshop39fs.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PublicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationCodeRepository confirmationCodeRepository;


    @BeforeEach
    void setUp(){
        User testUser = new User();
        testUser.setFirstName("user1");
        testUser.setLastName("user1");
        testUser.setEmail("user1@company.com");
        testUser.setPassword("Qwerty007!");
        testUser.setRole(User.Role.USER);
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
    void drop(){
        confirmationCodeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testRegisterUser() throws Exception {
        String newUserJson = """
                {
                    "firstName":"John",
                    "lastName":"User",
                    "email":"john@company.com",
                    "password":"Password123!"
                }
                """;

        mockMvc.perform(post("/api/public/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("johm@company.com"));

    }

    @Test
    void testConfirmRegistration() throws Exception {
        mockMvc.perform(post("/api/public/confirm")
                .param("confirmationCode","someConfirmationCode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Регистрация успешно завершена"));
    }

    @Test
    void return400ForBadFormatEmail() throws Exception {

        String newUserJson = """
                {
                    "firstName":"John",
                    "lastName":"User",
                    "email":"badFormatEmail",
                    "password":"Password123!"
                }
                """;

        mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    void return409ForExistEmail() throws Exception {

        String newUserJson = """
                {
                    "firstName":"John",
                    "lastName":"User",
                    "email":"user1@company.com",
                    "password":"Password123!"
                }
                """;

        mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isConflict());

    }

    @Test
    void return409CheckMessageForExistEmail() throws Exception {

        String newUserJson = """
                {
                    "firstName":"John",
                    "lastName":"User",
                    "email":"user1@company.com",
                    "password":"Password123!"
                }
                """;

        mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Exception Type: RestException"));

    }

}