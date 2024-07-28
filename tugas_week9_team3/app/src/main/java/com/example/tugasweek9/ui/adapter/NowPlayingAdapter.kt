package com.example.tugasweek9.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasweek9.R
import com.example.tugasweek9.data.response.MovieResponse
import com.example.tugasweek9.data.response.UpcomingMovieResponse
import com.squareup.picasso.Picasso

class NowPlayingAdapter(val listMovie: List<MovieResponse>) : RecyclerView.Adapter<NowPlayingAdapter.NowPlayingHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_now_playing, parent, false)
        return NowPlayingHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: NowPlayingHolder, position: Int) {
        val itemMovie = listMovie[position]
        holder.bindView(listMovie[position])

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(itemMovie)
        }
    }

    var onItemClick : ((MovieResponse) -> Unit)? = null

    inner class NowPlayingHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(movie: MovieResponse) {
            // inisiasi view
            val imgPoster = view.findViewById<ImageView>(R.id.imgPoster)
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
//            val iconBintang = view.findViewById<ImageView>(R.id.iconBintang)
            val tvRating = view.findViewById<TextView>(R.id.tvRating)
            val tvOverview = view.findViewById<TextView>(R.id.tvOverview)
            val tvPopularity = view.findViewById<TextView>(R.id.tvPopularity)
            val tvReleased = view.findViewById<TextView>(R.id.tvReleased)

            tvTitle.text = movie.title
            tvRating.text = "${movie.voteAverage}"
            tvOverview.text = "${movie.overview}"
            tvPopularity.text = "${movie.popularity}"
            tvReleased.text = "${movie.releaseDate}"
            val path = buildPosterPath(movie.posterPath)

            // load image from url into imageview
            Picasso.get().load(path).into(imgPoster)
        }

        private fun buildPosterPath(posterPath: String?): String {
            return "https://image.tmdb.org/t/p/w500$posterPath"
        }

    }



}