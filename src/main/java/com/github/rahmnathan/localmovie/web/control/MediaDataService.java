package com.github.rahmnathan.localmovie.web.control;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.rahmnathan.localmovie.web.data.MediaClient;
import com.github.rahmnathan.localmovie.web.data.MovieSearchCriteria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.paginateMediaFiles;
import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.removePosterImages;
import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.sortMediaFiles;

@Service
public class MediaDataService {
    private final CacheService cacheService;

    public MediaDataService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public int loadMediaListLength(String directoryPath){
        return cacheService.listFiles(directoryPath).size();
    }

    public JsonNode loadSingleMediaFile(String filePath) {
        return cacheService.getMedia(filePath);
    }

    public List<JsonNode> loadMediaFileList(MovieSearchCriteria searchCriteria) {
        Set<String> files = cacheService.listFiles(searchCriteria.getPath());
        List<JsonNode> movies = loadMedia(files);

        if (searchCriteria.getClient() == MediaClient.WEBAPP) {
            removePosterImages(movies);
        }

        movies = sortMediaFiles(searchCriteria, movies);
        return paginateMediaFiles(movies, searchCriteria);
    }

    private List<JsonNode> loadMedia(Set<String> relativePaths){
        return relativePaths.stream()
                .sorted()
                .map(cacheService::getMedia)
                .collect(Collectors.toList());
    }

    public List<JsonNode> getMediaFileEvents(LocalDateTime dateTime){
        return cacheService.getMediaEvents().stream()
                .filter(jsonNode -> {
                    LocalDateTime timestamp = LocalDateTime.parse(jsonNode.get("timestamp").textValue());
                    return timestamp.isAfter(dateTime);
                })
                .sorted((node1, node2) -> {
                    LocalDateTime node1Time = LocalDateTime.parse(node1.get("timestamp").textValue());
                    LocalDateTime node2Time = LocalDateTime.parse(node2.get("timestamp").textValue());
                    return node1Time.compareTo(node2Time);
                })
                .collect(Collectors.toList());
    }
}
