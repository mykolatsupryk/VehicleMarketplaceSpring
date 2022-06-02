package mykola.tsupryk.vehiclemarketplacespring.security.service;

import mykola.tsupryk.vehiclemarketplacespring.dto.request.LoginRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.SignupRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface AuthService {

    public ResponseEntity<JwtResponse> authenticateUser (LoginRequest loginRequest);

    public ResponseEntity<?> registerUser (SignupRequest signupRequest);



}
