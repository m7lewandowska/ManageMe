package com.example.managemev2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import kotlin.collections.ArrayList


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

        user_email = intent.getStringExtra("EMAIL_NAME")!!
        val login = user_email.split("@", ".")


        //Toolbar name at the top
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)
        //getSupportActionBar()?.setTitle(user_email);
        supportActionBar?.title = "Already seen";

        //FireBase get instance by
        val fireBase = FirebaseDatabase.getInstance()
        databaseReference = fireBase.getReference(login[0] + login[1] + login[2])
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 1)


        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError){
                Toast.makeText(applicationContext, "Database Error", Toast.LENGTH_SHORT).show()
            }
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot){
                listOfMovies = ArrayList()

                for(row in dataSnapshot.children){
                    val newRow = row.getValue(Movie::class.java)
                    listOfMovies.add(newRow!!)
                }


                if(listOfMovies.isNotEmpty())
                {
                    addFilm_info.text = ""

                }

                else
                {
                    addFilm_info.text = "Add new film to the list"
                }

                val sorted = listOfMovies.sortedByDescending { it.rating }
                setupAdapter(sorted.toMutableList())

            }
        })
    }

    private fun setupAdapter(mutableData:MutableList<Movie>){
        recyclerView.adapter = Adapter(mutableData, this, user_email)
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

