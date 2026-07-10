package com.ektasingh.portfolio.achievement.exception;

public class AchievementNotFoundException extends RuntimeException {

    public AchievementNotFoundException(Long id) {
        super("Achievement not found with id: " + id);
    }
}