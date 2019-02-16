package com.github.rahmnathan.localmovie.web.boundary;

import com.github.rahmnathan.localmovie.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("stub")
public class MovieResourceTest {
    private final MovieResource movieResource;

    @Autowired
    public MovieResourceTest(MovieResource movieResource) {
        this.movieResource = movieResource;
    }

    @Test
    public void getMoviesTest() {
        MovieInfoRequest movieInfoRequest = new MovieInfoRequest("/", 0, 100, MovieClient.WEBAPP, MovieOrder.RELEASE_YEAR);

        HttpServletResponse response = new MockHttpServletResponse();
        List<MediaFile> mediaFiles = movieResource.getMovies(movieInfoRequest, response);

        assertNotNull(response.getHeader("Count"));
        assertEquals(1, mediaFiles.size());

    }

    @Test
    public void getMovieCount(){
        HttpServletResponse response = new MockHttpServletResponse();
        movieResource.getMovieCount("/", response);

        assertEquals(1, Integer.valueOf(response.getHeader("Count")).intValue());
    }

    @Test
    public void streamVideo() {
        HttpServletResponse response = new MockHttpServletResponse();
        HttpServletRequest request = new MockHttpServletRequest();

        movieResource.streamVideo("/", response, request);
    }

    @Test
    public void getPoster() {
        byte[] poster = movieResource.getPoster("/");

        assertNotNull(poster);
    }

    @Test
    public void getEventsTest() {
        List<MediaFileEvent> mediaFileEvents = movieResource.getEvents(1L);

        assertNotNull(mediaFileEvents);
        assertEquals(1, mediaFileEvents.size());
    }
}
