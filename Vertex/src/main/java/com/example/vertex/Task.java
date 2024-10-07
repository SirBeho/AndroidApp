package com.example.vertex;

import java.time.LocalDateTime;

public class Task {

    
    private Long id;
    private String title;
    private String description;
    private String status; 
    private LocalDateTime created;
    private Long projet_id;
    private Long user_id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int progress; 

    public Task(Long id, String title, String description, String status, LocalDateTime created, Long projet_id, Long user_id, LocalDateTime startTime, LocalDateTime endTime , int progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.created = created;
        this.projet_id = projet_id;
        this.user_id = user_id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.progress = progress;
    }

    public String toJson() {
        return String.format("{\"id\": \"%s\", \"title\": \"%s\", \"description\": \"%s\", \"status\": \"%s\", \"created\": \"%s\", \"projet_id\": \"%s\", \"user_id\": \"%s\", \"startTime\": \"%s\", \"endTime\": \"%s\", \"progress\": \"%s\"}", id, title, description, status, created, projet_id, user_id, startTime, endTime, progress);
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }   

    public LocalDateTime getCreated() {
        return created;
    }   

    public Long getProjet_id() {
        return projet_id;
    }   

    public Long getUser_id() {
        return user_id;
    }   

    public LocalDateTime getStartTime() {
        return startTime;
    }   

    public LocalDateTime getEndTime() {
        return endTime;
    }   

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setTitle(String title) {
        this.title = title;
    }   

    public void setDescription(String description) {
        this.description = description;
    }   

    public void setStatus(String status) {
        this.status = status;
    }   

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }   

    public void setProjet_id(Long projet_id) {
        this.projet_id = projet_id;
    }   

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }   

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }   

        
    
}



