package com.example.managemev2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Write to the database
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("message")
//        myRef.push().setValue("Hello")

        //val user_email = intent.getStringExtra("EMAIL_NAME")

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)
        //getSupportActionBar()?.setTitle(user_email);
        getSupportActionBar()?.setTitle("Already seen");
    }

    //Menu options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_manu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        //Logout button clicked on the top bar
        if (id == R.id.logout_action){

            if(firebaseAuth !=null) {
                firebaseAuth.signOut()
                firebaseAuth.addAuthStateListener {
                    if(firebaseAuth.currentUser == null){
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            return true
        }

        //Search button clicked on the top bar
        else if(id == R.id.search_action){
            var intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            return true
        }

        //Already seen button option on the top bar in the menu option
        else{
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this,"Already seen clicked",Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
