package com.github.rahmnathan.localmovie.web.web;

import com.github.rahmnathan.localmovie.web.control.MediaDataService;
import com.github.rahmnathan.localmovie.web.domain.MediaFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/localmovie/management/api/v2/media")
public class MediaManagementResource {
    private final Logger logger = LoggerFactory.getLogger(MediaManagementResource.class.getName());
    private final MediaDataService mediaDataService;

    public MediaManagementResource(MediaDataService mediaDataService) {
        this.mediaDataService = mediaDataService;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void storeMedia(@RequestBody MediaFile mediaFile){
        logger.info("Storing mediaFile.");
        logger.trace(mediaFile.toString());

        mediaDataService.storeMediaFile(mediaFile);
    }
}
