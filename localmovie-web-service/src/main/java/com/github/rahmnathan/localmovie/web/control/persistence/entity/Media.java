package com.github.rahmnathan.localmovie.web.control.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.rahmnathan.localmovie.web.domain.MediaType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="media_sequence_generator")
    @SequenceGenerator(name="media_sequence_generator", sequenceName="MEDIA_SEQUENCE")
    private Long id;

    private MediaType mediaType;
    @Lob
    private String image;
    private String title;
    private String imdbRating;
    private String metaRating;
    private String releaseYear;
    private String actors;
    @Lob
    private String plot;
    private String genre;
    private Integer number;
    private LocalDateTime created;
    private LocalDateTime updated;

    @JsonIgnore
    @OneToOne(mappedBy = "media", cascade = CascadeType.ALL)
    private MediaFile mediaFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getMetaRating() {
        return metaRating;
    }

    public void setMetaRating(String metaRating) {
        this.metaRating = metaRating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public MediaFile getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return Objects.equals(id, media.id) &&
                mediaType == media.mediaType &&
                Objects.equals(image, media.image) &&
                Objects.equals(title, media.title) &&
                Objects.equals(imdbRating, media.imdbRating) &&
                Objects.equals(metaRating, media.metaRating) &&
                Objects.equals(releaseYear, media.releaseYear) &&
                Objects.equals(actors, media.actors) &&
                Objects.equals(plot, media.plot) &&
                Objects.equals(genre, media.genre) &&
                Objects.equals(number, media.number) &&
                Objects.equals(created, media.created) &&
                Objects.equals(updated, media.updated) &&
                Objects.equals(mediaFile, media.mediaFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mediaType, image, title, imdbRating, metaRating, releaseYear, actors, plot, genre, number, created, updated, mediaFile);
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", mediaType=" + mediaType +
                ", title='" + title + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", metaRating='" + metaRating + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", genre='" + genre + '\'' +
                ", number=" + number +
                ", created=" + created +
                ", updated=" + updated +
                ", mediaFile=" + mediaFile +
                '}';
    }

    public static Media fromDomain(com.github.rahmnathan.localmovie.web.domain.Media media){
        return Media.Builder.newInstance()
                .mediaType(media.getMediaType())
                .title(media.getTitle())
                .imdbRating(media.getImdbRating())
                .metaRating(media.getMetaRating())
                .releaseYear(media.getReleaseYear())
                .actors(media.getActors())
                .plot(media.getPlot())
                .genre(media.getGenre())
                .number(media.getNumber())
                .build();
    }

    public static class Builder {
        private Media media = new Media();

        public static Builder newInstance(){
            return new Builder();
        }

        public Builder mediaType(MediaType mediaType){
            this.media.mediaType = mediaType;
            return this;
        }

        public Builder title(String title){
            this.media.title = title;
            return this;
        }

        public Builder imdbRating(String imdbRating){
            this.media.imdbRating = imdbRating;
            return this;
        }

        public Builder metaRating(String metaRating){
            this.media.metaRating = metaRating;
            return this;
        }

        public Builder releaseYear(String releaseYear){
            this.media.releaseYear = releaseYear;
            return this;
        }

        public Builder actors(String actors){
            this.media.actors = actors;
            return this;
        }

        public Builder plot(String plot){
            this.media.plot = plot;
            return this;
        }

        public Builder genre(String genre){
            this.media.genre = genre;
            return this;
        }

        public Builder number(Integer number){
            this.media.number = number;
            return this;
        }

        public Builder mediaFile(MediaFile mediaFile){
            this.media.mediaFile = mediaFile;
            return this;
        }

        public Media build(){
            Media result = media;
            media = new Media();

            return result;
        }
    }
}
