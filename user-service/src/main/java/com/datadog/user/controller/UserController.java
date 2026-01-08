package com.datadog.user.controller;

import com.datadog.user.model.User;
import com.datadog.user.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("REST request to create user: {}", user.getUsername());
        try {
            User createdUser = userService.createUser(user);
            log.info("REST response - user created with id: {}", createdUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            log.error("REST error creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("REST request to get user by id: {}", id);
        return userService
                .getUserById(id)
                .map(user -> {
                    log.info("REST response - user found: {}", user.getUsername());
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> {
                    log.warn("REST response - user not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        log.info("REST request to get all users - page: {}, size: {}", page, size);

        List<User> users;
        if (page != null && size != null) {
            users = userService.getAllUsers(page, size);
            log.info("REST response - returning {} users (paginated)", users.size());
        } else {
            users = userService.getAllUsers();
            log.info("REST response - returning {} users", users.size());
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getUserCount() {
        log.info("REST request to get user count");
        int count = userService.getTotalCount();
        log.info("REST response - total users: {}", count);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        log.info("REST request to update user with id: {}", id);
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            log.info("REST response - user updated successfully: {}", updatedUser.getId());
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            log.error("REST error updating user: {}", e.getMessage());
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("REST request to delete user with id: {}", id);
        try {
            userService.deleteUser(id);
            log.info("REST response - user deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("REST error deleting user: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
