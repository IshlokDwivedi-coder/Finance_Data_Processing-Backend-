package org.example.finance_backend.securityConfiguration;

import org.example.finance_backend.entity.User;
import org.example.finance_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final jwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, jwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {

        if(loginRequest.getUsername()==null || loginRequest.getPassword()==null){
            throw new RuntimeException("Username and password must be provided");
        }
        User user=userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(()->new RuntimeException("Invalid username or password"));

        if(user.getStatus()== User.Status.INACTIVE){
            throw new RuntimeException("Inactive User");
        }
        return jwtUtil.generateToken(user.getUsername(),user.getRole().name());
    }
}
