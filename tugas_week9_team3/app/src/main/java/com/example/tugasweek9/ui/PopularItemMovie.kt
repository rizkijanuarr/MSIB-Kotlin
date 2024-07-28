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
import com.example.tugasweek9.data.response.MoviePopularResponse
import com.example.tugasweek9.ui.adapter.PopularAdapter
import kotlinx.coroutines.launch

class PopularItemMovie : AppCompatActivity() {

    private lateinit var adapter: PopularAdapter
    private var listMoviePopular = mutableListOf<MoviePopularResponse>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_item_movie)

        val rvPopular = findViewById<RecyclerView>(R.id.rvPopular)
        rvPopular.layoutManager = LinearLayoutManager(this)

        adapter = PopularAdapter(listMoviePopular)
        rvPopular.adapter = adapter

        adapter.onItemClick = {
            val intent = Intent(this@PopularItemMovie, DetailsMovie::class.java)
            intent.putExtra("Popular", it)
            startActivity(intent)
        }


        lifecycleScope.launch {
            val result = Network.getService(this@PopularItemMovie).getPopular(
                lang = "en",
                page = 1
            )

            Log.d("debug", "hasil total pagenya -> ${result.totalPage}")
            result.results.map {
                Log.d("debug", "hasilnya -> ${it.title}-${it.overview}")
                listMoviePopular.add(it)
            }



            adapter.notifyDataSetChanged()
        }
    }
}