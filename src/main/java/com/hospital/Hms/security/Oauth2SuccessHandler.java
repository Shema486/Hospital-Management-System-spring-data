package com.hospital.Hms.security;

import com.hospital.Hms.entity.Role;
import com.hospital.Hms.entity.SystemUser;
import com.hospital.Hms.entity.UserInfo;
import com.hospital.Hms.repository.SystemUSerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final SystemUSerRepository systemUSerRepository;
    private final JwtServices jwtServices;
    private final PasswordEncoder passwordEncoder;


    public Oauth2SuccessHandler(SystemUSerRepository systemUSerRepository,
                                JwtServices jwtServices, PasswordEncoder passwordEncoder) {
        this.systemUSerRepository = systemUSerRepository;
        this.jwtServices = jwtServices;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String fullName = oAuth2User.getAttribute("name");
        String baseUsername = fullName.toLowerCase().replaceAll("[^a-z0-9]", ".");
        String uniqueUsername = generateUniqueUsername(baseUsername);

        SystemUser systemUser = systemUSerRepository.findByEmail(email)
                .orElseGet(() -> {
                    SystemUser newUser = new SystemUser();
                    newUser.setFullName(fullName);
                    newUser.setEmail(email);
                    newUser.setUsername(uniqueUsername);
                    newUser.setPassword(passwordEncoder.encode("OAUTH2-USER"));
                    newUser.setRole(Role.NURSE);
                    newUser.setIsActive(true);
                    return systemUSerRepository.save(newUser);
                });

        UserInfo userInfo = new UserInfo(systemUser);
        String token = jwtServices.generateToken(userInfo);

//        response.sendRedirect("http://localhost:3000/oauth-success?token=" + token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("""
        {
          "token": "%s",
          "username": "%s",
          "role": "%s"
        }
        """.formatted(token, systemUser.getUsername(), systemUser.getRole().name()));
    }
    private String generateUniqueUsername(String baseUsername) {
        String username = baseUsername;
        int count = 1;

        while (systemUSerRepository.existsByUsername(username)) {
            username = baseUsername + count;
            count++;
        }

        return username;
    }

}

