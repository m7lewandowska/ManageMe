package com.example.managemev2

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

       // val intent = intent

        user_email = intent.getStringExtra("EMAIL_NAME")
        var login = user_email.split("@", ".")

        val mTitle = intent.getStringExtra("title")
        val mDirector = intent.getStringExtra("director")
        val mGenre = intent.getStringExtra("genre")
        val mYear = intent.getStringExtra("year")

        textView1.setText(mTitle)
        textView2.setText(mDirector)
        textView3.setText(mGenre)
        textView4.setText(mYear)


        //delete record from dataBase
        val firebase = FirebaseDatabase.getInstance()
       // databaseReference = firebase.getReference("22o2pl")
        databaseReference = firebase.getReference(login[0] + login[1] + login[2])

        BTNDelete.setOnClickListener{
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
                            record.child("productionYear").getValue().toString() == mYear) {
                                record.ref.removeValue()
                                Toast.makeText(applicationContext, "Deleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //end activity
                    finish()
                }
            })
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
