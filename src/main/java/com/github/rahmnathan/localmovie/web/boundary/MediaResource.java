package com.github.rahmnathan.localmovie.web.boundary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.rahmnathan.localmovie.web.control.FileSender;
import com.github.rahmnathan.localmovie.web.control.MediaMetadataService;
import com.github.rahmnathan.localmovie.web.data.MovieInfoRequest;
import com.github.rahmnathan.localmovie.web.data.MovieSearchCriteria;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;

@RestController
public class MediaResource {
    private final Logger logger = LoggerFactory.getLogger(MediaResource.class.getName());
    private final FileSender fileSender = new FileSender();
    private static final String MEDIA_PROPERTY = "media";
    private static final String IMAGE_PROPERTY = "image";
    private final MediaMetadataService metadataService;
    private final String[] mediaPaths;

    public MediaResource(MediaMetadataService metadataService, @Value("${media.path}") String[] mediaPaths){
        this.metadataService = metadataService;
        this.mediaPaths = mediaPaths;
    }

    @PostMapping(value = "/localmovie/v2/media", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<JsonNode> getMovies(@RequestBody MovieInfoRequest movieInfoRequest, HttpServletResponse response) {
        logger.info("Received request: {}", movieInfoRequest.toString());

        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(movieInfoRequest.getPath(), movieInfoRequest.getPage(),
                movieInfoRequest.getResultsPerPage(), movieInfoRequest.getClient(), movieInfoRequest.getOrder());

        Integer page = searchCriteria.getPage();
        if(page != null && page == 0)
            getMovieCount(movieInfoRequest.getPath(), response);

        List<JsonNode> movieInfoList = metadataService.loadMediaFileList(searchCriteria);

        logger.info("Returning {} movies", movieInfoList.size());
        return movieInfoList;
    }

    @GetMapping(value = "/localmovie/v2/media/count")
    public void getMovieCount(@RequestParam(value = "path") String path, HttpServletResponse response){
        logger.info("Received count request for path - {}", path);

        int count = metadataService.loadMediaListLength(path);

        logger.info("Returning count of - {}", count);
        response.setHeader("Count", String.valueOf(count));
    }

    /**
     * @param path - Path to video file to stream
     */
    @GetMapping(value = "/localmovie/v2/media/stream.mp4", produces = "video/mp4")
    public void streamVideo(@RequestParam("path") String path, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        logger.info("Received streaming request - {}", path);
        for(String mediaPath : mediaPaths) {
            logger.info("Checking if mediaPath {} contains requested path {}", mediaPath, path);
            if (new File(mediaPath + path).exists()) {
                logger.info("Streaming - {}{}", mediaPath, path);
                response.setHeader(HttpHeaders.CONTENT_TYPE, "video/mp4");
                fileSender.serveResource(Paths.get(mediaPath + path), request, response);
                break;
            }
            logger.warn("Path not found in mediaPaths: {}", path);
        }
    }

    /**
     * @param path - Path to video file
     * @return - Poster image for specified video file
     */
    @GetMapping(path = "/localmovie/v2/media/poster")
    public byte[] getPoster(@RequestParam("path") String path) {
        logger.info("Streaming poster - {}", path);

        JsonNode mediaFile = metadataService.loadSingleMediaFile(path);
        return extractMediaPoster(mediaFile);
    }

    /**
     *
     * @param epoch - Timestamp to collect events since
     * @return - List of MediaFileEvents
     */
    @GetMapping(path = "/localmovie/v2/media/events")
    public List<JsonNode> getEvents(@RequestParam("timestamp") Long epoch) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault());
        logger.info("Request for events since: {}", localDateTime);

        List<JsonNode> events = metadataService.getMediaFileEvents(localDateTime);

        logger.info("Events response. Time: {} EventList: {}", localDateTime, events);
        return events;
    }

    byte[] extractMediaPoster(JsonNode mediaFile){
        if(mediaFile.has(MEDIA_PROPERTY)){
            ObjectNode media = (ObjectNode) mediaFile.get(MEDIA_PROPERTY);
            if(!media.isNull() && media.has(IMAGE_PROPERTY)){
                JsonNode image = media.get(IMAGE_PROPERTY);
                if(!StringUtils.isEmpty(image.textValue())){
                    return Base64.getDecoder().decode(image.textValue());
                }
            }
        }

        return new byte[0];
    }
}