package com.example.REST.service.impl;

import com.example.REST.dto.UserFilter;
import com.example.REST.exception.EntityNotFoundException;
import com.example.REST.model.User;
import com.example.REST.repository.UserRepository;
import com.example.REST.service.UserService;
import com.example.REST.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<User> findAll(UserFilter filter) {
        return userRepository.findAll(
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(
                                "Пользователь с id {0} не найден!", id
                        )));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User existedUser = findById(user.getId());
        BeanUtils.copyNonNullProperties(user, existedUser);
        return userRepository.save(existedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
