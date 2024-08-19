package com.exam.ExamClient.rest;

import com.exam.ExamClient.model.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class RestDiary {
    private static final ParameterizedTypeReference<List<Diary>> DIARIES_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    // Получить дневник по идентификатору
    public ResponseEntity<Diary> getDiaryById(Integer diaryId) {
        return restClient.get()
                .uri("/diaries/{diaryId}", diaryId)
                .retrieve()
                .toEntity(Diary.class); // Возвращаем ResponseEntity
    }

    // Получить все дневники по автору
    public ResponseEntity<List<Diary>> getDiariesByAuthor(Integer authorId) {
        return restClient.get()
                .uri("/diaries/author/{authorId}", authorId)
                .retrieve()
                .toEntity(DIARIES_TYPE_REFERENCE); // Возвращаем ResponseEntity
    }

    // Найти дневники по тегу
    public ResponseEntity<List<Diary>> getDiariesByTag(String tag) {
        return restClient.get()
                .uri("/diaries/tags/{tag}", tag)
                .retrieve()
                .toEntity(DIARIES_TYPE_REFERENCE); // Возвращаем ResponseEntity
    }

    // Найти все архивированные дневники
    public ResponseEntity<List<Diary>> getArchivedDiaries() {
        return restClient.get()
                .uri("/diaries/archive")
                .retrieve()
                .toEntity(DIARIES_TYPE_REFERENCE); // Возвращаем ResponseEntity
    }

    // Найти все неархивированные дневники
    public ResponseEntity<List<Diary>> getActiveDiaries() {
        return restClient.get()
                .uri("/diaries/active")
                .retrieve()
                .toEntity(DIARIES_TYPE_REFERENCE); // Возвращаем ResponseEntity
    }

    // Создать новый дневник
    public ResponseEntity<Diary> createDiary(Diary diary) {
        return restClient.post()
                .uri("/diaries")
                .body(diary)
                .retrieve()
                .toEntity(Diary.class); // Возвращаем ResponseEntity
    }

    // Обновить существующий дневник
    public ResponseEntity<Diary> updateDiary(Integer diaryId, Diary diary) {
        return restClient.put()
                .uri("/diaries/{diaryId}", diaryId)
                .body(diary)
                .retrieve()
                .toEntity(Diary.class); // Возвращаем ResponseEntity
    }

    // Удалить дневник по идентификатору
    public ResponseEntity<Void> deleteDiary(Integer diaryId) {
        return restClient.delete()
                .uri("/diaries/{diaryId}", diaryId)
                .retrieve()
                .toEntity(Void.class); // Возвращаем ResponseEntity<Void>
    }
}
