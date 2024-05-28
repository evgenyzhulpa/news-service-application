package com.example.REST.controller;

import com.example.REST.dto.UpsertUserRequest;
import com.example.REST.dto.UserFilter;
import com.example.REST.dto.UserListResponse;
import com.example.REST.dto.UserResponse;
import com.example.REST.mapper.UserMapper;
import com.example.REST.model.User;
import com.example.REST.repository.UserRepository;
import com.example.REST.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll(UserFilter filter) {
        return ResponseEntity.ok(
                userMapper.userListToUserListResponse(
                        userService.findAll(filter)
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                userMapper.userToResponse(
                        userService.findById(id)
                ));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertUserRequest request) {
        User user = userService.update(userMapper.requestToUser(id, request));
        return ResponseEntity.ok(userMapper.userToResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
