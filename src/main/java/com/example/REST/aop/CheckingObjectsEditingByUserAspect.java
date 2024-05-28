package com.example.REST.aop;

import com.example.REST.exception.EntityNotFoundException;
import com.example.REST.model.Comment;
import com.example.REST.model.News;
import com.example.REST.repository.CommentRepository;
import com.example.REST.repository.NewsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Aspect
@Component
public class CheckingObjectsEditingByUserAspect {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    public CheckingObjectsEditingByUserAspect(NewsRepository newsRepository, CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Before("@annotation(CheckingNewsEditingByUser)")
    public void checkNewsEditingByUser(JoinPoint joinPoint) {
        Map<String, Long> idDataMap = getIdDataMap();
        if(!idDataMap.containsKey("id") || !idDataMap.containsKey("currentUserId")) {
            return;
        }

        Long newsId = idDataMap.get("id");
        Long currentUserId = idDataMap.get("currentUserId");
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isEmpty()) {
            return;
        }

        News news = optionalNews.get();
        Long userId = news.getUser().getId();
        if (!userId.equals(currentUserId)) {
            throw new EntityNotFoundException("Редактирование и удаление новости разрешается" +
                    " только тому пользователю, который ее создал!");
        }
    }

    @Before("@annotation(CheckingCommentEditingByUser)")
    public void checkCommentEditingByUser(JoinPoint joinPoint) {
        Map<String, Long> idDataMap = getIdDataMap();
        if(!idDataMap.containsKey("id") || !idDataMap.containsKey("currentUserId")) {
            return;
        }

        Long commentId = idDataMap.get("id");
        Long currentUserId = idDataMap.get("currentUserId");
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            return;
        }

        Comment comment = optionalComment.get();
        Long userId = comment.getUser().getId();
        if (!userId.equals(currentUserId)) {
            throw new EntityNotFoundException("Редактирование и удаление комментария разрешается" +
                    " только тому пользователю, который его создал!");
        }
    }

    private Map<String, Long> getIdDataMap() {
        HashMap<String, Long> idDataMap = new HashMap<>();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        var pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String currentUserId = request.getParameter("current_user_id");

        if(pathVariables.containsKey("id")) {
            idDataMap.put("id", Long.valueOf(pathVariables.get("id")));
        }

        if(currentUserId != null) {
            idDataMap.put("currentUserId", Long.valueOf(currentUserId));
        }

        return idDataMap;
    }
}
