package com.example.REST.controller;

import com.example.REST.dto.CommentFilter;
import com.example.REST.dto.CommentListResponse;
import com.example.REST.dto.CommentResponse;
import com.example.REST.dto.UpsertCommentRequest;
import com.example.REST.mapper.CommentMapper;
import com.example.REST.model.Comment;
import com.example.REST.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<CommentListResponse> findByNewsId(CommentFilter filter) {
        return ResponseEntity.ok(
                commentMapper.commentListToCommentListResponse(
                        commentService.findByNewsId(filter)
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentMapper.commentToResponse(
                        commentService.findById(id)
                ));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment newComment = commentService.save(commentMapper.requestToComment(request));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(newComment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertCommentRequest request) {
        Comment comment = commentService.update(commentMapper.requestToComment(id, request));
        return ResponseEntity.ok(commentMapper.commentToResponse(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
