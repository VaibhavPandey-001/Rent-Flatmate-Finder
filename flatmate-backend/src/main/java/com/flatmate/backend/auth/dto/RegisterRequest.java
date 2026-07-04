package com.flatmate.backend.auth.dto;

import com.flatmate.backend.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    private Role role;
}