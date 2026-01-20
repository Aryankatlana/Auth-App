package com.authApplication.auth_app_backend.service;

import com.authApplication.auth_app_backend.dtos.UserDto;


public interface AuthService {

    UserDto registerUser(UserDto userDto);

}
