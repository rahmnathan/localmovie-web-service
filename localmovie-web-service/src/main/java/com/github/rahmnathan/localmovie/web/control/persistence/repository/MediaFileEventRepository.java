package com.github.rahmnathan.localmovie.web.control.persistence.repository;

import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFileEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MediaFileEventRepository extends JpaRepository<MediaFileEvent, Long> {
    List<MediaFileEvent> findAllByCreatedAfterOrderByCreated(LocalDateTime timestamp);
}
