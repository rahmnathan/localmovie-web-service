package com.github.rahmnathan.localmovie.web.control.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class MediaFile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="media_file_sequence_generator")
    @SequenceGenerator(name="media_file_sequence_generator", sequenceName="MEDIA_FILE_SEQUENCE")
    private Long id;
    @Column(unique = true)
    private String path;
    private String fileName;
    private long created;
    private LocalDateTime updated;
    private int views;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Media media;

    @JsonIgnore
    @OneToOne(mappedBy = "mediaFile", cascade = CascadeType.ALL)
    private MediaFileEvent mediaFileEvent;

    @Version
    @JsonIgnore
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaFileEvent getMediaFileEvent() {
        return mediaFileEvent;
    }

    public void setMediaFileEvent(MediaFileEvent mediaFileEvent) {
        this.mediaFileEvent = mediaFileEvent;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void removeImage(){
        this.media.setImage(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaFile mediaFile = (MediaFile) o;
        return created == mediaFile.created &&
                views == mediaFile.views &&
                version == mediaFile.version &&
                Objects.equals(id, mediaFile.id) &&
                Objects.equals(path, mediaFile.path) &&
                Objects.equals(fileName, mediaFile.fileName) &&
                Objects.equals(updated, mediaFile.updated) &&
                Objects.equals(media, mediaFile.media) &&
                Objects.equals(mediaFileEvent, mediaFile.mediaFileEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, fileName, created, updated, views, media, mediaFileEvent, version);
    }

    @Override
    public String toString() {
        return "MediaFile{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", views=" + views +
                ", media=" + media +
                ", mediaFileEvent=" + mediaFileEvent +
                ", version=" + version +
                '}';
    }

    public static MediaFile fromDomain(com.github.rahmnathan.localmovie.web.domain.MediaFile mediaFile){
        return Builder.newInstance()
                .path(mediaFile.getPath())
                .fileName(mediaFile.getFileName())
                .media(Media.fromDomain(mediaFile.getMedia()))
                .build();
    }

    public static class Builder {
        private MediaFile mediaFile = new MediaFile();

        public static Builder newInstance(){
            return new Builder();
        }

        public Builder path(String path){
            this.mediaFile.path = path;
            return this;
        }

        public Builder fileName(String fileName){
            this.mediaFile.fileName = fileName;
            return this;
        }

        public Builder media(com.github.rahmnathan.localmovie.web.control.persistence.entity.Media media){
            this.mediaFile.media = media;
            return this;
        }

        public MediaFile build(){
            MediaFile result = mediaFile;
            mediaFile = new MediaFile();

            return result;
        }
    }
}
