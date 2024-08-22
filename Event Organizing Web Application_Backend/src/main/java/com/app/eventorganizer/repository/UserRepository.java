package com.app.eventorganizer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.eventorganizer.entity.Role;
import com.app.eventorganizer.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
    boolean existsByMobileNo(String mobileNo);
    boolean existsByGstNo(String gstNo);
	Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    Optional<User> findByUserIdAndRole(Long id, Role role);
}
