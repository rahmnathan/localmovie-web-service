package com.github.rahmnathan.localmovie.web.control;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MediaFileEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.*;

import static com.github.rahmnathan.localmovie.domain.CachePrefix.FILE_LIST;
import static com.github.rahmnathan.localmovie.domain.CachePrefix.MEDIA_EVENTS;
import static com.github.rahmnathan.localmovie.domain.CachePrefix.MEDIA_FILE;

@Service
public class MediaCacheService {
    private final Logger logger = LoggerFactory.getLogger(MediaCacheService.class);
    private static final TypeReference<List<MediaFileEvent>> EVENT_LIST_TYPE = new TypeReference<List<MediaFileEvent>>() {};
    private final ObjectMapper objectMapper;
    private final JedisPool jedisPool;

    public MediaCacheService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        this.objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new JavaTimeModule());
    }

    public MediaFile getMedia(String path){
        try (Jedis jedis = jedisPool.getResource()){
            String cacheValue = jedis.get(MEDIA_FILE + path);
            logger.debug("Cache response for media get. Key: {} Value: {}", MEDIA_FILE + path, cacheValue);
            if(cacheValue != null) {
                return objectMapper.readValue(cacheValue, MediaFile.class);
            }
        } catch (IOException e){
            logger.error("Failure getting media fron cache.", e);
        }

        return MediaFile.Builder.newInstance()
                .setPath(path)
                .build();
    }

    public Set<String> listFiles(String path) {
        try (Jedis jedis = jedisPool.getResource()){
            String cacheValue = jedis.get(FILE_LIST + path);
            logger.debug("Cache response for file list get. Key: {} Value: {}", FILE_LIST + path, cacheValue);
            if(cacheValue != null)
                return objectMapper.readValue(cacheValue, Set.class);
        } catch (IOException e){
            logger.error("Failure unmarshalling file set from cache.", e);
        }

        return new HashSet<>();
    }

    public List<MediaFileEvent> getMediaEvents(){
        try (Jedis jedis = jedisPool.getResource()){
            String cacheValue = jedis.get(MEDIA_EVENTS.name());
            logger.debug("Cache response for events get. Key: {} Value: {}", MEDIA_EVENTS, cacheValue);
            if(cacheValue != null)
                return objectMapper.readValue(cacheValue, EVENT_LIST_TYPE);
        } catch (IOException e){
            logger.error("Failure unmarshalling event list from cache.", e);
        }

        return new ArrayList<>();
    }
}
