package com.example.REST.mapper;

import com.example.REST.dto.CommentListResponse;
import com.example.REST.dto.CommentResponse;
import com.example.REST.dto.UpsertCommentRequest;
import com.example.REST.model.Comment;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment requestToComment(UpsertCommentRequest upsertCommentRequest);

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, UpsertCommentRequest upsertCommentRequest);

    CommentResponse commentToResponse(Comment comment);

    List<CommentResponse> commentListToCommentResponseList(List<Comment> comments);

    default CommentListResponse commentListToCommentListResponse(List<Comment> comments) {
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToCommentResponseList(comments));

        return response;
    }
}
