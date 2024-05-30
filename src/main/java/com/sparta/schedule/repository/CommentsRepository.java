package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

}
