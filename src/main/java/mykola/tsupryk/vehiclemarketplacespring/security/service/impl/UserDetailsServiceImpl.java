package mykola.tsupryk.vehiclemarketplacespring.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.entity.AppUser;
import mykola.tsupryk.vehiclemarketplacespring.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        log.info("In loadUserByUsername UserDetailsServiceImpl - user found with email: '{}'", user.getEmail());
        return UserDetailsImpl.build(user);
    }
}
