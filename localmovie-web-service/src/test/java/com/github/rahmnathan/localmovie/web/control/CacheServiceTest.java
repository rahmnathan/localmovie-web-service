package com.github.rahmnathan.localmovie.web.control;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CacheServiceTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
//    private final CacheService mediaCacheService;
//    private final Jedis jedis = mock(Jedis.class);
//    private static final String PATH = "Movies";
//
//    public CacheServiceTest() {
//        JedisPool jedisPool = mock(JedisPool.class);
//        when(jedisPool.getResource()).thenReturn(jedis);
//
//        this.mediaCacheService = new CacheService(jedisPool);
//    }
//
//    @Test
//    public void getMediaTest() {
//        when(jedis.get(MEDIA_FILE + PATH)).thenReturn(MAPPER.createObjectNode().put("path", PATH).toString());
//
//        JsonNode mediaFile = mediaCacheService.getMedia(PATH);
//
//        assertEquals(PATH, mediaFile.get("path").textValue());
//    }
//
//    @Test
//    public void listFilesTest() throws Exception {
//        when(jedis.get(FILE_LIST + PATH)).thenReturn(MAPPER.writeValueAsString(Set.of(PATH)));
//
//        Set<String> fileList = mediaCacheService.listFiles(PATH);
//
//        assertEquals(PATH, fileList.iterator().next());
//    }
//
//    @Test
//    public void getMediaEventsTest() throws Exception {
//        when(jedis.get(MEDIA_EVENTS.name())).thenReturn(MAPPER.writeValueAsString(Set.of(MAPPER.createObjectNode().put("path", PATH))));
//
//        List<JsonNode> mediaEvents = mediaCacheService.getMediaEvents();
//
//        assertEquals(PATH, mediaEvents.get(0).get("path").textValue());
//    }
}
