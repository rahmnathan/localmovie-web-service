package com.github.rahmnathan.localmovie.web.data

data class MovieSearchCriteria(val path: String, val page: Int?, val itemsPerPage: Int?, val client: MediaClient?, val order: MediaOrder?)
