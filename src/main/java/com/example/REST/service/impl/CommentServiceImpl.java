package com.example.REST.service.impl;

import com.example.REST.aop.CheckingCommentEditingByUser;
import com.example.REST.dto.CommentFilter;
import com.example.REST.exception.EntityNotFoundException;
import com.example.REST.model.Comment;
import com.example.REST.model.News;
import com.example.REST.model.User;
import com.example.REST.repository.CommentRepository;
import com.example.REST.service.CommentService;
import com.example.REST.service.NewsService;
import com.example.REST.service.UserService;
import com.example.REST.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;

    @Override
    public List<Comment> findByNewsId(CommentFilter filter) {
        return commentRepository.findByNewsId(
                filter.getNewsId(),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(
                                "Комментарий с id {0} не найден!", id
                        )));
    }

    @Override
    public Comment save(Comment comment) {
        User user = userService.findById(comment.getUser().getId());
        News news = newsService.findById(comment.getNews().getId());

        comment.setUser(user);
        comment.setNews(news);
        return commentRepository.save(comment);
    }

    @Override
    @CheckingCommentEditingByUser
    public Comment update(Comment comment) {
        User user = userService.findById(comment.getUser().getId());
        News news = newsService.findById(comment.getNews().getId());
        Comment existedComment = findById(comment.getId());

        BeanUtils.copyNonNullProperties(comment, existedComment);
        existedComment.setUser(user);
        existedComment.setNews(news);
        return commentRepository.save(existedComment);
    }

    @Override
    @CheckingCommentEditingByUser
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
