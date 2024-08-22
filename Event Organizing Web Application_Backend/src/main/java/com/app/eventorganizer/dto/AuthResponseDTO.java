package com.app.eventorganizer.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
	private String token;
    private Long roleId;
    private Long userId;

    
    public AuthResponseDTO() {}

    
    public AuthResponseDTO(String token, Long roleId, Long userId) {
        this.token = token;
        this.roleId = roleId;
        this.userId = userId;
    }

}
