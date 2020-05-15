package com.example.managemev2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {

    private lateinit var adapter: Adapter
    lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var listOfMovies: MutableList<Movie>

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var user_email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       recyclerView = findViewById(R.id.activity_login_recyclerView_filmList)

        //adapter = Adapter(listOfMovies, this)
       // recyclerView.adapter = adapter

        // Write to the database
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("message")
//        myRef.push().setValue("Hello")

        user_email = intent.getStringExtra("EMAIL_NAME")
        var login = user_email.split("@", ".")

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)
        //getSupportActionBar()?.setTitle(user_email);
        getSupportActionBar()?.setTitle("Already seen");


       // recyclerView.adapter = Adapter(listOfMovies, this)

        val fireBase = FirebaseDatabase.getInstance()
        databaseReference = fireBase.getReference(login[0] + login[1] + login[2])
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 1)


        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError){
            }
            override fun onDataChange(dataSnapshot: DataSnapshot){
                listOfMovies = ArrayList()

                for(row in dataSnapshot.children){
                    val newRow = row.getValue(Movie::class.java)
                    listOfMovies.add(newRow!!)
                }
                setupAdapter(listOfMovies)
            }
        })



    }

    private fun setupAdapter(mutableData:MutableList<Movie>){
        recyclerView.adapter = Adapter(mutableData, this)
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
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this,"Already seen clicked",Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

