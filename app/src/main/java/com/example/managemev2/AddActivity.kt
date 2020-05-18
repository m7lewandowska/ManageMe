package com.example.managemev2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.item_row.*
import java.lang.Double.parseDouble

class AddActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var user_email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        user_email = intent?.getStringExtra("EMAIL_NAME")!!
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Add new movie";

        buttonAdd.setOnClickListener{

            val mName = MovieTitle_Input.text.toString()
            val mDirector = MovieDirector_Input.text.toString()
            val mGenre = MovieGenre_Input.text.toString()
            val mYear = MovieYear_Input.text.toString()
            val mRating = MovieRating_Input.text.toString()

            val movie = Movie(mName, mDirector, mGenre, mYear, mRating)
            val login = user_email.split("@", ".")

            //Write to the database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference(login[0] + login[1] + login[2])

            if(mName != "" && mName.isNotEmpty() && mDirector != "" && mDirector.isNotEmpty() &&
                mGenre != "" && mGenre.isNotEmpty()){
                    if(mYear != "" && mYear.isNotEmpty() && mYear.toInt() > 1895 &&
                        mYear.toInt() < 2021){
                        if(mRating != "" && mRating.isNotEmpty() && mRating.toInt() >= 0 &&
                            mRating.toInt() <= 10){

                            myRef.push().setValue(movie)
                            MovieTitle_Input.text?.clear()
                            MovieDirector_Input.text?.clear()
                            MovieGenre_Input.text?.clear()
                            MovieYear_Input.text?.clear()
                            MovieRating_Input.text?.clear()

                            Toast.makeText(applicationContext, "Movie added", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(applicationContext, "Rating value from 0 to 10", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(applicationContext, "Year must be from 1895 to 2021", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(applicationContext, "All data must be filled", Toast.LENGTH_SHORT).show()
                }
    }
}
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_manu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        //Logout button clicked on the top bar
        if(id == R.id.logout_action){

            firebaseAuth.signOut()
            firebaseAuth.addAuthStateListener {
                if(firebaseAuth.currentUser == null){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            return true
        }

        //Add button clicked on the top bar
        else if(id == R.id.add_action){
            // var intent = Intent(this, AddActivity::class.java)
            val intent = Intent(this, AddActivity::class.java).apply {
                putExtra("EMAIL_NAME", user_email)
            }
            startActivity(intent)
            return true
        }

        //Already seen button option on the top bar in the menu option
        else{
            val intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("EMAIL_NAME", user_email)
            }
            startActivity(intent)
//            Toast.makeText(this,"Already seen clicked",Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
