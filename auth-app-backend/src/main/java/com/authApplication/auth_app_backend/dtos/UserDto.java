package com.authApplication.auth_app_backend.dtos;

import com.authApplication.auth_app_backend.entities.Provider;
import com.authApplication.auth_app_backend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

//What is DTO in a Spring Project?
//
//DTO (Data Transfer Object) is a simple Java class used to transfer data between different layers of a Spring application (Controller ↔ Service ↔ Client).
//
//Its main goal is to carry only the required data, not the entire entity.
public class UserDto {

    private UUID id;
    private String email;
    private String name;
    private String password;
    private String image;
    private boolean enable = true;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private Provider provider = Provider.LOCAL;
    private Set<Role> roles = new HashSet<>();

}
