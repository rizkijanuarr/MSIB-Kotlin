package com.example.tugasweek9.data.response

import com.google.gson.annotations.SerializedName

data class TopRatedResponse(
    val page: Int,
    val results : List<MovieTopRatedResponse>,
    @SerializedName("total_pages")
    val totalPages:Int,
    @SerializedName("total_results")
    val totalResult:Int,
)
