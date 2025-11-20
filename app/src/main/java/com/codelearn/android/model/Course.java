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

    // Learning structure
    private int totalLessons; // total number of lessons in this course
    private int completedLessons; // completed lessons count
    private String currentLesson; // current lesson file name
    private List<String> lessonSequence; // ordered list of lessons
    private String prerequisiteCourse; // required course before this one

    // Gamification
    private int learningStreak; // consecutive days of learning
    private long lastAccessDate; // timestamp of last access
    private int totalMinutesSpent; // total minutes spent on this course
    private int experiencePoints; // XP earned from this course

    // Default constructor
    public Course() {
        this.isActive = true;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.learningStreak = 0;
        this.lastAccessDate = 0;
        this.totalMinutesSpent = 0;
        this.experiencePoints = 0;
        this.totalLessons = 0;
        this.completedLessons = 0;
        this.currentLesson = "";
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

    // ==================== LEARNING STRUCTURE METHODS ====================

    public int getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
        this.updatedAt = System.currentTimeMillis();
    }

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getCurrentLesson() {
        return currentLesson;
    }

    public void setCurrentLesson(String currentLesson) {
        this.currentLesson = currentLesson;
        this.updatedAt = System.currentTimeMillis();
    }

    public List<String> getLessonSequence() {
        return lessonSequence;
    }

    public void setLessonSequence(List<String> lessonSequence) {
        this.lessonSequence = lessonSequence;
        this.totalLessons = lessonSequence != null ? lessonSequence.size() : 0;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getPrerequisiteCourse() {
        return prerequisiteCourse;
    }

    public void setPrerequisiteCourse(String prerequisiteCourse) {
        this.prerequisiteCourse = prerequisiteCourse;
        this.updatedAt = System.currentTimeMillis();
    }

    /**
     * Get progress percentage
     */
    public float getProgressPercentage() {
        if (totalLessons == 0) return 0f;
        return (float) completedLessons / totalLessons * 100f;
    }

    /**
     * Check if course is completed
     */
    public boolean isCompleted() {
        return totalLessons > 0 && completedLessons >= totalLessons;
    }

    /**
     * Mark current lesson as completed and move to next
     */
    public String completeCurrentLessonAndMoveToNext() {
        if (lessonSequence == null || lessonSequence.isEmpty()) {
            return null;
        }

        // Find current lesson index
        int currentIndex = -1;
        if (currentLesson != null && !currentLesson.isEmpty()) {
            for (int i = 0; i < lessonSequence.size(); i++) {
                if (lessonSequence.get(i).equals(currentLesson)) {
                    currentIndex = i;
                    break;
                }
            }
        }

        // Mark current lesson as completed if it exists
        if (currentIndex >= 0 && currentIndex < lessonSequence.size()) {
            completedLessons = Math.max(completedLessons, currentIndex + 1);
        }

        // Move to next lesson
        int nextIndex = currentIndex + 1;
        if (nextIndex < lessonSequence.size()) {
            String nextLesson = lessonSequence.get(nextIndex);
            setCurrentLesson(nextLesson);
            return nextLesson;
        }

        return null; // No more lessons
    }

    /**
     * Get next lesson
     */
    public String getNextLesson() {
        if (lessonSequence == null || lessonSequence.isEmpty()) {
            return null;
        }

        if (currentLesson == null || currentLesson.isEmpty()) {
            return lessonSequence.get(0); // First lesson
        }

        int currentIndex = -1;
        for (int i = 0; i < lessonSequence.size(); i++) {
            if (lessonSequence.get(i).equals(currentLesson)) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex >= 0 && currentIndex < lessonSequence.size() - 1) {
            return lessonSequence.get(currentIndex + 1);
        }

        return null; // No next lesson
    }

    /**
     * Get previous lesson
     */
    public String getPreviousLesson() {
        if (lessonSequence == null || lessonSequence.isEmpty() ||
            currentLesson == null || currentLesson.isEmpty()) {
            return null;
        }

        int currentIndex = -1;
        for (int i = 0; i < lessonSequence.size(); i++) {
            if (lessonSequence.get(i).equals(currentLesson)) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex > 0) {
            return lessonSequence.get(currentIndex - 1);
        }

        return null; // No previous lesson
    }

    /**
     * Get current lesson index
     */
    public int getCurrentLessonIndex() {
        if (lessonSequence == null || lessonSequence.isEmpty() ||
            currentLesson == null || currentLesson.isEmpty()) {
            return -1;
        }

        for (int i = 0; i < lessonSequence.size(); i++) {
            if (lessonSequence.get(i).equals(currentLesson)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Get formatted progress text
     */
    public String getProgressText() {
        return completedLessons + " dari " + totalLessons + " pelajaran";
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
                ", totalLessons=" + totalLessons +
                ", completedLessons=" + completedLessons +
                ", progress=" + getProgressPercentage() + "%" +
                '}';
    }
}