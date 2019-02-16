package com.github.rahmnathan.localmovie.web.control.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MediaFileEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.rahmnathan.localmovie.domain.CachePrefix.*;
import static com.github.rahmnathan.localmovie.domain.CachePrefix.MEDIA_EVENTS;

@Service
@Profile("jedis")
public class CacheServiceJedis implements CacheService {
    private final Logger logger = LoggerFactory.getLogger(CacheServiceStub.class);
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final CollectionType EVENT_LIST_TYPE = MAPPER.getTypeFactory().constructCollectionType(List.class, MediaFileEvent.class);
    private static final CollectionType FILE_SET_TYPE = MAPPER.getTypeFactory().constructCollectionType(Set.class, String.class);
    private final JedisPool jedisPool;

    public CacheServiceJedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public MediaFile getMedia(String path){
        try (Jedis jedis = jedisPool.getResource()){
            String cacheValue = jedis.get(MEDIA_FILE + path);
            logger.debug("Cache response for media get. Key: {} Value: {}", MEDIA_FILE + path, cacheValue);
            if(cacheValue != null) {
                return MAPPER.readValue(cacheValue, MediaFile.class);
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
                return MAPPER.readValue(cacheValue, FILE_SET_TYPE);
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
                return MAPPER.readValue(cacheValue, EVENT_LIST_TYPE);
        } catch (IOException e){
            logger.error("Failure unmarshalling event list from cache.", e);
        }

        return new ArrayList<>();
    }
}
