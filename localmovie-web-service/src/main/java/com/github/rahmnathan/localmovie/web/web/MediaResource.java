package com.github.rahmnathan.localmovie.web.web;

import com.github.rahmnathan.localmovie.web.config.WebServiceConfig;
import com.github.rahmnathan.localmovie.web.control.FileSender;
import com.github.rahmnathan.localmovie.web.control.MediaDataService;
import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFile;
import com.github.rahmnathan.localmovie.web.control.persistence.entity.MediaFileEvent;
import com.github.rahmnathan.localmovie.web.domain.MediaRequest;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/localmovie/api/v2/media")
public class MediaResource {
    private final Logger logger = LoggerFactory.getLogger(MediaResource.class.getName());
    private final FileSender fileSender = new FileSender();
    private final MediaDataService metadataService;
    private final String[] mediaPaths;

    public MediaResource(MediaDataService metadataService, WebServiceConfig serviceConfig){
        this.mediaPaths = serviceConfig.getMediaPaths();
        this.metadataService = metadataService;
    }

    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<MediaFile> getMedia(@RequestBody MediaRequest mediaRequest, HttpServletResponse response, Principal principal) {
        logger.info("Received request: {}", mediaRequest.toString());

        Integer page = mediaRequest.getPage();
        if(page != null && page == 0)
            getMediaCount(mediaRequest.getPath(), response);

        List<MediaFile> movieInfoList = metadataService.loadMediaFileList(mediaRequest);

        logger.info("Returning {} movies", movieInfoList.size());
        return movieInfoList;
    }

    @GetMapping(value = "/count")
    public void getMediaCount(@RequestParam(value = "path") String path, HttpServletResponse response){
        logger.info("Received count request for path - {}", path);

        int count = metadataService.loadMediaListLength(path);

        logger.info("Returning count of - {}", count);
        response.setHeader("Count", String.valueOf(count));
    }

    /**
     * @param id - Path to video file to stream
     */
    @GetMapping(value = "/{id}/stream.mp4", produces = "video/mp4")
    public void streamVideo(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader(HttpHeaders.CONTENT_TYPE, "video/mp4");
        logger.info("Received streaming request - {}", id);

        metadataService.loadSingleMediaFile(id).ifPresent(mediaFile -> {
            boolean found = false;
            for (String mediaPath : mediaPaths) {
                logger.info("Checking if mediaPath {} contains requested path {}", mediaPath, id);
                if (new File(mediaPath + mediaFile.getPath()).exists()) {
                    logger.info("Streaming - {}{}", mediaPath, mediaFile.getPath());
                    found = true;
                    fileSender.serveResource(Paths.get(mediaPath + mediaFile.getPath()), request, response);
                    break;
                }
            }

            if (!found) {
                logger.warn("Path not found in mediaPaths: {}", id);
            }
        });
    }

    /**
     * @param id - MediaFile id
     * @return - Poster image for specified video file
     */
    @GetMapping(path = "/{id}/poster")
    public byte[] getPoster(@PathVariable("id") Long id) {
        logger.info("Streaming poster - {}", id);

        Optional<MediaFile> mediaFile = metadataService.loadSingleMediaFile(id);
        return mediaFile.map(this::extractMediaPoster).orElseGet(() -> new byte[0]);
    }

    /**
     * @param epoch - Timestamp to collect events since
     * @return - List of MediaFileEvents
     */
    @GetMapping(path = "/events")
    public List<MediaFileEvent> getEvents(@RequestParam("timestamp") Long epoch) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault());
        logger.info("Request for events since: {}", localDateTime);

        List<MediaFileEvent> events = metadataService.getMediaFileEvents(localDateTime);

        logger.info("Events response. Time: {} EventList: {}", localDateTime, events);
        return events;
    }

    private byte[] extractMediaPoster(MediaFile mediaFile){
        if(mediaFile.getMedia() != null){
            if(mediaFile.getMedia().getImage() != null){
                Base64.getDecoder().decode(mediaFile.getMedia().getImage());
            }
        }

        return new byte[0];
    }
}