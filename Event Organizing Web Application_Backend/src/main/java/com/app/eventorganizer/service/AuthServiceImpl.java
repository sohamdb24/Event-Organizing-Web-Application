package com.app.eventorganizer.service;

import com.app.eventorganizer.dto.AuthRequestDTO;
import com.app.eventorganizer.dto.AuthResponseDTO;
import com.app.eventorganizer.dto.UserDTO;
import com.app.eventorganizer.entity.Role;
import com.app.eventorganizer.entity.User;
import com.app.eventorganizer.exception.ResourceNotFoundException;
import com.app.eventorganizer.exception.ValidationException;
import com.app.eventorganizer.repository.RoleRepository;
import com.app.eventorganizer.repository.UserRepository;
import com.app.eventorganizer.security.JwtTokenUtil;

import java.sql.Timestamp;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ReentrantLock lock = new ReentrantLock();

    @Override
    @Transactional
    public void registerUser(UserDTO userDTO) {
        lock.lock();
        try {
            
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new ValidationException("Email already exists");
            }

            
            if (userRepository.existsByMobileNo(userDTO.getMobileNo())) {
                throw new ValidationException("Mobile number already exists");
            }

            
            if (userDTO.getGstNo() != null && userRepository.existsByGstNo(userDTO.getGstNo())) {
                throw new ValidationException("GST number already exists");
            }

            
            Role role = roleRepository.findByRoleId(userDTO.getRoleId());
            if (role == null) {
                throw new IllegalArgumentException("Invalid role provided");
            }

         
            User user = new User();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setAddress(userDTO.getAddress());
            user.setMobileNo(userDTO.getMobileNo());
            user.setGstNo(userDTO.getGstNo());
            user.setBookingCapacity(userDTO.getBookingCapacity());
            user.setSpecialization(userDTO.getSpecialization());
            user.setDateOfBirth(userDTO.getDateOfBirth());
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            user.setRole(role);

            userRepository.save(user);
        } finally {
            lock.unlock();
        }
    }

    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        lock.lock();
        try {
            User user = userRepository.findByEmail(authRequestDTO.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            if (!passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())) {
                throw new ValidationException("Invalid credentials");
            }

            String token = jwtTokenUtil.generateToken(user);
            Long roleId = user.getRoleId(); 
            Long userId	= user.getUserId();
            return new AuthResponseDTO(token, roleId, userId);
        } finally {
            lock.unlock();
        }
    }
}
