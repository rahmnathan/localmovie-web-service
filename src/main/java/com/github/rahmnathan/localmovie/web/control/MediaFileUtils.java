package com.github.rahmnathan.localmovie.web.control;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.rahmnathan.localmovie.web.data.MediaOrder;
import com.github.rahmnathan.localmovie.web.data.MediaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class MediaFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(MediaFileUtils.class.getName());

    static List<JsonNode> sortMediaFiles(MediaRequest mediaRequest, List<JsonNode> mediaFiles){
        if (mediaRequest.getPath().split(File.separator).length > 1) {
            return sortMovieInfoList(mediaFiles, MediaOrder.SEASONS_EPISODES);
        } else if (mediaRequest.getOrder() != null) {
            return sortMovieInfoList(mediaFiles, mediaRequest.getOrder());
        }

        return mediaFiles;
    }

    static List<JsonNode> paginateMediaFiles(List<JsonNode> mediaFiles, MediaRequest mediaRequest){
        logger.info("Paginating movie list - page: {} resultsPerPage: {}", mediaRequest.getPage(), mediaRequest.getResultsPerPage());
        return mediaFiles.stream()
                .skip(mediaRequest.getPage() * mediaRequest.getResultsPerPage())
                .limit(mediaRequest.getResultsPerPage())
                .collect(Collectors.toList());
    }

    static void removePosterImages(List<JsonNode> mediaFiles){
        logger.info("Removing images for webapp");
        mediaFiles.forEach(jsonNode -> {
            if (jsonNode.has("media")) {
                ObjectNode mediaNode = (ObjectNode) jsonNode.get("media");
                if (mediaNode.has("image")) {
                    mediaNode.remove("image");
                }
            }
        });
    }

    private static List<JsonNode> sortMovieInfoList(List<JsonNode> mediaFileList, MediaOrder order){
        logger.info("Sorting movie list: {}", order.name());
        switch (order) {
            case DATE_ADDED:
                return mediaFileList.stream()
                        .sorted(MediaFileUtils::compareCreatedTimestamps)
                        .collect(Collectors.toList());
            case MOST_VIEWS:
                return mediaFileList.stream()
                        .sorted(MediaFileUtils::compareViews)
                        .collect(Collectors.toList());
            case RELEASE_YEAR:
                return mediaFileList.stream()
                        .sorted(MediaFileUtils::compareReleaseYear)
                        .collect(Collectors.toList());
            case RATING:
                return mediaFileList.stream()
                        .sorted(MediaFileUtils::compareImdbRating)
                        .collect(Collectors.toList());
            case SEASONS_EPISODES:
                return mediaFileList.stream()
                        .sorted(Comparator.comparing(mediaFile -> mediaFile.get("media").get("number").asInt()))
                        .collect(Collectors.toList());
            default:
                return mediaFileList;
        }
    }

    private static int compareCreatedTimestamps(JsonNode mediaFile1, JsonNode mediaFile2){
        return Long.compare(mediaFile2.get("created").longValue(), mediaFile1.get("created").longValue());
    }

    private static int compareViews(JsonNode mediaFile1, JsonNode mediaFile2){
        return Integer.compare(mediaFile2.get("views").asInt(), mediaFile1.get("views").asInt());
    }

    private static int compareReleaseYear(JsonNode mediaFile1, JsonNode mediaFile2){
        return Long.compare(mediaFile2.get("media").get("releaseYear").asLong(), mediaFile1.get("media").get("releaseYear").asLong());
    }

    private static int compareImdbRating(JsonNode mediaFile1, JsonNode mediaFile2){
        return Long.compare(mediaFile2.get("media").get("imdbRating").asLong(), mediaFile1.get("media").get("imdbRating").asLong());
    }
}
