package org.example.appsmallcrm.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.dto.UserDTO;
import org.example.appsmallcrm.entity.User;
import org.example.appsmallcrm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/list")
    public ResponseEntity<List<User>> getUsers() {
        try {
            return userService.getUsersList();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserByID(id);
    }

    @PostMapping("/users/{id}")
    public  ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}
