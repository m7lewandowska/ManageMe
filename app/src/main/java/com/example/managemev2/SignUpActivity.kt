package com.example.managemev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.editTextMail
import kotlinx.android.synthetic.main.activity_sign_up.editTextPassword

class SignUpActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Sign Up";

        buttonSignUp.setOnClickListener() {
           // var email = editTextMail.text.toString()
            val email = editTextMail2.text.toString()
            val password = editTextPassword2.text.toString()


            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all fields!!!", Toast.LENGTH_SHORT).show()
                editTextMail2.text?.clear()
                editTextPassword2.text?.clear()
            }
            if(password.length < 6){
                Toast.makeText(applicationContext, "Minimum password length: 6 characters!!!", Toast.LENGTH_SHORT).show()
                editTextPassword2.text?.clear()
            }
            else {
                createAccount(email.toString(), password.toString())
            }
        }

        buttonBackLogin.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Toast.makeText(applicationContext, "User created successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
