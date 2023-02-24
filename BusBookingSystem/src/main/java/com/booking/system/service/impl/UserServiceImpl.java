package com.booking.system.service.impl;

import com.booking.system.model.User;
import com.booking.system.model.dto.UserDTO;
import com.booking.system.model.exception.UserAlreadyExistsException;
import com.booking.system.model.exception.UserNotFoundException;
import com.booking.system.repository.UserRepository;
import com.booking.system.service.api.UserService;
import com.booking.system.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {
        log.info("Registering new User with email {}", userDTO.getEmail());

        if (repository.existsByEmail(userDTO.getEmail()))
            throw new UserAlreadyExistsException(userDTO.getEmail());

        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = repository.insert(user);

        log.info("User with email {} successfully created", userDTO.getEmail());
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserDTO getUser(String email) {
        log.info("Getting User with email {}", email);
        User user = repository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException(email);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Getting all Users");
        return repository
                .findAll()
                .stream()
                .map(UserMapper.INSTANCE::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        log.info("Updating User with email {}", userDTO.getEmail());
        User persistedUser = repository.findByEmail(userDTO.getEmail());

        if (persistedUser == null)
            throw new UserNotFoundException(userDTO.getEmail());

        persistedUser = UserMapper.INSTANCE.populateUserWithPresentUserDtoFields(persistedUser, userDTO);
        persistedUser.setPassword(bCryptPasswordEncoder.encode(persistedUser.getPassword()));
        User storedUser = repository.save(persistedUser);

        log.info("User with email {} successfully updated", storedUser.getEmail());
        return UserMapper.INSTANCE.userToUserDTO(persistedUser);
    }

    @Override
    public void deleteUser(String email) {
        log.info("Deleting the User with email {}", email);
        repository.deleteByEmail(email);
    }
}
