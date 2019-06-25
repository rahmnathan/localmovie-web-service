package com.github.rahmnathan.localmovie.web.data

data class MovieInfoRequest(val path: String, val page: Int?, val resultsPerPage: Int?, val client: MediaClient?,
                            val order: MediaOrder?)
