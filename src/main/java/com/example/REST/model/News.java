package com.example.REST.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_up")
    private LocalDateTime createdUp;

    @UpdateTimestamp
    @Column(name = "updated_up")
    private LocalDateTime updatedUp;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_category_id")
    private NewsCategory newsCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "news")
    private List<Comment> comments;

    public List<Comment> getComments() {
        if(comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

}
