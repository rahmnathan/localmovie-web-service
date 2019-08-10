package com.github.rahmnathan.localmovie.web.control;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.rahmnathan.localmovie.web.domain.MediaClient;
import com.github.rahmnathan.localmovie.web.domain.MediaOrder;
import com.github.rahmnathan.localmovie.web.domain.MediaRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.removePosterImages;
import static com.github.rahmnathan.localmovie.web.control.MediaFileUtils.sortMediaFiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MediaFileUtilsTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

//    @Test
//    public void removeImagesTest(){
//        JsonNode media = MAPPER.createObjectNode().put("image", "my-image");
//        JsonNode mediaFile = MAPPER.createObjectNode().set("media", media);
//        List<JsonNode> mediaFiles = List.of(mediaFile);
//        removePosterImages(mediaFiles);
//
//        assertFalse(mediaFiles.get(0).get("media").has("image"));
//    }
//
//    @Test
//    public void sortMediaFilesTest(){
//        JsonNode mediaFile1 = MAPPER.createObjectNode().put("created", LocalDateTime.now().toString());
//        JsonNode mediaFile2 = MAPPER.createObjectNode().put("created", LocalDateTime.now().toString());
//        JsonNode mediaFile3 = MAPPER.createObjectNode().put("created", LocalDateTime.now().toString());
//        JsonNode mediaFile4 = MAPPER.createObjectNode().put("created", LocalDateTime.now().toString());
//
//        List<JsonNode> mediaList = List.of(mediaFile3, mediaFile2, mediaFile1, mediaFile4);
//
//        MediaRequest mediaRequest = new MediaRequest("", 1, 1, MediaClient.WEBAPP, MediaOrder.DATE_ADDED);
//
//        sortMediaFiles(mediaRequest, mediaList);
//
//        assertEquals(mediaFile3.get("created").textValue(), mediaList.get(0).get("created").asText());
//        assertEquals(mediaFile2.get("created").textValue(), mediaList.get(1).get("created").asText());
//        assertEquals(mediaFile1.get("created").textValue(), mediaList.get(2).get("created").asText());
//    }
}
