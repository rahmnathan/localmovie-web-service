package com.github.rahmnathan.localmovie.web.control.cache;

import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MediaFileEvent;

import java.util.List;
import java.util.Set;

public interface CacheService {
    MediaFile getMedia(String path);
    Set<String> listFiles(String path);
    List<MediaFileEvent> getMediaEvents();
}
