package com.authApplication.auth_app_backend.controller;

import com.authApplication.auth_app_backend.Security.JwtService;
import com.authApplication.auth_app_backend.dtos.LoginRequest;
import com.authApplication.auth_app_backend.dtos.TokenResponse;
import com.authApplication.auth_app_backend.dtos.UserDto;
import com.authApplication.auth_app_backend.entities.User;
import com.authApplication.auth_app_backend.repo.UserRepository;
import com.authApplication.auth_app_backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager ;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loggin(@RequestBody LoginRequest loginRequest){

        //authenticate user
        Authentication authentication = authenticate(loginRequest);
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new BadCredentialsException("Invaild Username or Password"));
        if(!user.isEnable()){
            throw new DisabledException("User is disabled");
        }

        //Generate Token
        String accessToken = jwtService.generateAccessToken(user);
        TokenResponse tokenResponse = TokenResponse.of(accessToken,"",jwtService.getAccessTtlSeconds(), modelMapper.map(user,UserDto.class));
        return ResponseEntity.ok(tokenResponse);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        try{
           return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(),loginRequest.password()));

        }catch (Exception e){
            throw new BadCredentialsException("Invalid username or Password");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(userDto));
    }
}
