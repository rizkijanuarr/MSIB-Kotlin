package com.example.tugasweek9

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class SingWithGoogle : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object
    {val RC_GOOGLE_SINGIN = 1}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_with_google)

        // Inisiasi button
        val btnSingIn = findViewById<SignInButton>(R.id.btnSingWithGoogle)


        // Inisiasi untuk kebutuhan tombol sing in with google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclient_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //inisiasi firebase auth
        auth = Firebase.auth

        btnSingIn.setOnClickListener {
            val singInIntent = googleSignInClient.signInIntent
            startActivityForResult(singInIntent, RC_GOOGLE_SINGIN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SINGIN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("nabila_tag", "firebaseAuthWithGoogle: ${account.idToken}")

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e:ApiException){
                Log.e("nabila_tag", "error -> ${e.localizedMessage}")

            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        Log.d("nabila_tag", "token -> $idToken")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("nabila_tag", "singInWithCredential:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        this,
                        "Berhasil Sign In ${user?.displayName}",
                        Toast.LENGTH_SHORT).show()

                } else {
                    Log.w("nabila_tag","singInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Gagal Sign In", Toast.LENGTH_SHORT).show()
                }
            }

    }
}