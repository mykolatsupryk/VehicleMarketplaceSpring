package mykola.tsupryk.vehiclemarketplacespring.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.LoginRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.request.SignupRequest;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.JwtResponse;
import mykola.tsupryk.vehiclemarketplacespring.dto.response.MessageResponse;
import mykola.tsupryk.vehiclemarketplacespring.entity.AppUser;
import mykola.tsupryk.vehiclemarketplacespring.entity.Role;
import mykola.tsupryk.vehiclemarketplacespring.entity.model.enums.AppUserRole;
import mykola.tsupryk.vehiclemarketplacespring.repository.AppUserRepository;
import mykola.tsupryk.vehiclemarketplacespring.repository.RoleRepository;
import mykola.tsupryk.vehiclemarketplacespring.security.jwt.JwtUtils;
import mykola.tsupryk.vehiclemarketplacespring.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwt);
        jwtResponse.setId(userDetails.getId());
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setEmail(userDetails.getEmail());
        jwtResponse.setRoles(roles);
        log.info("In authenticateUser AuthServiceImpl - authenticate user: '{}'", jwtResponse.getEmail());
        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        AppUser user = new AppUser();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(getAppUserRole(signupRequest))
                            .orElseThrow(() -> new RuntimeException("Error: Role_User is not found!"));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        log.info("In registerUser AuthServiceImpl - register user: '{}'", user.getEmail());
        return ResponseEntity.ok(new MessageResponse("User register successfully!"));
    }

    private AppUserRole getAppUserRole (SignupRequest signupRequest) {
        return signupRequest.getRole().equalsIgnoreCase("person") ? AppUserRole.ROLE_USER_PERSON : AppUserRole.ROLE_USER_COMPANY;
    }
}
