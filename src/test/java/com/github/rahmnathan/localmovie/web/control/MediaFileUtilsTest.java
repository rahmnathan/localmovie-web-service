package com.github.rahmnathan.localmovie.web.control;

import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MovieClient;
import com.github.rahmnathan.localmovie.domain.MovieOrder;
import com.github.rahmnathan.localmovie.domain.MovieSearchCriteria;
import com.github.rahmnathan.omdb.data.Media;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MediaFileUtilsTest {
    private static final Random RANDOM = new Random();

    @Test
    public void paginateMediaFilesTest(){
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria("/", 0, 2, MovieClient.WEBAPP, MovieOrder.RELEASE_YEAR);
        List<MediaFile> paginatedMedia = MediaFileUtils.paginateMediaFiles(buildMediaFileList(), searchCriteria);

        assertEquals(2, paginatedMedia.size());
    }

    @Test
    public void removePosterImagesTest(){
        List<MediaFile> mediaFilesNoPosterImages = MediaFileUtils.removePosterImages(buildMediaFileList());

        mediaFilesNoPosterImages.forEach(mediaFile -> assertEquals("", mediaFile.getMedia().getImage()));
    }

    @Test
    public void sortMediaListDateTest(){
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria("/", 0, 2, MovieClient.WEBAPP, MovieOrder.DATE_ADDED);
        List<MediaFile> sortedMedia = MediaFileUtils.sortMediaFiles(searchCriteria, buildMediaFileList());

        assertTrue(sortedMedia.size() > 1);
        MediaFile previous = sortedMedia.get(0);
        for(int i = 1; i < sortedMedia.size(); i++){
            MediaFile current = sortedMedia.get(i);
            assertTrue(previous.getCreated() >= current.getCreated());
            previous = current;
        }
    }

    @Test
    public void sortMediaListViewsTest(){
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria("/", 0, 2, MovieClient.WEBAPP, MovieOrder.MOST_VIEWS);
        List<MediaFile> sortedMedia = MediaFileUtils.sortMediaFiles(searchCriteria, buildMediaFileList());

        assertTrue(sortedMedia.size() > 1);
        MediaFile previous = sortedMedia.get(0);
        for(int i = 1; i < sortedMedia.size(); i++){
            MediaFile current = sortedMedia.get(i);
            assertTrue(previous.getViews() >= current.getViews());
            previous = current;
        }
    }

    @Test
    public void sortMediaListRatingTest(){
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria("/", 0, 2, MovieClient.WEBAPP, MovieOrder.RATING);
        List<MediaFile> sortedMedia = MediaFileUtils.sortMediaFiles(searchCriteria, buildMediaFileList());

        assertTrue(sortedMedia.size() > 1);
        MediaFile previous = sortedMedia.get(0);
        for(int i = 1; i < sortedMedia.size(); i++){
            MediaFile current = sortedMedia.get(i);
            assertTrue(Double.valueOf(previous.getMedia().getImdbRating()) >= Double.valueOf(current.getMedia().getImdbRating()));
            previous = current;
        }
    }

    @Test
    public void sortMediaListYearTest(){
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria("/", 0, 2, MovieClient.WEBAPP, MovieOrder.RELEASE_YEAR);
        List<MediaFile> sortedMedia = MediaFileUtils.sortMediaFiles(searchCriteria, buildMediaFileList());

        assertTrue(sortedMedia.size() > 1);
        MediaFile previous = sortedMedia.get(0);
        for(int i = 1; i < sortedMedia.size(); i++){
            MediaFile current = sortedMedia.get(i);
            assertTrue(Integer.valueOf(previous.getMedia().getReleaseYear()) >= Integer.valueOf(current.getMedia().getReleaseYear()));
            previous = current;
        }
    }

    private List<MediaFile> buildMediaFileList(){
        List<MediaFile> mediaFiles = new ArrayList<>();
        for(int i = 0; i < 200; i++){
            mediaFiles.add(buildMediaFile("/first/path" + i));
        }

        return mediaFiles;
    }

    public static MediaFile buildMediaFile(String path){
        MediaFile mediaFile = MediaFile.Builder.newInstance()
                .setFileName("test")
                .setPath(path)
                .setViews(RANDOM.nextInt())
                .setMedia(buildMovie())
                .build();

        mediaFile.setUpdated();
        mediaFile.setCreated();

        return mediaFile;
    }

    private static Media buildMovie(){
        return Media.Builder.newInstance()
                .setReleaseYear(String.valueOf(RANDOM.nextInt()))
                .setTitle("title")
                .setIMDBRating(String.valueOf(RANDOM.nextDouble()))
                .setImage("fakeImage")
                .setGenre("Action")
                .build();
    }
}
