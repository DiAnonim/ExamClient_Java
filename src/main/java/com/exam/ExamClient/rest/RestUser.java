package com.exam.ExamClient.rest;

import com.exam.ExamClient.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestUser {
    private static final ParameterizedTypeReference<List<User>> USERS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    // Получить пользователя по идентификатору
    public Optional<User> getUserById(Integer userId) {
        try {
            User user = restClient.get()
                    .uri("/users/{userId}", userId)
                    .retrieve()
                    .body(User.class);
            return Optional.of(user);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    // Получить пользователя по имени
    public Optional<User> getUserByUsername(String username) {
        try {
            User user = restClient.get()
                    .uri("/users/username/{username}", username)
                    .retrieve()
                    .body(User.class);
            return Optional.of(user);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    // Получить пользователя по email
    public Optional<User> getUserByEmail(String email) {
        try {
            User user = restClient.get()
                    .uri("/users/email/{email}", email)
                    .retrieve()
                    .body(User.class);
            return Optional.of(user);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    // Создать нового пользователя
    public ResponseEntity<User> createUser(User user) {
        try {
            User createdUser = restClient.post()
                    .uri("/users/create")
                    .body(user)
                    .retrieve()
                    .body(User.class);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (HttpClientErrorException.Conflict e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // Обновить существующего пользователя
    public ResponseEntity<User> updateUser(Integer userId, User user) {
        try {
            user.setUser_id(userId);
            User updatedUser = restClient.put()
                    .uri("/users/{userId}", userId)
                    .body(user)
                    .retrieve()
                    .body(User.class);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (HttpClientErrorException.NotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Удалить пользователя по идентификатору
    public ResponseEntity<Void> deleteUser(Integer userId) {
        try {
            restClient.delete()
                    .uri("/users/{userId}", userId)
                    .retrieve();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (HttpClientErrorException.NotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Авторизация пользователя
    public Optional<User> loginUser(String username, String password) {
        try {
            User user = restClient.get()
                    .uri("/users/login/{username}/{password}", username, password)
                    .retrieve()
                    .body(User.class);
            return Optional.of(user);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
