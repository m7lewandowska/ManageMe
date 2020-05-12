package com.example.managemev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(toolbar)
        //getSupportActionBar()?.setTitle(user_email);
        getSupportActionBar()?.setTitle("Search");

        recyclerView.layoutManager = GridLayoutManager(this,1)
        recyclerView.adapter


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

        else if(id == R.id.search_action){
            var intent = Intent(this, SearchActivity::class.java)
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
