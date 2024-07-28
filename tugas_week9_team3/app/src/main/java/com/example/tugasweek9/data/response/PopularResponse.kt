package com.example.tugasweek9.data.response

import com.google.gson.annotations.SerializedName

data class PopularResponse(

    val page : Int,
    val results : List<MoviePopularResponse>,
    @SerializedName("total_pages")
    val totalPage : Int,
    @SerializedName("total_results")
    val totalResult: Int
)
