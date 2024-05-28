package com.example.REST.service.impl;

import com.example.REST.dto.UserFilter;
import com.example.REST.model.User;
import com.example.REST.repository.UserRepository;
import com.example.REST.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private final UserRepository userRepository =
            Mockito.mock(UserRepository.class);
    private final UserService userService = new UserServiceImpl(userRepository);
    private User user;

    @BeforeEach
    public void setUp() {
        Long userId = 1l;

        user = new User();
        user.setId(userId);
        user.setName("Test user");
        user.setComments(List.of());
        user.setNews(List.of());
    }

    @Test
    @DisplayName("Test findAll method")
    public void testFindAll() {
        UserFilter userFilter = new UserFilter();
        userFilter.setPageNumber(1);
        userFilter.setPageSize(10);
        Pageable pageable = PageRequest.of(userFilter.getPageNumber(), userFilter.getPageSize());

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(users));
        Collection<User> usersFromService = userService.findAll(userFilter);

        assertEquals(users.size(), usersFromService.size());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findById method")
    public void testFindById() {
        Long userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User userFromService = userService.findById(userId);
        assertEquals(userId, userFromService.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Test save method")
    public void testSave() {
        userService.save(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Test update method")
    public void testUpdate() {
        Long userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.update(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Test delete by id method")
    public void testDeleteById() {
        userService.deleteById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

}
