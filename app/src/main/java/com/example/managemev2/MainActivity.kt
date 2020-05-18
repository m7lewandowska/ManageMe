package com.example.managemev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import android.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
          setSupportActionBar(toolbar)
        supportActionBar?.title = "ManageMe";

        buttonLogin.setOnClickListener() {
            val email = editTextMail4.text.toString()
            val password = editTextPassword4.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill all fields!!!", Toast.LENGTH_SHORT).show()
                editTextMail4.text?.clear()
                editTextPassword4.text?.clear()
            }
            else {
                signIn(email.toString(), password.toString())
            }
        }

        buttonCreateAccount.setOnClickListener() {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if(task.isSuccessful) {
                Toast.makeText(applicationContext, "User logged successfully", Toast.LENGTH_SHORT).show()
                //Create new Activity
                val intent = Intent(this, LoginActivity::class.java).apply {
                    val email_name = editTextMail4.text.toString()
                    putExtra("EMAIL_NAME", email_name)
                }
                    startActivity(intent)
                    editTextMail4.text?.clear()
                    editTextPassword4.text?.clear()
            }
            else{
                Toast.makeText(applicationContext, "Wrong data, please try again!!!", Toast.LENGTH_SHORT).show()
                editTextMail4.text?.clear()
                editTextPassword4.text?.clear()
            }
        }

    }

}
