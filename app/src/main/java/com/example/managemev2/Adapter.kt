package com.example.managemev2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter: RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val  inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View  = inflater.inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

class MyViewHolder(val view: View): RecyclerView.ViewHolder(view)