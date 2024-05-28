package com.example.REST.mapper;

import com.example.REST.dto.UpsertCommentRequest;
import com.example.REST.model.Comment;
import com.example.REST.service.NewsService;
import com.example.REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest upsertCommentRequest) {
        Comment comment = new Comment();
        comment.setDescription(upsertCommentRequest.getDescription());
        comment.setUser(userService.findById(upsertCommentRequest.getUserId()));
        comment.setNews(newsService.findById(upsertCommentRequest.getNewsId()));

        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, UpsertCommentRequest upsertCommentRequest) {
        Comment comment = requestToComment(upsertCommentRequest);
        comment.setId(commentId);

        return comment;
    }
}
