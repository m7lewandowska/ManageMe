package com.example.managemev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLogin.setOnClickListener()
        {
            var email = editTextMail.text.toString()
            var password = editTextPassword.text.toString()

            signIn(email, password)
        }

        buttonCreateAccount.setOnClickListener()
        {
            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext, "User logged successfully", Toast.LENGTH_LONG).show()
                //Create new Activity
                var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
            }

        }

    }
}
