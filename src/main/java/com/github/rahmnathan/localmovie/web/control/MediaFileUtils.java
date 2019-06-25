package com.github.rahmnathan.localmovie.web.control;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.rahmnathan.localmovie.web.data.MediaOrder;
import com.github.rahmnathan.localmovie.web.data.MovieSearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

class MediaFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(MediaFileUtils.class.getName());

    static List<JsonNode> sortMediaFiles(MovieSearchCriteria searchCriteria, List<JsonNode> mediaFiles){
        if (searchCriteria.getPath().split(File.separator).length > 1) {
            return sortMovieInfoList(mediaFiles, MediaOrder.SEASONS_EPISODES);
        } else if (searchCriteria.getOrder() != null) {
            return sortMovieInfoList(mediaFiles, searchCriteria.getOrder());
        }

        return mediaFiles;
    }

    static List<JsonNode> paginateMediaFiles(List<JsonNode> mediaFiles, MovieSearchCriteria searchCriteria){
        logger.info("Paginating movie list - page: {} resultsPerPage: {}", searchCriteria.getPage(), searchCriteria.getItemsPerPage());
        return mediaFiles.stream()
                .skip(searchCriteria.getPage() * searchCriteria.getItemsPerPage())
                .limit(searchCriteria.getItemsPerPage())
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

    private static List<JsonNode> sortMovieInfoList(List<JsonNode> movieInfoList, MediaOrder order){
        logger.info("Sorting movie list: {}", order.name());
        switch (order) {
//            case DATE_ADDED:
//                return movieInfoList.stream()
//                        .sorted((movie1, movie2) -> Long.compare(movie2.getCreated(), movie1.getCreated()))
//                        .collect(Collectors.toList());
//            case MOST_VIEWS:
//                return movieInfoList.stream()
//                        .sorted((movie1, movie2) -> Integer.compare(movie2.getViews(), movie1.getViews()))
//                        .collect(Collectors.toList());
//            case RELEASE_YEAR:
//                return movieInfoList.stream()
//                        .sorted((movie1, movie2) -> Long.valueOf(movie2.getMedia().getReleaseYear())
//                                .compareTo(Long.valueOf(movie1.getMedia().getReleaseYear())))
//                        .collect(Collectors.toList());
//            case RATING:
//                return movieInfoList.stream()
//                        .sorted((movie1, movie2) -> Double.valueOf(movie2.getMedia().getImdbRating())
//                                .compareTo(Double.valueOf(movie1.getMedia().getImdbRating())))
//                        .collect(Collectors.toList());
//            case SEASONS_EPISODES:
//                return movieInfoList.stream()
//                        .sorted(Comparator.comparing(movie -> movie.getMedia().getNumber()))
//                        .collect(Collectors.toList());
            default:
                return movieInfoList;
        }
    }
}
