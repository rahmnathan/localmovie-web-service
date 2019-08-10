package com.github.rahmnathan.localmovie.web.domain

data class MediaRequest(val path: String,
                        val page: Int?,
                        val resultsPerPage: Int?,
                        val client: MediaClient?,
                        val order: MediaOrder?)
