package com.example.REST.controller;

import com.example.REST.dto.*;
import com.example.REST.mapper.UserMapper;
import com.example.REST.model.Comment;
import com.example.REST.model.News;
import com.example.REST.model.User;
import com.example.REST.rest.AbstractTestController;
import com.example.REST.rest.StringTestUtils;
import com.example.REST.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractTestController {

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void whenFindAll_thenReturnAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(createUser(1l, null, null));
        News news = createNews(1l, null, null);
        Comment comment = createComment(1l, null, null);
        users.add(createUser(2l, news, comment));

        List<UserResponse> userResponses = new ArrayList<>();
        userResponses.add(createUserResponse(1l, null, null));
        NewsResponse newsResponse = createNewsResponse(1l, null);
        CommentResponse commentResponse = createCommentResponse(1l);
        userResponses.add(createUserResponse(2l, newsResponse, commentResponse));
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.setUsers(userResponses);
        UserFilter userFilter = new UserFilter();

        when(userService.findAll(userFilter)).thenReturn(users);
        when(userMapper.userListToUserListResponse(users)).thenReturn(userListResponse);

        String actualResponse = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_users_response.json");
        verify(userService, times(1)).findAll(userFilter);
        verify(userMapper, times(1)).userListToUserListResponse(users);
        assertJsonEquals(actualResponse, expectedResponse);
    }
}
