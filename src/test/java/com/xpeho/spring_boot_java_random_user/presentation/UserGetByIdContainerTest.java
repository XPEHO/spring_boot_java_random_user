package com.xpeho.spring_boot_java_random_user.presentation;

import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@AutoConfigureTestRestTemplate
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.sql.init.mode=never",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        }
)
class UserGetByIdContainerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /random-users/{id} should return 200 with persisted user")
    void shouldReturnUserByIdWhenUserExists() {
        User user = new User();
        user.setGender("female");
        user.setFirstname("Jane");
        user.setLastname("Doe");
        user.setCivility("Ms");
        user.setEmail("jane.doe@example.com");
        user.setPhone("0600000000");
        user.setPicture("https://example.com/jane.jpg");
        user.setNationality("FR");

        User saved = userRepository.saveAndFlush(user);

        ResponseEntity<UserEntity> response = restTemplate.getForEntity(
            "/random-users/{id}",
                UserEntity.class,
                saved.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(saved.getId());
        assertThat(response.getBody().firstname()).isEqualTo("Jane");
        assertThat(response.getBody().nat()).isEqualTo("FR");
    }

    @Test
    @DisplayName("GET /random-users/{id} should return 404 when user does not exist")
    void shouldReturnNotFoundWhenUserDoesNotExist() {
        ResponseEntity<UserEntity> response = restTemplate.getForEntity(
            "/random-users/{id}",
                UserEntity.class,
            -1
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
