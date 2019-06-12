package com.github.rahmnathan.localmovie.web.control.cache;

import com.github.rahmnathan.localmovie.domain.MediaFile;
import com.github.rahmnathan.localmovie.domain.MediaFileEvent;
import com.github.rahmnathan.omdb.data.Media;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Set;

@Service
@Profile("stub")
public class CacheServiceStub implements CacheService {
    public static final String FILE_PATH = "/opt/localmovies/Movies/test.mp4";
    public static final Set<String> FILE_LIST = Set.of(FILE_PATH);
    public static final MediaFileEvent MEDIA_FILE_EVENT;
    public static final MediaFile MEDIA_FILE;
    public static final Media MOVIE;

    static {
        MOVIE = Media.Builder.newInstance()
                .setTitle("Title")
                .setReleaseYear("1234")
                .build();

        MEDIA_FILE = MediaFile.Builder.newInstance()
                .setFileName(new File(FILE_PATH).getName())
                .setPath(FILE_PATH)
                .setViews(0)
                .setMedia(MOVIE)
                .build();

        MEDIA_FILE_EVENT = new MediaFileEvent("CREATE", MEDIA_FILE, FILE_PATH);
        MEDIA_FILE_EVENT.setTimestamp();
    }

    public MediaFile getMedia(String path){
        return MEDIA_FILE;
    }

    public Set<String> listFiles(String path) {
        return FILE_LIST;
    }

    public List<MediaFileEvent> getMediaEvents(){
        return List.of(MEDIA_FILE_EVENT);
    }
}
