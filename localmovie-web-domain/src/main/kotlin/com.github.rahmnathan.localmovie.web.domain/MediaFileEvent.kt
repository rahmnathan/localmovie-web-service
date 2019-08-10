package com.github.rahmnathan.localmovie.web.domain

data class MediaFileEvent(val eventType: MediaFileEventType,
                          val path: String,
                          val mediaFile: MediaFile?)
