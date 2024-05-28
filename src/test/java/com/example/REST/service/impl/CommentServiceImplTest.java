package com.example.REST.service.impl;

import com.example.REST.dto.CommentFilter;
import com.example.REST.model.Comment;
import com.example.REST.model.News;
import com.example.REST.model.NewsCategory;
import com.example.REST.model.User;
import com.example.REST.repository.CommentRepository;
import com.example.REST.service.CommentService;
import com.example.REST.service.NewsService;
import com.example.REST.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    private final CommentRepository commentRepository =
            Mockito.mock(CommentRepository.class);
    private final NewsService newsService =
            Mockito.mock(NewsService.class);
    private final UserService userService =
            Mockito.mock(UserService.class);
    private final CommentService commentService = new CommentServiceImpl(commentRepository, userService, newsService);
    private Comment comment;

    @BeforeEach
    public void setUp() {
        Long commentId = 1l;

        User user = new User();
        user.setId(commentId);
        user.setName("Test user");
        NewsCategory newsCategory = new NewsCategory();
        newsCategory.setId(commentId);
        newsCategory.setName("Test news category");

        News news = new News();
        news.setId(commentId);
        news.setUser(user);
        news.setNewsCategory(newsCategory);
        news.setDescription("Test news");
        news.setCreatedUp(LocalDateTime.now());
        news.setUpdatedUp(LocalDateTime.now());
        user.setNews(List.of(news));
        newsCategory.setNews(List.of(news));

        comment = new Comment();
        comment.setId(commentId);
        comment.setCreatedUp(LocalDateTime.now());
        comment.setDescription("Test");
        comment.setUser(user);
        comment.setNews(news);
        List<Comment> commentList = List.of(comment);
        user.setComments(commentList);
        news.setComments(commentList);
    }

    @Test
    @DisplayName("Test findByNewsId method")
    public void testFindByNewsId() {
        CommentFilter commentFilter = new CommentFilter();
        commentFilter.setNewsId(comment.getNews().getId());
        commentFilter.setPageNumber(1);
        commentFilter.setPageSize(10);
        Pageable pageable = PageRequest.of(commentFilter.getPageNumber(), commentFilter.getPageSize());

        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        when(commentRepository.findByNewsId(commentFilter.getNewsId(), pageable)).thenReturn(new PageImpl<>(comments));
        Collection<Comment> commentsFromService = commentService.findByNewsId(commentFilter);

        assertEquals(comments.size(), commentsFromService.size());
        verify(commentRepository, times(1)).findByNewsId(commentFilter.getNewsId(), pageable);
    }

    @Test
    @DisplayName("Test findById method")
    public void testFindById() {
        Long commentId = comment.getId();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        Comment commentFromService = commentService.findById(commentId);
        assertEquals(commentId, commentFromService.getId());
        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    @DisplayName("Test save method")
    public void testSave() {
        commentService.save(comment);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("Test update method")
    public void testUpdate() {
        Long commentId = comment.getId();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.update(comment);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("Test delete by id method")
    public void testDeleteById() {
        Long commentId = comment.getId();
        commentService.deleteById(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

}
