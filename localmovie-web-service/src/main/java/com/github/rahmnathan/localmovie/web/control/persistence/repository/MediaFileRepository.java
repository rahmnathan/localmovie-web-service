package com.github.rahmnathan.localmovie.web.control.persistence.repository;

import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
    List<MediaFile> findAllByPathOrderByFileName(String path);
}
