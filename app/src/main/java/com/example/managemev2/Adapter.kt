package com.example.managemev2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter: RecyclerView.Adapter<MyViewHolder>() {

    val movieList = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val  inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View  = inflater.inflate(R.layout.item_row, parent, false)
        var row : LinearLayout = view.findViewById(R.id.single_row)

        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return movieList.size();
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        TODO("Not yet implemented")

         var movie : Movie = movieList[position]
        holder.movieTitle.text = movie.title
        holder.movieDirector.text = movie.director
        holder.movieGenre.text = movie.genre
        holder.movieYear.text = movie.productionYear
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        val movieTitle : TextView = view.findViewById(R.id.MovieName)
        val movieDirector : TextView = view.findViewById(R.id.MovieDirector)
        val movieGenre : TextView = view.findViewById(R.id.MovieGenre)
        val movieYear : TextView = view.findViewById(R.id.MovieProductionYear)

    }
}

//class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)