package com.github.rahmnathan.localmovie.web.control.persistence;

import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFile;
import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFileEvent;
import com.github.rahmnathan.localmovie.web.control.persistence.repository.MediaFileEventRepository;
import com.github.rahmnathan.localmovie.web.control.persistence.repository.MediaFileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MediaPersistenceService {
    private final MediaFileEventRepository mediaFileEventRepository;
    private final MediaFileRepository mediaFileRepository;

    public MediaPersistenceService(MediaFileEventRepository mediaFileEventRepository, MediaFileRepository mediaFileRepository) {
        this.mediaFileEventRepository = mediaFileEventRepository;
        this.mediaFileRepository = mediaFileRepository;
    }

    public List<MediaFile> listFiles(String path){
        return mediaFileRepository.findAllByPathOrderByFileName(path);
    }

    public Optional<MediaFile> getMedia(Long id){
        return mediaFileRepository.findById(id);
    }

    public List<MediaFileEvent> getMediaFileEventsAfter(LocalDateTime localDateTime){
        return mediaFileEventRepository.findAllByCreatedAfterOrderByCreated(localDateTime);
    }

    public void storeMediaFile(MediaFile mediaFile){
        mediaFileRepository.save(mediaFile);
    }
}
