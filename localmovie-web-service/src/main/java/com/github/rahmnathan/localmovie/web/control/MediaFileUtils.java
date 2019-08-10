package com.github.rahmnathan.localmovie.web.control;

import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFile;
import com.github.rahmnathan.localmovie.web.domain.MediaOrder;
import com.github.rahmnathan.localmovie.web.domain.MediaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class MediaFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(MediaFileUtils.class.getName());

    static List<MediaFile> sortMediaFiles(MediaRequest mediaRequest, List<MediaFile> mediaFiles){
        if (mediaRequest.getPath().split(File.separator).length > 1) {
            return sortMovieInfoList(mediaFiles, MediaOrder.SEASONS_EPISODES);
        } else if (mediaRequest.getOrder() != null) {
            return sortMovieInfoList(mediaFiles, mediaRequest.getOrder());
        }

        return mediaFiles;
    }

    static List<MediaFile> paginateMediaFiles(List<MediaFile> mediaFiles, MediaRequest mediaRequest){
        logger.info("Paginating movie list - page: {} resultsPerPage: {}", mediaRequest.getPage(), mediaRequest.getResultsPerPage());
        return mediaFiles.stream()
                .skip(mediaRequest.getPage() * mediaRequest.getResultsPerPage())
                .limit(mediaRequest.getResultsPerPage())
                .collect(Collectors.toList());
    }

    static void removePosterImages(List<MediaFile> mediaFiles){
        logger.info("Removing images for webapp");
        mediaFiles.forEach(MediaFile::removeImage);
    }

    private static List<MediaFile> sortMovieInfoList(List<MediaFile> mediaFileList, MediaOrder order){
        logger.info("Sorting movie list: {}", order.name());
        switch (order) {
            case DATE_ADDED:
                return mediaFileList.stream()
                        .sorted(Comparator.comparing(MediaFile::getCreated))
                        .collect(Collectors.toList());
            case MOST_VIEWS:
                return mediaFileList.stream()
                        .sorted(Comparator.comparing(MediaFile::getViews))
                        .collect(Collectors.toList());
            case RELEASE_YEAR:
                return mediaFileList.stream()
                        .sorted(Comparator.comparing(mediaFile -> mediaFile.getMedia().getReleaseYear()))
                        .collect(Collectors.toList());
            case RATING:
                return mediaFileList.stream()
                        .sorted(Comparator.comparing(mediaFile -> mediaFile.getMedia().getImdbRating()))
                        .collect(Collectors.toList());
            case SEASONS_EPISODES:
                return mediaFileList.stream()
                        .sorted(Comparator.comparing(mediaFile -> mediaFile.getMedia().getNumber()))
                        .collect(Collectors.toList());
            default:
                return mediaFileList;
        }
    }
}
