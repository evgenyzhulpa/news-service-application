package com.example.REST.mapper;

import com.example.REST.dto.UpsertUserRequest;
import com.example.REST.dto.UserListResponse;
import com.example.REST.dto.UserResponse;
import com.example.REST.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class, CommentMapper.class})
public interface UserMapper {
    User requestToUser(UpsertUserRequest upsertUserRequest);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest upsertUserRequest);

    UserResponse userToResponse(User user);

    default UserListResponse userListToUserListResponse(List<User> userList) {
        UserListResponse response = new UserListResponse();
        response.setUsers(userList.stream()
                .map(user -> userToResponse(user))
                .toList());

        return response;
    }
}
