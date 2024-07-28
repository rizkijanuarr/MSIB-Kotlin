import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasweek9.R
import com.example.tugasweek9.data.response.MovieTopRatedResponse
import com.squareup.picasso.Picasso

class TopRatedAdapter(private val listMovie: List<MovieTopRatedResponse>) :
    RecyclerView.Adapter<TopRatedAdapter.TopRatedHolder>() {

    var onItemClick: ((MovieTopRatedResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top_rated, parent, false)
        return TopRatedHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: TopRatedHolder, position: Int) {
        val itemTopRatedMovie = listMovie[position]
        holder.bindView(listMovie[position])

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(itemTopRatedMovie)
        }
    }



    inner class TopRatedHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(movie: MovieTopRatedResponse) {
            // Initialization of views
            val imgPoster = view.findViewById<ImageView>(R.id.imgPosterTopRated)
            val tvTitle = view.findViewById<TextView>(R.id.tvTittleTopRated)
            val tvRating = view.findViewById<TextView>(R.id.tvRatingTopRated)
            val tvOverview = view.findViewById<TextView>(R.id.tvOverviewTopRated)

            tvTitle.text = movie.title
            tvRating.text = movie.voteAverage.toString()
            tvOverview.text = movie.overview.toString()
            val path = buildPosterPath(movie.posterPath)

            // Load an image from a URL into ImageView
            Picasso.get().load(path).into(imgPoster)
        }

        private fun buildPosterPath(posterPath: String?): String {
            return "https://image.tmdb.org/t/p/w500/$posterPath"
        }
    }
}
