package com.github.rahmnathan.localmovie.web.control;

import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MediaFileEvent;
import com.github.rahmnathan.localmovie.domain.MovieClient;
import com.github.rahmnathan.localmovie.domain.MovieSearchCriteria;
import org.springframework.stereotype.Service;

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
    private final MediaCacheService cacheService;

    public MediaMetadataService(MediaCacheService cacheService) {
        this.cacheService = cacheService;
    }

    public int loadMediaListLength(String directoryPath){
        return cacheService.listFiles(directoryPath).size();
    }

    public MediaFile loadSingleMediaFile(String filePath) {
        return cacheService.getMedia(filePath);
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
        return relativePaths.stream()
                .sorted()
                .map(cacheService::getMedia)
                .collect(Collectors.toList());
    }

    public List<MediaFileEvent> getMediaFileEvents(LocalDateTime dateTime){
        return cacheService.getMediaEvents().stream()
                .filter(mediaFileEvent -> mediaFileEvent.getTimestamp().isAfter(dateTime))
                .sorted(Comparator.comparing(MediaFileEvent::getTimestamp))
                .collect(Collectors.toList());
    }
}
