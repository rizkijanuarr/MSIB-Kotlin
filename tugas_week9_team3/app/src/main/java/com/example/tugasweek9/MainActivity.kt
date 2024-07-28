package com.example.tugasweek9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.tugasweek9.data.api.Network
import com.example.tugasweek9.data.response.UpcomingMovieResponse
import com.example.tugasweek9.ui.NowPlaying
import com.example.tugasweek9.ui.PopularItemMovie
import com.example.tugasweek9.ui.TopRated
import com.example.tugasweek9.ui.UpcomingMovie
import com.example.tugasweek9.ui.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var btnNowPlaying: Button
    lateinit var btnTopRated: Button
    lateinit var btnPopular: Button
    lateinit var btnUpcoming: Button
    private var upcomingMovie = mutableListOf<UpcomingMovieResponse>()

    // kiki nambahin private auth : firebaseauth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // kiki nambahin button signut disini yaa
        auth = Firebase.auth
        val btnSignout = findViewById<Button>(R.id.btnSignout)
        btnSignout.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        btnNowPlaying = findViewById(R.id.btnNowPlaying)
        btnPopular = findViewById(R.id.btnPopular)
        btnTopRated = findViewById(R.id.btnTopRated)
        btnUpcoming = findViewById(R.id.btnUpcoming)

        lifecycleScope.launch {
            val result = Network.getService(this@MainActivity).getUpcoming(
                page = 1
            )




            Log.d("Debug", "total page -> ${result.totalPage}")

            result.results.map {
                Log.d("Debug", "hasil -> ${it.title} - ${it.overview}")
                upcomingMovie.add(it)
            }
        }

        btnNowPlaying.setOnClickListener{
            val intent = Intent(this, NowPlaying::class.java)
            startActivity(intent)
            finish()
        }

        btnUpcoming.setOnClickListener {
            val intent = Intent(this, UpcomingMovie::class.java)
            startActivity(intent)
            finish()
        }

        btnTopRated.setOnClickListener {
            val intent = Intent(this, TopRated::class.java)
            startActivity(intent)
            finish()
        }

        btnPopular.setOnClickListener {
            val intent = Intent(this, PopularItemMovie::class.java)
            startActivity(intent)
            finish()
        }

    }
}