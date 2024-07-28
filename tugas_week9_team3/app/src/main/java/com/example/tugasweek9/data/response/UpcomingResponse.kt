package com.example.tugasweek9.data.response

import com.example.tugasweek9.data.response.UpcomingMovieResponse
import com.google.gson.annotations.SerializedName

data class UpcomingResponse (
    val page: Int,
    val results: List<UpcomingMovieResponse>,
    @SerializedName("total_page")
    val totalPage: Int,
    @SerializedName("total_results")
    val totalResults: Int
)