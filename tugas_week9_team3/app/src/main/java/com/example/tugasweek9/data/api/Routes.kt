package com.example.tugasweek9.data.api

import com.example.tugasweek9.data.response.NowPlayingResponse
import com.example.tugasweek9.data.response.PopularResponse
import com.example.tugasweek9.data.response.TopRatedResponse
import com.example.tugasweek9.data.response.UpcomingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Routes {
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("language") lang: String,
        @Query("page") page: Int,
    ): PopularResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("language") lang: String = "en-US",
        @Query("page") page: Int
    ): NowPlayingResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language") lang: String = "en-EN",
        @Query("page") page: Int
    ): UpcomingResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("languague")lang:String = "en_EN",
        @Query("page") page:Int
    ): TopRatedResponse


}