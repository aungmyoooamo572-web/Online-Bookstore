package org.example.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public record RegisterDto(String username,String password,String email){}

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(authService.existsByUserNameOrEmail(registerDto.username)){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already registered.");
        }
        if(authService.existsByUserNameOrEmail(registerDto.email)){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already registered.");
        }
        authService
                .register(registerDto.username,
                        registerDto.email,
                        registerDto.password);
        return ResponseEntity.ok("User registered successfully");
    }
    public record LoginDto(String usernameEmail,String password){}
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        return ResponseEntity
                .ok(authService.login(loginDto.usernameEmail,
                        loginDto.password));
    }
}
