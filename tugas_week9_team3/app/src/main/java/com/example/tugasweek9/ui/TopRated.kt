package com.example.tugasweek9.ui

import TopRatedAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasweek9.R
import com.example.tugasweek9.data.api.Network
import com.example.tugasweek9.data.response.MovieTopRatedResponse
import kotlinx.coroutines.launch

class TopRated : AppCompatActivity() {

    private lateinit var adapter: TopRatedAdapter
    private var listMovieTopRated = mutableListOf<MovieTopRatedResponse>() // Change the type to MovieTopRatedResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_rated)

        val rvTopRated = findViewById<RecyclerView>(R.id.rvTopRated)
        rvTopRated.layoutManager = LinearLayoutManager(this)

        adapter = TopRatedAdapter(listMovieTopRated)
        rvTopRated.adapter = adapter

        adapter.onItemClick = {
            val intent = Intent(this@TopRated, DetailsMovie::class.java)
            intent.putExtra("Top Rated",it)
            startActivity(intent)
        }

        lifecycleScope.launch {

            val result = Network.getService(this@TopRated).getTopRated(
                page = 1
            )

            Log.d("debug", "total page -> ${result.totalPages}")

            result.results.map {
                Log.d("debug", "hasilnya -> ${it.title} - ${it.overview}")
                listMovieTopRated.add(it)
            }

            // Update the RecyclerView
            adapter.notifyDataSetChanged()
        }
    }
}
