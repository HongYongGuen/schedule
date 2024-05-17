package com.sparta.schedule.repository;

import com.sparta.schedule.entity.File;
import com.sparta.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByFileId(Long fileId);
}
