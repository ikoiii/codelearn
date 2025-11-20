package com.codelearn.android.model;

import java.io.Serializable;

/**
 * Achievement entity for gamification system
 */
public class Achievement implements Serializable {

    private int id;
    private String title;
    private String description;
    private String category; // "PROGRESS", "STREAK", "COURSE", "MILESTONE"
    private String icon; // emoji or drawable name
    private boolean isUnlocked;
    private long unlockedAt;
    private int points; // points awarded for achievement
    private String requirement; // what user needs to do

    // Default constructor
    public Achievement() {
        this.isUnlocked = false;
        this.unlockedAt = 0;
        this.points = 0;
    }

    // Constructor with required fields
    public Achievement(int id, String title, String description, String category, String icon, int points) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.icon = icon;
        this.points = points;
    }

    // Constructor with all fields
    public Achievement(int id, String title, String description, String category, String icon, int points, String requirement) {
        this(id, title, description, category, icon, points);
        this.requirement = requirement;
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
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
        if (unlocked && this.unlockedAt == 0) {
            this.unlockedAt = System.currentTimeMillis();
        }
    }

    public long getUnlockedAt() {
        return unlockedAt;
    }

    public void setUnlockedAt(long unlockedAt) {
        this.unlockedAt = unlockedAt;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    // Helper methods
    public String getFormattedUnlockDate() {
        if (!isUnlocked) return "Tidak Terbuka";

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date(unlockedAt));
    }

    public int getRarity() {
        // Determine rarity based on points
        if (points >= 100) return 4; // Legendary
        if (points >= 50) return 3;  // Epic
        if (points >= 25) return 2;  // Rare
        if (points >= 10) return 1;  // Common
        return 0; // Basic
    }

    public String getRarityName() {
        switch (getRarity()) {
            case 4: return "Legendary";
            case 3: return "Epic";
            case 2: return "Rare";
            case 1: return "Common";
            case 0: default: return "Basic";
        }
    }

    public int getRarityColor() {
        switch (getRarity()) {
            case 4: return 0xFFFFD700; // Legendary - Gold
            case 3: return 0xFF9B59B6; // Epic - Purple
            case 2: return 0xFF2196F3; // Rare - Blue
            case 1: return 0xFF4CAF50; // Common - Green
            case 0: default: return 0xFF757575; // Basic - Gray
        }
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", points=" + points +
                ", isUnlocked=" + isUnlocked +
                '}';
    }
}