package com.example.managemev2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //var intent = getIntent()
        var mail = intent.getStringExtra("EMAIL_NAME")
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)

        //getSupportActionBar()?.setTitle(user_email);
        getSupportActionBar()?.setTitle("Add new movie");

        buttonAdd.setOnClickListener {

            var mName = MovieTitle_Input.text.toString()

            var mDirector = MovieDirector_Input.text.toString()
            var mGenre = MovieGenre_Input.text.toString()
            var mYear = MovieYear_Input.text.toString()

            var movie = Movie(mName, mDirector, mGenre, mYear)

            var login = mail.split("@")

//          Write to the database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference(login[0])
            myRef.push().setValue(movie)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_manu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        if (id == R.id.logout_action){

            if(firebaseAuth !=null) {
                firebaseAuth.signOut()
                firebaseAuth.addAuthStateListener {
                    if(firebaseAuth.currentUser == null){
//                        this.finish()
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            return true
        }

        else if(id == R.id.add_action){
            var intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            return true
        }

        else{
//            Toast.makeText(this,"Already seen clicked", Toast.LENGTH_SHORT).show
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
