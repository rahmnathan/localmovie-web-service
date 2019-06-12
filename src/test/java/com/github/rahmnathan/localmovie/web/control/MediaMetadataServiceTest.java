package com.github.rahmnathan.localmovie.web.control;

import com.github.rahmnathan.localmovie.domain.*;
import com.github.rahmnathan.localmovie.web.control.cache.CacheServiceStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("stub")
public class MediaMetadataServiceTest {
    private final MediaMetadataService mediaMetadataService;

    @Autowired
    public MediaMetadataServiceTest(MediaMetadataService mediaMetadataService) {
        this.mediaMetadataService = mediaMetadataService;
    }

    @Test
    public void loadMediaListLengthTest(){
        int length = mediaMetadataService.loadMediaListLength(CacheServiceStub.FILE_PATH);

        assertEquals(CacheServiceStub.FILE_LIST.size(), length);
    }

    @Test
    public void loadSingleMediaFileTest(){
        MediaFile mediaFile = mediaMetadataService.loadSingleMediaFile(CacheServiceStub.FILE_PATH);

        assertEquals(CacheServiceStub.FILE_PATH, mediaFile.getPath());
        assertEquals(CacheServiceStub.MOVIE.getTitle(), mediaFile.getMedia().getTitle());
    }

    @Test
    public void loadMediaFileListTest(){
        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(CacheServiceStub.FILE_PATH, 0, 1000, MovieClient.WEBAPP, MovieOrder.RELEASE_YEAR);
        List<MediaFile> mediaFiles = mediaMetadataService.loadMediaFileList(searchCriteria);

        assertEquals(CacheServiceStub.FILE_LIST.size(), mediaFiles.size());
    }

    @Test
    public void getMediaFileEventsTest(){
        List<MediaFileEvent> mediaFileEvents = mediaMetadataService.getMediaFileEvents(LocalDateTime.now().minusHours(1));

        assertEquals(1, mediaFileEvents.size());
        assertEquals(CacheServiceStub.MEDIA_FILE_EVENT, mediaFileEvents.get(0));
    }
}
