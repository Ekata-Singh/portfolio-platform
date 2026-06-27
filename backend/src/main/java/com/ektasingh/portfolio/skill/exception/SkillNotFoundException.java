package com.ektasingh.portfolio.skill.exception;

public class SkillNotFoundException extends RuntimeException {

    public SkillNotFoundException(Long id) {
        super("Skill not found with id: " + id);
    }
}