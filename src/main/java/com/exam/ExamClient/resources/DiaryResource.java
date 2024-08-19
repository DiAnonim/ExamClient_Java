package com.exam.ExamClient.resources;

import com.exam.ExamClient.model.Diary;
import com.exam.ExamClient.model.User;
import jakarta.servlet.http.HttpSession;
import com.exam.ExamClient.rest.RestDiary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/diaries")
@RequiredArgsConstructor
public class DiaryResource {
    private final RestDiary restDiary;

    // Получить страницу для создания нового дневника
    @GetMapping("/create")
    public String createDiariesPage(Model model) {
        model.addAttribute("diary", new Diary());
        return "diary/create-diary";
    }

    // Создать новый дневник
    @PostMapping
    public String createDiary(@ModelAttribute Diary diary, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            diary.setAuthor(user); // Установите ID пользователя как автора дневника
            ResponseEntity<Diary> response = restDiary.createDiary(diary);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                model.addAttribute("diary", response.getBody());
                return "diary/details"; // Путь к странице с деталями нового дневника
            } else {
                return "error/409"; // Путь к странице с ошибкой конфликта
            }
        } else {
            return "error/403"; // Путь к странице с ошибкой доступа, если пользователь не авторизован
        }
    }


    // Обновить существующий дневник
    @GetMapping("/{diaryId}/edit")
    public String editDiariesPage(@PathVariable Integer diaryId, Model model) {
        ResponseEntity<Diary> response = restDiary.getDiaryById(diaryId);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return "error/404";
        }
        model.addAttribute("diary", response.getBody());
        return "diary/edit-diary";
    }

    // Получить список дневников по автору
    @GetMapping("/author/{authorId}")
    public String getDiariesByAuthor(@PathVariable Integer authorId, Model model) {
        ResponseEntity<List<Diary>> response = restDiary.getDiariesByAuthor(authorId);
        model.addAttribute("diaries", response.getBody());
        return "diary/list-diaries";
    }

    // Найти дневники по тегу
    @GetMapping("/tags/{tag}")
    public String getDiariesByTag(@PathVariable String tag, Model model) {
        ResponseEntity<List<Diary>> response = restDiary.getDiariesByTag(tag);
        model.addAttribute("diaries", response.getBody());
        return "diary/list-diaries";
    }

    // Найти все архивированные дневники
    @GetMapping("/archive")
    public String getArchivedDiaries(Model model) {
        ResponseEntity<List<Diary>> response = restDiary.getArchivedDiaries();
        model.addAttribute("diaries", response.getBody());
        return "diary/list-diaries";
    }

    // Найти все неархивированные дневники
    @GetMapping("/active")
    public String getActiveDiaries(Model model) {
        ResponseEntity<List<Diary>> response = restDiary.getActiveDiaries();
        model.addAttribute("diaries", response.getBody());
        return "diary/list-diaries";
    }

    // Удалить дневник по идентификатору
    @DeleteMapping("/{diaryId}/delete")
    public String deleteDiary(@PathVariable Integer diaryId) {
        ResponseEntity<Void> response = restDiary.deleteDiary(diaryId);
        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return "redirect:/diaries"; // Перенаправление на список дневников после удаления
        } else {
            return "error/404"; // Путь к странице с ошибкой, если не удалось удалить
        }
    }
}
