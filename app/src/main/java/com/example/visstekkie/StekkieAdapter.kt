package com.example.visstekkie

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup

/**
 * Default adapter class for the RecyclesView
 */
class StekkieAdapter(
        private var modelArraylist: ArrayList<StekkieModel>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<StekkieAdapter.Viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        //Inflate layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        //To set data to textview and imageview of each card layout
        val model: StekkieModel = modelArraylist[position]

        holder.stekkeName.text = model.name
        holder.stekkieDesc.text = model.description
        holder.stekkieLoc.text = model.getLocationName(holder.stekkieLoc.context) //Get context from TV
        model.image?.let { holder.stekkieImg.setImageResource(it) }
    }

    override fun getItemCount(): Int {
        //Shows number of card items in recycler view
        return modelArraylist.size
    }

    /**
     * Holder class for initializing of the views
     * Also handles the onClick.
     */
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var stekkieImg: ImageView = itemView.findViewById(R.id.stekkie_img)
        var stekkeName: TextView = itemView.findViewById(R.id.stekkie_name)
        var stekkieDesc: TextView = itemView.findViewById(R.id.stekkie_desc)
        var stekkieLoc: TextView = itemView.findViewById(R.id.stekkie_loc)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //adapterPosition returns current adapt position (Int)
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(position: Int)
}