package com.codelearn.android.model;

import java.io.Serializable;
import java.util.List;

/**
 * Course entity representing a programming course
 * Contains course information and offline content
 */
public class Course implements Serializable {

    private int id;

    private String title;
    private String description;
    private String category; // "HTML", "CSS", "JavaScript"
    private int difficulty; // 1-5 scale
    private int estimatedTime; // in minutes
    private String thumbnail;
    private boolean isActive;
    private long createdAt;
    private long updatedAt;

    // Offline content
    private List<String> lessonFiles;
    private String firstLessonAsset;
    private boolean hasOfflineContent;

    // Default constructor
    public Course() {
        this.isActive = true;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    // Constructor with required fields
    public Course(String title, String description, String category, int difficulty, int estimatedTime) {
        this();
        this.title = title;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.estimatedTime = estimatedTime;
    }

    // Constructor with all fields including thumbnail
    public Course(String title, String description, String category, int difficulty, int estimatedTime, String thumbnail) {
        this();
        this.title = title;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.estimatedTime = estimatedTime;
        this.thumbnail = thumbnail;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.updatedAt = System.currentTimeMillis();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        this.updatedAt = System.currentTimeMillis();
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        this.updatedAt = System.currentTimeMillis();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        this.updatedAt = System.currentTimeMillis();
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Get difficulty level as string
     * @return difficulty level string
     */
    public String getDifficultyLevel() {
        switch (difficulty) {
            case 1:
                return "Beginner";
            case 2:
                return "Easy";
            case 3:
                return "Intermediate";
            case 4:
                return "Advanced";
            case 5:
                return "Expert";
            default:
                return "Unknown";
        }
    }

    /**
     * Get formatted estimated time
     * @return formatted time string
     */
    public String getFormattedTime() {
        if (estimatedTime < 60) {
            return estimatedTime + " min";
        } else {
            int hours = estimatedTime / 60;
            int minutes = estimatedTime % 60;
            if (minutes == 0) {
                return hours + " hour" + (hours > 1 ? "s" : "");
            } else {
                return hours + " hour" + (hours > 1 ? "s" : "") + " " + minutes + " min";
            }
        }
    }

    // ==================== OFFLINE CONTENT METHODS ====================

    public List<String> getLessonFiles() {
        return lessonFiles;
    }

    public void setLessonFiles(List<String> lessonFiles) {
        this.lessonFiles = lessonFiles;
    }

    public String getFirstLessonAsset() {
        return firstLessonAsset;
    }

    public void setFirstLessonAsset(String firstLessonAsset) {
        this.firstLessonAsset = firstLessonAsset;
    }

    public boolean hasOfflineContent() {
        return hasOfflineContent;
    }

    public void setHasOfflineContent(boolean hasOfflineContent) {
        this.hasOfflineContent = hasOfflineContent;
    }

    /**
     * Get asset path for offline content
     */
    public String getOfflineAssetPath(String fileName) {
        if (category != null) {
            String categoryPath = category.toLowerCase();
            return "content/" + categoryPath + "/" + fileName;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", difficulty=" + difficulty +
                ", estimatedTime=" + estimatedTime +
                ", hasOfflineContent=" + hasOfflineContent +
                '}';
    }
}