package com.booking.system.security.userdetails;

import com.booking.system.model.User;
import com.booking.system.model.exception.UserNotFoundException;
import com.booking.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        return new CustomUserDetails.Builder()
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withAuthorities(Collections.singletonList(new SimpleGrantedAuthority("User")))
                .build();
    }
}
