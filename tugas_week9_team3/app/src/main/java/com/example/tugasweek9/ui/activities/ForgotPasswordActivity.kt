package com.example.tugasweek9.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.tugasweek9.MainActivity
import com.example.tugasweek9.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // ini action untuk btn submit menuju ke arah firebase nantinyaa
        val btnSubmit =  findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            resetPassword()
        }

        auth = Firebase.auth
    }

    private fun resetPassword() {
        val inputEmailUlangLayout = findViewById<TextInputLayout>(R.id.inputEmailUlang)
        val inputEmailUlang = inputEmailUlangLayout.editText?.text.toString()
        val txtCheckYourEmailBox = findViewById<TextView>(R.id.txtCheckYourEmailBox)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        if (validateForm(inputEmailUlang, inputEmailUlangLayout)) {
            showProgressBar()

            auth.sendPasswordResetEmail(inputEmailUlang).addOnCompleteListener { task ->
                hideProgressBar()

                if (task.isSuccessful) {
                    inputEmailUlangLayout.visibility = View.GONE
                    txtCheckYourEmailBox.visibility = View.GONE
                    btnSubmit.visibility = View.GONE
                } else {
                    inputEmailUlangLayout.error = "Gagal mengirim email reset kata sandi."
                }
            }
        }
    }

    private fun validateForm(email: String, inputEmailUlangLayout: TextInputLayout): Boolean {
        return when {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                inputEmailUlangLayout.error = "Alamat email tidak valid"
                false
            }
            else -> true
        }
    }

}