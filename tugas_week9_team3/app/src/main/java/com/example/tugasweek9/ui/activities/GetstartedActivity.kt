package com.example.tugasweek9.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.tugasweek9.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GetstartedActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getstarted)

        // inisiasi firebase nya
        auth = Firebase.auth
        if (auth.currentUser!= null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val btnGetstarted = findViewById<Button>(R.id.btnGetstarted)
        btnGetstarted.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}