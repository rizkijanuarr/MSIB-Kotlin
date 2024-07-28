package com.example.tugasweek9.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.tugasweek9.MainActivity
import com.example.tugasweek9.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // ini action untuk btn register yaaa
        val btnRegister =  findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            registerUser()
// analityc 1
//            val bundle = Bundle()
//            bundle.putString(FirebaseAnalytics.Param.METHOD, "Register")
//            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)

        }

        // ini menuju ke halaman LoginActivity yaaa
        val txtLogin = findViewById<TextView>(R.id.txtLogin)
        txtLogin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        // inisiasi firebase nya
        auth = Firebase.auth

    }

    private fun registerUser() {
        val inputName = findViewById<TextInputLayout>(R.id.inputName).editText?.text.toString()
        val inputEmail = findViewById<TextInputLayout>(R.id.inputEmail).editText?.text.toString()
        val inputPassword =
            findViewById<TextInputLayout>(R.id.inputPassword).editText?.text.toString()

        val nameLayout = findViewById<TextInputLayout>(R.id.inputName)
        val emailLayout = findViewById<TextInputLayout>(R.id.inputEmail)
        val passwordLayout = findViewById<TextInputLayout>(R.id.inputPassword)

        if (validateForm(
                nameLayout,
                emailLayout,
                passwordLayout,
                inputName,
                inputEmail,
                inputPassword
            )
        ) {
            showProgressBar()
            auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        showToast(this, "user id created successfully")
                        hideProgressBar()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        saveUserDataToFirestore(auth.currentUser?.uid ?: "", inputEmail)
                    } else {
                        showToast(this, "user id not created")
                        hideProgressBar()
                    }

                }
        }
    }

    private fun validateForm(
        nameLayout: TextInputLayout,
        emailLayout: TextInputLayout,
        passwordLayout: TextInputLayout,
        name: String,
        email: String,
        password: String
    ): Boolean {
        return when {
            name.isEmpty() -> {
                nameLayout.error = "Nama tidak boleh kosong"
                false
            }

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
        val userCollection = db.collection("register")
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