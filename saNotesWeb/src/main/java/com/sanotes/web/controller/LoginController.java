package com.sanotes.web.controller;

import com.sanotes.commons.model.user.User;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.JwtAuthenticationResponse;
import com.sanotes.web.payload.LoginRequest;
import com.sanotes.web.payload.SignUpRequest;
import com.sanotes.web.security.JwtTokenProvider;
import com.sanotes.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "login", description = "the Login API")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Login", description = "", tags = {"login"})
    @PostMapping(value = "/login", consumes = {"application/json", "application/xml"})
    public ResponseEntity<JwtAuthenticationResponse> loginUser(
            @Parameter(description = "Username or Email and Password", required = true)
            @Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));
    }

    @Operation(summary = "Sign up", description = "", tags = {"login"})
    @PostMapping(value = "/signup", consumes = {"application/json", "application/xml"})
    public ResponseEntity<ApiResponse> signupUser(
            @Parameter(description = "User parameters", required = true)
            @Valid @RequestBody SignUpRequest signUpRequest) {
        String firstName = signUpRequest.getFirstname();
        String lastName = signUpRequest.getLastname();
        String username = signUpRequest.getUsername();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        User user = new User(firstName, lastName, username, password, email);

        userService.addUser(user);
        return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, "User signed up successfully."));
    }

}
