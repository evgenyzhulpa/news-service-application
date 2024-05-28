package com.example.REST.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<News> news;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    public List<News> getNews() {
        if(news == null) {
            news = new ArrayList<>();
        }
        return news;
    }

    public List<Comment> getComments() {
        if(comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    public void addNews(News news) {
        this.news.add(news);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

}
