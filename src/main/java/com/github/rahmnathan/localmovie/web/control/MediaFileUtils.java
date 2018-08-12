package com.github.rahmnathan.localmovie.web.control;

import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MovieOrder;
import com.github.rahmnathan.localmovie.domain.MovieSearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

class MediaFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(MediaFileUtils.class.getName());

    static List<MediaFile> sortMediaFiles(MovieSearchCriteria searchCriteria, List<MediaFile> mediaFiles){
        logger.info("Sorting movie list - order: {}", searchCriteria.getOrder());
        if (searchCriteria.getPath().split(File.separator).length > 1) {
            return sortMovieInfoList(mediaFiles, MovieOrder.SEASONS_EPISODES);
        } else if (searchCriteria.getOrder() != null) {
            return sortMovieInfoList(mediaFiles, searchCriteria.getOrder());
        }

        return mediaFiles;
    }

    static List<MediaFile> paginateMediaFiles(List<MediaFile> mediaFiles, MovieSearchCriteria searchCriteria){
        logger.info("Paginating movie list - page: {} resultsPerPage: {}", searchCriteria.getPage(), searchCriteria.getItemsPerPage());
        return mediaFiles.stream()
                .skip(searchCriteria.getPage() * searchCriteria.getItemsPerPage())
                .limit(searchCriteria.getItemsPerPage())
                .collect(Collectors.toList());
    }

    static List<MediaFile> removePosterImages(List<MediaFile> mediaFiles){
        logger.info("Removing images for webapp");
        return mediaFiles.stream()
                .map(MediaFile.Builder::copyWithNoImage)
                .collect(Collectors.toList());
    }

    private static List<MediaFile> sortMovieInfoList(List<MediaFile> movieInfoList, MovieOrder order){
        logger.info("Sorting movie list: {}", order.name());
        switch (order){
            case DATE_ADDED:
                return movieInfoList.stream()
                        .sorted((movie1, movie2) -> Long.compare(movie2.getCreated(), movie1.getCreated()))
                        .collect(Collectors.toList());
            case MOST_VIEWS:
                return movieInfoList.stream()
                        .sorted((movie1, movie2) -> Integer.compare(movie2.getViews(), movie1.getViews()))
                        .collect(Collectors.toList());
            case RELEASE_YEAR:
                return movieInfoList.stream()
                        .sorted((movie1, movie2) -> Long.valueOf(movie2.getMovie().getReleaseYear())
                                .compareTo(Long.valueOf(movie1.getMovie().getReleaseYear())))
                        .collect(Collectors.toList());
            case RATING:
                return movieInfoList.stream()
                        .sorted((movie1, movie2) -> Double.valueOf(movie2.getMovie().getImdbRating())
                                .compareTo(Double.valueOf(movie1.getMovie().getImdbRating())))
                        .collect(Collectors.toList());
            case SEASONS_EPISODES:
                return movieInfoList.stream()
                        .sorted((movie1, movie2) -> {
                            Integer current = Integer.parseInt(movie1.getMovie().getTitle().split(" ")[1]);
                            Integer next = Integer.parseInt(movie2.getMovie().getTitle().split(" ")[1]);
                            return current.compareTo(next);
                        })
                        .collect(Collectors.toList());
        }
        return movieInfoList;
    }
}
