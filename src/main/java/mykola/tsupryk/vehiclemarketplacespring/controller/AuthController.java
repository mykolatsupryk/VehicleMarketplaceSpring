package mykola.tsupryk.vehiclemarketplacespring.controller;


import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.LoginRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.SignupRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.JwtResponse;
import mykola.tsupryk.vehiclemarketplacespring.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser (@RequestBody LoginRequest loginRequest) {
        log.info("In authenticateUser AuthController - user: with email '{}'", loginRequest.getEmail());
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser (@RequestBody SignupRequest signupRequest) {
        log.info("In registerUser AuthController - user: with email '{}'", signupRequest.getEmail());
        return authService.registerUser(signupRequest);
    }



}
