package com.authApplication.auth_app_backend.Security;

import com.authApplication.auth_app_backend.entities.User;
import com.authApplication.auth_app_backend.exceptions.ResourceNotFoundException;
import com.authApplication.auth_app_backend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //chatGpt
        return userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new BadCredentialsException("User not found with email: " + username));

//        return userRepository
//                .findByEmail(username)
//                .orElseThrow(() -> new BadCredentialsException("Invaild email or Password !!"));
    }
}
