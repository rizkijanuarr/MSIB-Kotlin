package com.example.tugasweek9.data.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieTopRatedResponse(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val overview: String?,
    val popularity: Float?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Float?,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("original_language")
    val originalLanguage: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdropPath)
        parcel.writeInt(id)
        parcel.writeString(overview)
        parcel.writeValue(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeString(title)
        parcel.writeValue(voteAverage)
        parcel.writeInt(voteCount)
        parcel.writeString(originalLanguage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieTopRatedResponse> {
        override fun createFromParcel(parcel: Parcel): MovieTopRatedResponse {
            return MovieTopRatedResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieTopRatedResponse?> {
            return arrayOfNulls(size)
        }
    }
}

