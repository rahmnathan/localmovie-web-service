package com.github.rahmnathan.localmovie.web.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.rahmnathan.localmovie.web.control.MediaMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class MediaResourceTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private MediaResource mediaResource;

//    @BeforeEach
//    public void init(){
//        mediaResource = new MediaResource(mock(MediaMetadataService.class), new String[0]);
//    }
//
//    @Test
//    public void testExtractImage() throws Exception {
//        String inputJson = "{\n" +
//                "  \"created\": 1561430034312,\n" +
//                "  \"updated\": null,\n" +
//                "  \"views\": 0,\n" +
//                "  \"media\": {\n" +
//                "    \"id\": 688,\n" +
//                "    \"mediaType\": \"MOVIE\",\n" +
//                "    \"image\": \"SGVsbG8gV29ybGQ=\",\n" +
//                "    \"title\": \"Aladdin\",\n" +
//                "    \"imdbRating\": \"8.0\",\n" +
//                "    \"metaRating\": \"86\",\n" +
//                "    \"created\": [\n" +
//                "      2019,\n" +
//                "      6,\n" +
//                "      25,\n" +
//                "      2,\n" +
//                "      33,\n" +
//                "      54,\n" +
//                "      312969000\n" +
//                "    ],\n" +
//                "    \"updated\": null\n" +
//                "  }\n" +
//                "}";
//
//        mediaResource.extractMediaPoster(MAPPER.readTree(inputJson));
//    }
}
