package com.ektasingh.portfolio.profile.repository;

import com.ektasingh.portfolio.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}