package com.app.eventorganizer.repository;

import com.app.eventorganizer.entity.Booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {  
	List<Booking> findByUserUserId(Long userId);
}
