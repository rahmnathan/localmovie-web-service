package com.github.rahmnathan.localmovie.web.control;

import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MediaFileEvent;
import com.github.rahmnathan.localmovie.domain.MovieClient;
import com.github.rahmnathan.localmovie.domain.MovieSearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.ManagedBean;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.paginateMediaFiles;
import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.removePosterImages;
import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.sortMediaFiles;

@Service
public class MediaMetadataService {
    private final Logger logger = LoggerFactory.getLogger(MediaMetadataService.class);
    private final MediaCacheService cacheService;

    public MediaMetadataService(MediaCacheService cacheService) {
        this.cacheService = cacheService;
    }

    public int loadMediaListLength(String directoryPath){
        return cacheService.listFiles(directoryPath).size();
    }

    public MediaFile loadSingleMediaFile(String filePath) {
        return getMediaMetadata(filePath);
    }

    public List<MediaFile> loadMediaFileList(MovieSearchCriteria searchCriteria) {
        Set<String> files = cacheService.listFiles(searchCriteria.getPath());
        List<MediaFile> movies = loadMedia(files);

        if (searchCriteria.getClient() == MovieClient.WEBAPP) {
            movies = removePosterImages(movies);
        }

        movies = sortMediaFiles(searchCriteria, movies);
        return paginateMediaFiles(movies, searchCriteria);
    }

    private List<MediaFile> loadMedia(Set<String> relativePaths){
        return relativePaths.parallelStream()
                .sorted()
                .map(this::getMediaMetadata)
                .collect(Collectors.toList());
    }

    public MediaFile getMediaMetadata(String path){
        return cacheService.getMedia(path);
    }

    public List<MediaFileEvent> getMediaFileEvents(LocalDateTime dateTime){
        return cacheService.getMediaEvents().stream()
                .sorted(Comparator.comparing(MediaFileEvent::getTimestamp))
                .filter(mediaFileEvent -> mediaFileEvent.getTimestamp().isAfter(dateTime))
                .collect(Collectors.toList());
    }
}
