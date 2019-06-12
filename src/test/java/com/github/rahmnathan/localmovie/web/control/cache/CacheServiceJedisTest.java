package com.github.rahmnathan.localmovie.web.control.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MediaFileEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

import static com.github.rahmnathan.localmovie.domain.CachePrefix.*;
import static com.github.rahmnathan.localmovie.web.control.MediaFileUtilsTest.buildMediaFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CacheServiceJedisTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String PATH = "/my/test/path";
    private CacheServiceJedis cacheServiceJedis;

    @BeforeEach
    public void init() throws Exception {
        Jedis jedis = mock(Jedis.class);
        when(jedis.get(MEDIA_FILE + PATH)).thenReturn(MAPPER.writeValueAsString(buildMediaFile(PATH)));
        when(jedis.get(FILE_LIST + PATH)).thenReturn(MAPPER.writeValueAsString(List.of("/first/path", "/second/path")));
        when(jedis.get(MEDIA_EVENTS.name())).thenReturn(MAPPER.writeValueAsString(List.of(new MediaFileEvent("CREATE", buildMediaFile(PATH), PATH))));

        JedisPool jedisPool = mock(JedisPool.class);
        when(jedisPool.getResource()).thenReturn(jedis);

        this.cacheServiceJedis = new CacheServiceJedis(jedisPool);
    }

    @Test
    public void getExistingMediaTest(){
        MediaFile media = cacheServiceJedis.getMedia(PATH);

        assertNotNull(media);
        assertNotNull(media.getMedia());
        assertEquals(PATH, media.getPath());
    }

    @Test
    public void getMissingMediaTest(){
        String path = "MyFakePath";
        MediaFile media = cacheServiceJedis.getMedia(path);

        assertNotNull(media);
        assertNull(media.getMedia());
        assertEquals(path, media.getPath());
    }

    @Test
    public void listFilesTest(){
        Set<String> files = cacheServiceJedis.listFiles(PATH);

        assertNotNull(files);
        assertTrue(files.size() > 0);
    }

    @Test
    public void listMissingFilesTest(){
        Set<String> files = cacheServiceJedis.listFiles("MyFakePath");

        assertNotNull(files);
        assertEquals(0, files.size());
    }

    @Test
    public void getMediaEventsTest(){
        List<MediaFileEvent> events = cacheServiceJedis.getMediaEvents();

        assertNotNull(events);
        assertTrue(events.size() > 0);
    }
}
