package com.example.tugasweek9.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasweek9.R
import com.example.tugasweek9.data.api.Network
import com.example.tugasweek9.data.response.UpcomingMovieResponse
import com.example.tugasweek9.ui.adapter.UpcomingAdapter
import kotlinx.coroutines.launch

class UpcomingMovie : AppCompatActivity() {

    private lateinit var adapter: UpcomingAdapter
    private var listUpcoming = mutableListOf<UpcomingMovieResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_movie)

        val rvUpcomingMovie = findViewById<RecyclerView>(R.id.rvUpcomingMovie)
        rvUpcomingMovie.layoutManager = LinearLayoutManager(this)
        adapter = UpcomingAdapter(listUpcoming)
        rvUpcomingMovie.adapter = adapter

        adapter.onItemClick = {
            val intent = Intent(this@UpcomingMovie, DetailsMovie::class.java)
            intent.putExtra("Upcoming", it)
            startActivity(intent)
        }

        lifecycleScope.launch {
            val result = Network.getService(this@UpcomingMovie).getUpcoming(
                page = 1
            )

            Log.d("Debug", "total page -> ${result.totalPage}")

            result.results.map {
                Log.d("Debug", "hasil -> ${it.title} - ${it.overview}")
                listUpcoming.add(it)
            }

            adapter.notifyDataSetChanged()
        }



    }
}