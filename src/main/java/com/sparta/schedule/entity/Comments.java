package com.sparta.schedule.entity;

import com.sparta.schedule.dto.CommentsRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
public class Comments extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comments(CommentsRequestDto commentsRequestDto) {
        this.content = commentsRequestDto.getContent();
        this.userId = commentsRequestDto.getUserId();
    }

    public void update(CommentsRequestDto commentsRequestDto) {
        this.content = commentsRequestDto.getContent();
    }
}
