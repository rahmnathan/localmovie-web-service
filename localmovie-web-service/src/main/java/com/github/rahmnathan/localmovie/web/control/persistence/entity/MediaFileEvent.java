package com.github.rahmnathan.localmovie.web.control.persistence.entity;

import com.github.rahmnathan.localmovie.web.domain.MediaFileEventType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class MediaFileEvent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="media_file_event_sequence_generator")
    @SequenceGenerator(name="media_file_event_sequence_generator", sequenceName="MEDIA_FILE_EVENT_SEQUENCE")
    private Long id;

    private MediaFileEventType eventType;
    private LocalDateTime timestamp;
    private String path;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    private MediaFile mediaFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MediaFileEventType getEventType() {
        return eventType;
    }

    public void setEventType(MediaFileEventType eventType) {
        this.eventType = eventType;
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
        MediaFileEvent that = (MediaFileEvent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(path, that.path) &&
                Objects.equals(eventType, that.eventType) &&
                Objects.equals(mediaFile, that.mediaFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, path, eventType, mediaFile);
    }

    @Override
    public String toString() {
        return "MediaFileEvent{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", path='" + path + '\'' +
                ", eventType='" + eventType + '\'' +
                ", mediaFile=" + mediaFile +
                '}';
    }

    public static MediaFileEvent fromDomain(com.github.rahmnathan.localmovie.web.domain.MediaFileEvent mediaFileEvent){
        return MediaFileEvent.Builder.newInstance()
                .mediaFile(MediaFile.fromDomain(mediaFileEvent.getMediaFile()))
                .eventType(mediaFileEvent.getEventType())
                .path(mediaFileEvent.getPath())
                .build();
    }

    public static class Builder {
        private MediaFileEvent mediaFileEvent = new MediaFileEvent();

        public static Builder newInstance(){
            return new Builder();
        }

        public Builder path(String path){
            this.mediaFileEvent.path = path;
            return this;
        }

        public Builder eventType(MediaFileEventType eventType){
            this.mediaFileEvent.eventType = eventType;
            return this;
        }

        public Builder mediaFile(MediaFile mediaFile){
            this.mediaFileEvent.mediaFile = mediaFile;
            return this;
        }

        public MediaFileEvent build(){
            MediaFileEvent result = mediaFileEvent;
            mediaFileEvent = new MediaFileEvent();

            return result;
        }
    }
}
