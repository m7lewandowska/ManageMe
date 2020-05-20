package com.example.managemev2

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class Adapter(private var movieList: MutableList<Movie>, val context: Context, var user_email: String): RecyclerView.Adapter<Adapter.MyViewHolder>() {

    //private var movieList = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val  inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View  = inflater.inflate(R.layout.item_row, parent, false)
        var row : LinearLayout = view.findViewById(R.id.single_row)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size;
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie : Movie = movieList[position]

        holder.movieTitle.text = movieList[holder.adapterPosition].title
        holder.movieDirector.text = movieList[holder.adapterPosition].director
        //holder.movieGenre.text = movieList[holder.adapterPosition].genre
        //holder.movieYear.text = movieList[holder.adapterPosition].productionYear
        holder.movieRating.text = movieList[holder.adapterPosition].rating

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java).apply {
               putExtra("EMAIL_NAME", user_email)
            }
            intent.putExtra("title", movie.title)
            intent.putExtra("director", movie.director)
            intent.putExtra("genre", movie.genre)
            intent.putExtra("year", movie.productionYear)
            intent.putExtra("rating", movie.rating)
            intent.putExtra("watched", movie.watched)

            ContextCompat.startActivity(context, intent,null)
        }
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle : TextView = view.findViewById(R.id.MovieName)
        val movieDirector : TextView = view.findViewById(R.id.MovieDirector)
       //val movieGenre : TextView = view.findViewById(R.id.MovieGenre)
       //val movieYear : TextView = view.findViewById(R.id.MovieProductionYear)
        val movieRating : TextView = view.findViewById(R.id.MovieRating)
    }

    fun setMovies(movies:MutableList<Movie>){
        movieList = movies
        notifyDataSetChanged()
    }
}