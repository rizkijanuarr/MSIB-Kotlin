package com.example.tugasweek9.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.tugasweek9.MainActivity
import com.example.tugasweek9.R
import com.example.tugasweek9.SingWithGoogle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // analytic
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object
    {val RC_GOOGLE_SINGIN = 1}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)



        //analityc 1
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM){
//            params(FirebaseAnalytics.Param.ITEM_ID,"ffDBEnWHRdUXfh0KLfn4o5dnRnL2")
//            params(FirebaseAnalytics.Param.ITEM_NAME,"TEST")
//        }


        // ini menuju ke halaman ForgotPasswordActivity yaa
        val txtForgotPassword = findViewById<TextView>(R.id.txtForgotPassword)
        txtForgotPassword.setOnClickListener {
            Intent(this, ForgotPasswordActivity::class.java).also {
                startActivity(it)
            }
        }

        // ini action button login yaa
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {

            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, "123")
                putString(FirebaseAnalytics.Param.ITEM_NAME, "Test Item")
                putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
            }
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
            loginUser()

//            val bundle = Bundle().apply {
//                putString(FirebaseAnalytics.Param.ITEM_ID, "123")
//                putString(FirebaseAnalytics.Param.ITEM_NAME, "Test Item")
//                putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
//            }
//            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
            // analityc Login USERPASS
//            val bundle = Bundle()
//            bundle.putString(FirebaseAnalytics.Param.METHOD, "userpass")
//            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
            //analityc 2.2
        }

        // ini menuju ke action login Google yaaa
        val btnGoogle = findViewById<SignInButton>(R.id.btnGoogle)
        btnGoogle.setOnClickListener {
            val singInIntent = googleSignInClient.signInIntent
            startActivityForResult(singInIntent, RC_GOOGLE_SINGIN)

            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.ITEM_ID, "123")
                putString(FirebaseAnalytics.Param.ITEM_NAME, "Test Item")
                putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
            }
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

            //Analytic debugview
//            val bundle = Bundle()
//            bundle.putString("loginGoogle","Login Google")
//            firebaseAnalytics.logEvent("login_google", bundle)

        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webclient_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)



        // ini menuju ke halaman RegisterActivity yaa
        val txtRegister = findViewById<TextView>(R.id.txtRegister)
        txtRegister.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

        // inisiasi firebase nya
        auth = Firebase.auth
    }

    private fun params(itemId: String, s: String) {

    }


    // ini login with google yaa
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SingWithGoogle.RC_GOOGLE_SINGIN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("nabila_tag", "firebaseAuthWithGoogle: ${account.idToken}")

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException){
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

                    Toast.makeText(this, "berhasil regist", Toast.LENGTH_SHORT).show()
//                    //analityc 2.1
//                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN){
//                        params(FirebaseAnalytics.Param.METHOD,"google")
//                  }
//                    //analityc Login GOOGLE
//                    val bundle = Bundle()
//                    bundle.putString(FirebaseAnalytics.Param.METHOD, "userpass")
//                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)

                    handleLogin()
                    Toast.makeText(
                        this,
                        "Berhasil Sign In ${user?.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // redirect ke halaman mainactivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    saveUserDataToFirestore(user?.uid ?: "", user?.email ?: "")
                } else {
                    Log.w("nabila_tag","singInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Gagal Sign In", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun loginUser() {
        val inputEmail = findViewById<TextInputLayout>(R.id.inputEmail).editText?.text.toString()
        val inputPassword =
            findViewById<TextInputLayout>(R.id.inputPassword).editText?.text.toString()


        val emailLayout = findViewById<TextInputLayout>(R.id.inputEmail)
        val passwordLayout = findViewById<TextInputLayout>(R.id.inputPassword)

        if (validateForm(
                emailLayout,
                passwordLayout,
                inputEmail,
                inputPassword
            )
        ) {
            showProgressBar()
            auth.signInWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        showToast(this, "Berhasil Login, yeayy!")
                        handleLogin()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        hideProgressBar()
                        saveUserDataToFirestore(auth.currentUser?.uid ?: "", inputEmail)
                    } else {
                        showToast(this, "Tidak bisa login!")
                        hideProgressBar()
                    }

                }
        }
    }

    private fun validateForm(
        emailLayout: TextInputLayout,
        passwordLayout: TextInputLayout,
        email: String,
        password: String
    ): Boolean {
        return when {

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailLayout.error = "Alamat email tidak valid"
                false
            }

            password.length < 6 -> {
                passwordLayout.error = "Kata sandi harus memiliki panjang minimal 6 karakter"
                false
            }
            else -> true
        }
    }

    private fun handleLogin() {
        // Your existing login code...

        // After the user is logged in:
        val userId = auth.uid
        val ref = FirebaseDatabase.getInstance().getReference("Users")

        ref.child(userId!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // The user is logging in for the first time, create the default data structure
                    val defaultData = mapOf(
                        "favorites" to mapOf(
                            "Now Playing" to mapOf("null" to true),
                            // Add other categories here...
                        )
                    )
                    ref.child(userId).setValue(defaultData)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error...
            }
        })
    }
    private fun saveUserDataToFirestore(userId: String, email: String) {
        // Access the Firestore instance
        val db = FirebaseFirestore.getInstance()

        // Define the data you want to save to Firestore
        val userData = hashMapOf(
            "userId" to userId,
            "email" to email
            // Add more fields as needed
        )

        // Specify the Firestore collection and document where you want to store the data
        val userCollection = db.collection("login")
        val userDocument = userCollection.document(userId) // You can use the user's unique ID as the document ID

        // Add the data to Firestore
        userDocument.set(userData)
            .addOnSuccessListener {
                // Data has been successfully saved to Firestore
                // You can perform any additional actions or navigate to the main activity here
                showToast(this, "Data saved to Firestore")
            }
            .addOnFailureListener { e ->
                // Handle the error if the data couldn't be saved
                showToast(this, "Error saving data to Firestore: ${e.message}")
            }
    }


}