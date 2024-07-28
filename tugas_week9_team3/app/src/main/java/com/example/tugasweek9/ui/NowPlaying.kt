package com.example.tugasweek9.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasweek9.R
import com.example.tugasweek9.data.api.Network
import com.example.tugasweek9.data.response.MovieResponse
import com.example.tugasweek9.ui.adapter.NowPlayingAdapter
import kotlinx.coroutines.launch

class NowPlaying : AppCompatActivity() {

    private lateinit var adapter: NowPlayingAdapter
    private var listMovie = mutableListOf<MovieResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)


        val rvNowPlaying = findViewById<RecyclerView>(R.id.rvNowPlaying)
        rvNowPlaying.layoutManager = LinearLayoutManager(this)

        adapter = NowPlayingAdapter(listMovie)
        rvNowPlaying.adapter = adapter

        adapter.onItemClick = {
            val intent = Intent(this@NowPlaying, DetailsMovie::class.java)
            intent.putExtra("Now Playing", it)
            startActivity(intent)
        }

        lifecycleScope.launch {

            val result = Network.getService(this@NowPlaying).getNowPlaying(
                page = 1
            )

            Log.d("debug", "total page -> ${result.totalPages}")


            result.results.map {
                Log.d("debug", "hasilnya -> ${it.title} - ${it.overview}")
                listMovie.add(it)
            }

            // update recyclerviewnya lagi
            adapter.notifyDataSetChanged()
        }
    }
}


