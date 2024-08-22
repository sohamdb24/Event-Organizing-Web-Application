package com.app.eventorganizer.repository;

import com.app.eventorganizer.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Services, Long> {
}
