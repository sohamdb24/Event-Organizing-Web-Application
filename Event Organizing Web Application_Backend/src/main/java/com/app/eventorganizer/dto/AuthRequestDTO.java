package com.app.eventorganizer.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AuthRequestDTO {
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;


}
