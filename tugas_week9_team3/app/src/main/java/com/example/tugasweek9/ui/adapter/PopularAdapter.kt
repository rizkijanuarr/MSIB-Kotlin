package com.example.tugasweek9.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasweek9.R
import com.example.tugasweek9.data.response.MoviePopularResponse
import com.example.tugasweek9.data.response.MovieResponse
import com.squareup.picasso.Picasso

class PopularAdapter (private val listMoviePopular: List<MoviePopularResponse>) :
    RecyclerView.Adapter<PopularAdapter.PopularHolder>() {

    var onItemClick : ((MoviePopularResponse) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_mv, parent, false)
        return PopularHolder(view)
    }

    override fun onBindViewHolder(holder: PopularHolder, position: Int) {
        val itemPopularMovie = listMoviePopular[position]
        holder.bindView(listMoviePopular[position])

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(itemPopularMovie)
        }
    }

    override fun getItemCount(): Int {
        return listMoviePopular.size
    }

    inner class PopularHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(movie: MoviePopularResponse) {
            val posterImage = view.findViewById<ImageView>(R.id.posterimage)
            val title = view.findViewById<TextView>(R.id.titletv)
//            val titlepage = view.findViewById<TextView>(R.id.titlePage)
//            val rilisdate = view.findViewById<TextView>(R.id.txtRelesedate)
            val daterilis = view.findViewById<TextView>(R.id.daterilismov)
            val textrate = view.findViewById<TextView>(R.id.textRate)
//            val starRate = view.findViewById<ImageView>(R.id.starrateicn)
            val overview = view.findViewById<TextView>(R.id.descPopMovie)
//            val icndetail = view.findViewById<ImageView>(R.id.btnmoredetails)
//            val detail = view.findViewById<TextView>(R.id.textIcnDetail)



            title.text = movie.title
            textrate.text = "${movie.voteAverage}"
            overview.text = movie.overview
            daterilis.text = "${movie.releaseDate}"


            val path = buildPosterPath(movie.posterPath)

            //percobaan

            //load image from url
            Picasso.get().load(path).into(posterImage)

        }

        private fun buildPosterPath(posterPath: String?): String {
            return "https://image.tmdb.org/t/p/w500/$posterPath"

        }

    }
}