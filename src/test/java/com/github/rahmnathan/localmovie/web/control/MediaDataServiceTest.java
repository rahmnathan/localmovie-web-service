package com.github.rahmnathan.localmovie.web.control;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.rahmnathan.localmovie.web.data.MediaClient;
import com.github.rahmnathan.localmovie.web.data.MediaRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MediaDataServiceTest {
    private final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final String PATH = "Movies";
    private CacheService cacheService = mock(CacheService.class);
    private MediaDataService mediaDataService = new MediaDataService(cacheService);

    @Test
    public void loadSingleMediaFileTest(){
        when(cacheService.getMedia(PATH)).thenReturn(MAPPER.createObjectNode().put("path", PATH));

        JsonNode mediaFile = mediaDataService.loadSingleMediaFile(PATH);

        assertEquals(PATH, mediaFile.get("path").textValue());
    }

    @Test
    public void loadMediaListLengthTest(){
        when(cacheService.listFiles(PATH)).thenReturn(Set.of(PATH, PATH + "/test"));

        int mediaListLength = mediaDataService.loadMediaListLength(PATH);

        assertEquals(2, mediaListLength);
    }

    @Test
    public void loadMediaFileListTest(){
        String secondPath = "Series";
        when(cacheService.getMedia(PATH)).thenReturn(MAPPER.createObjectNode().put("path", PATH));
        when(cacheService.getMedia(secondPath)).thenReturn(MAPPER.createObjectNode().put("path", secondPath));
        when(cacheService.listFiles(PATH)).thenReturn(Set.of(PATH, secondPath));

        MediaRequest mediaRequest = new MediaRequest(PATH, 0, 2, MediaClient.ANDROID, null);

        List<JsonNode> mediaFiles = mediaDataService.loadMediaFileList(mediaRequest);

        assertEquals(2, mediaFiles.size());
    }

    @Test
    public void getMediaEventsTest(){
        JsonNode mediaFileEvent = MAPPER.createObjectNode()
                .put("path", PATH)
                .put("timestamp", LocalDateTime.now().toString());

        String secondPath = "Series";
        JsonNode mediaFileEvent2 = MAPPER.createObjectNode()
                .put("path", secondPath)
                .put("timestamp", LocalDateTime.now().toString());

        when(cacheService.getMediaEvents()).thenReturn(List.of(mediaFileEvent, mediaFileEvent2));

        List<JsonNode> mediaFileEvents = mediaDataService.getMediaFileEvents(LocalDateTime.now().minusHours(4));

        assertEquals(2, mediaFileEvents.size());
        assertEquals(PATH, mediaFileEvents.get(0).get("path").textValue());
    }
}
