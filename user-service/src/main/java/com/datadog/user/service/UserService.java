package com.datadog.user.service;

import com.datadog.user.model.User;
import com.datadog.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        log.info("Creating new user with username: {}", user.getUsername());
        log.debug(
                "User details - email: {}, firstName: {}, lastName: {}",
                user.getEmail(),
                user.getFirstName(),
                user.getLastName());

        if (userRepository.existsByUsername(user.getUsername())) {
            log.warn("Username already exists: {}", user.getUsername());
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Email already exists: {}", user.getEmail());
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }

        User savedUser = userRepository.save(user);
        log.info("User created successfully with id: {}", savedUser.getId());
        return savedUser;
    }

    public Optional<User> getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            log.debug("User found: {}", user.get().getUsername());
        } else {
            log.warn("User not found with id: {}", id);
        }

        return user;
    }

    public List<User> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        log.debug("Found {} users", users.size());
        return users;
    }

    public List<User> getAllUsers(int page, int size) {
        log.info("Fetching users with pagination - page: {}, size: {}", page, size);
        List<User> users = userRepository.findAll(page, size);
        log.debug("Found {} users for page {}", users.size(), page);
        return users;
    }

    public int getTotalCount() {
        log.debug("Getting total user count");
        int count = userRepository.count();
        log.debug("Total users: {}", count);
        return count;
    }

    public User updateUser(Long id, User userDetails) {
        log.info("Updating user with id: {}", id);
        log.debug(
                "Update details - username: {}, email: {}, firstName: {}, lastName: {}",
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName());

        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            log.error("User not found for update with id: {}", id);
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        User user = existingUser.get();

        if (!user.getUsername().equals(userDetails.getUsername())
                && userRepository.existsByUsername(userDetails.getUsername())) {
            log.warn("Cannot update: username already exists: {}", userDetails.getUsername());
            throw new IllegalArgumentException("Username already exists: " + userDetails.getUsername());
        }

        if (!user.getEmail().equals(userDetails.getEmail()) && userRepository.existsByEmail(userDetails.getEmail())) {
            log.warn("Cannot update: email already exists: {}", userDetails.getEmail());
            throw new IllegalArgumentException("Email already exists: " + userDetails.getEmail());
        }

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setActive(userDetails.isActive());

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with id: {}", updatedUser.getId());
        return updatedUser;
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            log.error("User not found for deletion with id: {}", id);
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        log.info("User deleted successfully with id: {}", id);
    }

    public boolean existsById(Long id) {
        log.debug("Checking if user exists with id: {}", id);
        boolean exists = userRepository.existsById(id);
        log.debug("User exists: {}", exists);
        return exists;
    }
}
