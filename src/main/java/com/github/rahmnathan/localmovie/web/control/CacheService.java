package com.github.rahmnathan.localmovie.web.control;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CacheService {
    private final Logger logger = LoggerFactory.getLogger(CacheService.class);
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final CollectionType EVENT_LIST_TYPE = MAPPER.getTypeFactory().constructCollectionType(List.class, JsonNode.class);
    private static final CollectionType FILE_SET_TYPE = MAPPER.getTypeFactory().constructCollectionType(Set.class, String.class);
    private final JedisPool jedisPool;

    public CacheService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    JsonNode getMedia(String path){
        try (Jedis jedis = jedisPool.getResource()){
            String cacheValue = jedis.get(MEDIA_FILE + path);
            logger.debug("Cache response for media get. Key: {} Value: {}", MEDIA_FILE + path, cacheValue);
            if(cacheValue != null) {
                return MAPPER.readTree(cacheValue);
            }
        } catch (IOException e){
            logger.error("Failure getting media from cache.", e);
        }

        return MAPPER.createObjectNode().put("path", path);
    }

    Set<String> listFiles(String path) {
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

    List<JsonNode> getMediaEvents(){
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
