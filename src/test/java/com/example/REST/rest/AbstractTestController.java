package com.example.REST.rest;

import com.example.REST.dto.CommentResponse;
import com.example.REST.dto.NewsCategoryResponse;
import com.example.REST.dto.NewsResponse;
import com.example.REST.dto.UserResponse;
import com.example.REST.model.Comment;
import com.example.REST.model.News;
import com.example.REST.model.NewsCategory;
import com.example.REST.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected User createUser(Long id, News news, Comment comment) {
        User user = new User(id, "User " + id,
                new ArrayList<>(), new ArrayList<>());
        if (news != null) {
            news.setUser(user);
            comment.setUser(user);
            user.addNews(news);
            user.addComment(comment);
        }
        return user;
    }

    protected News createNews(Long id, User user, NewsCategory newsCategory) {
        News news = new News();
        news.setId(id);
        news.setCreatedUp(LocalDateTime.now());
        news.setUpdatedUp(LocalDateTime.now());
        news.setTitle("News " + id);
        news.setDescription("This is the news " + id);
        if (user != null) {
            news.setUser(user);
            user.addNews(news);
        }
        if (newsCategory != null) {
            news.setNewsCategory(newsCategory);
            newsCategory.addNews(news);
        }
        return news;
    }

    protected NewsCategory createNewsCategory(Long id) {
        return new NewsCategory(id, "News category " + id, new ArrayList<>());
    }

    protected Comment createComment(Long id, User user, News news) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setCreatedUp(LocalDateTime.now());
        comment.setDescription("This is the comment " + id);
        if (user != null) {
            comment.setUser(user);
            user.addComment(comment);
        }
        if (news != null) {
            comment.setNews(news);
            news.addComment(comment);
        }
        return comment;
    }

    protected UserResponse createUserResponse(Long id, NewsResponse newsResponse, CommentResponse commentResponse) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(id);
        userResponse.setName("User " + id);
        if (newsResponse != null) {
            userResponse.getNews().add(newsResponse);
        }
        if (commentResponse != null) {
            userResponse.getComments().add(commentResponse);
        }
        return userResponse;
    }

    protected NewsResponse createNewsResponse(Long id, CommentResponse commentResponse) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setId(id);
        newsResponse.setTitle("News " + id);
        newsResponse.setDescription("This is the news " + id);
        if (commentResponse != null) {
            newsResponse.getComments().add(commentResponse);
        }
        return newsResponse;
    }

    protected NewsCategoryResponse createNewsCategoryResponse(Long id, NewsResponse newsResponse) {
        NewsCategoryResponse newsCategoryResponse = new NewsCategoryResponse();
        newsCategoryResponse.setId(id);
        newsCategoryResponse.setName("News category " + id);
        if (newsResponse != null) {
            newsCategoryResponse.getNews().add(newsResponse);
        }
        return newsCategoryResponse;
    }

    protected CommentResponse createCommentResponse(Long id) {
        return new CommentResponse(id, "This is the comment " + id);
    }
}
