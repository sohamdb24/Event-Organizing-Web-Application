package com.app.eventorganizer.service;

import com.app.eventorganizer.dto.AuthRequestDTO;
import com.app.eventorganizer.dto.AuthResponseDTO;
import com.app.eventorganizer.dto.UserDTO;

public interface AuthService {

    
    void registerUser(UserDTO userDTO);

    
    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
}
