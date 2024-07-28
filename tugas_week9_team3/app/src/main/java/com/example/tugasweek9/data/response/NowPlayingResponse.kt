package com.example.tugasweek9.data.response

import com.google.gson.annotations.SerializedName

// UNTUK MENGHANDLE DATA DARI NOW PLAYING

data class NowPlayingResponse(
    val page: Int,
    val results: List<MovieResponse>,

    // Tambahan, ini jika kita pengen melihat total pages nya dan total resultnya
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
