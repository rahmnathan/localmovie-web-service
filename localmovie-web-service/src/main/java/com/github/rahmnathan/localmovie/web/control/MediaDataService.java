package com.github.rahmnathan.localmovie.web.control;

import com.github.rahmnathan.localmovie.web.control.persistence.MediaPersistenceService;
import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFile;
import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFileEvent;
import com.github.rahmnathan.localmovie.web.domain.MediaClient;
import com.github.rahmnathan.localmovie.web.domain.MediaRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.paginateMediaFiles;
import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.removePosterImages;
import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.sortMediaFiles;

@Service
public class MediaDataService {
    private final MediaPersistenceService persistenceService;

    public MediaDataService(MediaPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public int loadMediaListLength(String directoryPath){
        return persistenceService.listFiles(directoryPath).size();
    }

    public Optional<MediaFile> loadSingleMediaFile(Long id) {
        return persistenceService.getMedia(id);
    }

    public List<MediaFile> loadMediaFileList(MediaRequest mediaRequest) {
        List<MediaFile> media = persistenceService.listFiles(mediaRequest.getPath());

        if (mediaRequest.getClient() == MediaClient.WEBAPP) {
            removePosterImages(media);
        }

        media = sortMediaFiles(mediaRequest, media);
        return paginateMediaFiles(media, mediaRequest);
    }

    public List<MediaFileEvent> getMediaFileEvents(LocalDateTime dateTime){
        return persistenceService.getMediaFileEventsAfter(dateTime);
    }

    public void storeMediaFile(com.github.rahmnathan.localmovie.web.domain.MediaFile mediaFile){
        MediaFile mediaFileEntity = MediaFile.fromDomain(mediaFile);
        persistenceService.storeMediaFile(mediaFileEntity);
    }
}
