package com.github.rahmnathan.localmovie.web.domain

data class Media(val mediaType: MediaType,
                 val releaseYear: String?,
                 val imdbRating: String?,
                 val metaRating: String?,
                 val number: Int?,
                 val actors: String?,
                 val genre: String?,
                 val image: String?,
                 val title: String,
                 val plot: String?)
