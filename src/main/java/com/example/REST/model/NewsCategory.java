package com.example.REST.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "newsCategory", cascade = CascadeType.ALL)
    private List<News> news;

    public List<News> getNews() {
        if(news == null) {
            news = new ArrayList<>();
        }
        return news;
    }

    public void addNews(News news) {
        this.news.add(news);
    }

}
