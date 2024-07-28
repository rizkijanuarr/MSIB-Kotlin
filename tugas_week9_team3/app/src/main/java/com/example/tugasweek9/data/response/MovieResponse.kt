package com.example.tugasweek9.data.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// MENGIDENTIFIKASI DIDALAM DATA OBJECTNYA ATAU MENDFINISIKAN KEY NYA.

// MELIHAT DATA INI SEMUA DARI MANA?
// 1. KE POSTMAN DULU, DI COPY DULU SEGALA MACAM API_KEY ATAU ATHORIZATION NYA
// 2. KLIK BUTTON SEND DAN NANTI AKAN MUNCUL RESPONSENYA, NAH ITU DI COPY HASIL RESPONSE NYA
// 3. KEMUDIAN KE LINK WEB https://jsonviewer.stack.hu/ , DAN PASTE HASILNYA

data class MovieResponse(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Float?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Float?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdropPath)
        parcel.writeValue(id)
        parcel.writeString(originalTitle)
        parcel.writeString(overview)
        parcel.writeValue(popularity)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeString(title)
        parcel.writeValue(voteAverage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieResponse> {
        override fun createFromParcel(parcel: Parcel): MovieResponse {
            return MovieResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieResponse?> {
            return arrayOfNulls(size)
        }
    }
}
