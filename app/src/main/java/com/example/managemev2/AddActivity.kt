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
    var user_email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //var intent = getIntent()
        user_email = intent.getStringExtra("EMAIL_NAME")
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

            var login = user_email.split("@", ".")

//          Write to the database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference(login[0] + login[1] + login[2])
            myRef.push().setValue(movie)

            MovieTitle_Input.text?.clear()
            MovieDirector_Input.text?.clear()
            MovieGenre_Input.text?.clear()
            MovieYear_Input.text?.clear()
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

        //Add button clicked on the top bar
        else if(id == R.id.add_action){
            // var intent = Intent(this, AddActivity::class.java)

            var intent = Intent(this, AddActivity::class.java).apply {

                putExtra("EMAIL_NAME", user_email)
            }

            startActivity(intent)
            return true
        }

        //Already seen button option on the top bar in the menu option
        else{
            var intent = Intent(this, LoginActivity::class.java).apply {
                putExtra("EMAIL_NAME", user_email)
            }
            startActivity(intent)
//            Toast.makeText(this,"Already seen clicked",Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
