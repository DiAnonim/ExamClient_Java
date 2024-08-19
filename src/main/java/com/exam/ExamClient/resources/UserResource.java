package com.exam.ExamClient.resources;

import com.exam.ExamClient.model.User;
import com.exam.ExamClient.rest.RestUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

    private final RestUser restUser;

    // Получить страницу с информацией о пользователе по ID
    @GetMapping("/{userId}")
    public String getUserById(@PathVariable Integer userId, Model model) {
        User user = restUser.getUserById(userId).orElse(null);
        if (user == null) {
            return "error/404";
        }
        model.addAttribute("user", user);
        return "user/details";
    }

    // Показать форму для логина
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    // Авторизация пользователя
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Optional<User> user = restUser.loginUser(username, password);
        if (user.isPresent()) {
            session.setAttribute("user", user.get()); // Сохранение пользователя в сессии
            return "redirect:/users/details"; // Перенаправление на страницу с деталями пользователя
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "user/login";
        }
    }

    // Получить страницу для создания нового пользователя
    @GetMapping("/create")
    public String createUserPage(Model model) {
        model.addAttribute("user", new User());
        return "user/create-user";
    }

    // Создать нового пользователя
    @PostMapping
    public String createUser(@ModelAttribute User user, HttpSession session, Model model) {
        ResponseEntity<User> response = restUser.createUser(user);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            User createdUser = response.getBody();
            if (createdUser != null) {
                model.addAttribute("user", createdUser);
                session.setAttribute("user", createdUser); // Установка пользователя в сессию
                return "user/details";
            }
        }
        model.addAttribute("error", "User creation failed");
        return "error/409";
    }

    // Получить страницу для обновления пользователя по идентификатору
    @GetMapping("/{userId}/edit")
    public String editUserPage(@PathVariable Integer userId, Model model) {
        User user = restUser.getUserById(userId).orElse(null);
        if (user == null) {
            return "error/404";
        }
        model.addAttribute("user", user);
        return "user/edit-user";
    }

    // Обновить существующего пользователя
    @PutMapping("/{userId}")
    public String updateUser(@PathVariable Integer userId, @ModelAttribute User user, Model model) {
        ResponseEntity<User> response = restUser.updateUser(userId, user);
        if (response.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("user", response.getBody());
            return "user/details";
        } else {
            return "error/404";
        }
    }

    // Удалить пользователя по идентификатору
    @DeleteMapping("/{userId}/delete")
    public String deleteUser(@PathVariable Integer userId) {
        ResponseEntity<Void> response = restUser.deleteUser(userId);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return "redirect:/users/create";
        } else {
            return "error/404";
        }
    }
}
