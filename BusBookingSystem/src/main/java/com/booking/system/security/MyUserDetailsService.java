package com.booking.system.security;

import com.booking.system.model.User;
import com.booking.system.model.exception.UserNotFoundException;
import com.booking.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException(email);
        log.info("Logging into {}", user.getEmail());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("User")));
    }
}
