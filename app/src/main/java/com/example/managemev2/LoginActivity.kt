package com.example.managemev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    //var firebaseUser: FirebaseUser = firebaseAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogout.setOnClickListener()
        {
            if(firebaseAuth !=null)
            {
                firebaseAuth.signOut()

                firebaseAuth.addAuthStateListener {
                    if(firebaseAuth.currentUser == null){
                        this.finish()
                    }
                }
            }
        }
    }
}
