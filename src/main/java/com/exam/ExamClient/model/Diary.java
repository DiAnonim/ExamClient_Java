package com.exam.ExamClient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diary {
    private Integer diary_id;
    private String photoLink;
    private String title;
    private String content;
    private User author;
    private Set<String> tags;
    private List<User> collaborators;;
    private Boolean isArchive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
