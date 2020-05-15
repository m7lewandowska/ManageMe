package com.example.managemev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = intent

        val mTitle = intent.getStringExtra("title")
        val mDirector = intent.getStringExtra("director")
        val mGenre = intent.getStringExtra("genre")
        val mYear = intent.getStringExtra("year")

        //edit_name_product.setText(name)
    }
}
