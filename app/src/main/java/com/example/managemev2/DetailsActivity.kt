package com.example.managemev2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var databaseReference: DatabaseReference
    var user_email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //Toolbar name at the top
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Movie details";

        user_email = intent?.getStringExtra("EMAIL_NAME")!!
        val login = user_email.split("@", ".")

        val mTitle = intent.getStringExtra("title")
        val mDirector = intent.getStringExtra("director")
        val mGenre = intent.getStringExtra("genre")
        val mYear = intent.getStringExtra("year")
        val mRating = intent.getStringExtra("rating")
        val mWatched = intent.getStringExtra("watched")

        if(mWatched == "1"){
            checkBox_watched.setChecked(true)
        }
        else {
            checkBox_watched.setChecked(false)
        }

        textView1.setText(mTitle)
        textView2.setText(mDirector)
        textView3.setText(mGenre)
        textView4.setText(mYear)
        InputRating.setText(mRating)
        //textView7.setText(mRating)

        if(InputRating.text.length > 0 ){
            InputRating.isEnabled = false
            InputRating.background = null
            checkBox_watched.setEnabled(false);
        }
        else{
            InputRating.isEnabled = true
        }

        //delete record from dataBase
        val firebase = FirebaseDatabase.getInstance()
        databaseReference = firebase.getReference(login[0] + login[1] + login[2])
        val map_Movie: HashMap<String, Any> = HashMap()

        checkBox_watched.setOnClickListener {

            if (InputRating.text.isNotEmpty()){

                databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(applicationContext, "Database Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (record in dataSnapshot.children) {

                            if (record.child("title").getValue().toString() == mTitle &&
                                record.child("director").getValue().toString() == mDirector &&
                                record.child("genre").getValue().toString() == mGenre &&
                                record.child("productionYear").getValue().toString() == mYear &&
                                record.child("rating").getValue().toString() == mRating) {

                                if (record.child("watched").getValue().toString() == "1") {
                                    map_Movie["watched"] = "0"
                                    map_Movie["rating"] = ""
                                }
                                else {
                                    map_Movie["watched"] = "1"
                                    val currentRating = InputRating.text.toString()
                                    map_Movie["rating"] = currentRating
                                }
                                record.ref.updateChildren(map_Movie)
                                break
                            }
                        }
                        Toast.makeText(applicationContext,"Already watched movie updated",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
        }
            else{
                Toast.makeText(applicationContext,"Fill movie rating from 0 to 10",Toast.LENGTH_SHORT).show()
                checkBox_watched.setChecked(false)
            }
        }

        BTNDelete.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete movie")
            builder.setMessage("Are you sure to delete movie $mTitle?")
            builder.setPositiveButton("YES"){dialog, which ->
            databaseReference.addValueEventListener(object: ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError){
                    Toast.makeText(applicationContext, "Database Error", Toast.LENGTH_SHORT).show()
                }
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    for (record in dataSnapshot.children) {
                        //val movie = dataSnapshot.getValue(Movie::class.java)
                        if(record.child("title").getValue().toString() == mTitle &&
                            record.child("director").getValue().toString() == mDirector &&
                            record.child("genre").getValue().toString() == mGenre &&
                            record.child("productionYear").getValue().toString() == mYear &&
                            record.child("rating").getValue().toString() == mRating) {
                                record.ref.removeValue()
                                Toast.makeText(applicationContext, "Movie deleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //end activity
                    finish()
                }
            })
        }
            builder.setNeutralButton("Cancel"){_,_ -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
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
