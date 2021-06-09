package com.example.visstekkie

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Default adapter class for the RecyclesView
 */
class StekkieAdapter(
        private var modelArraylist: ArrayList<StekkieModel>,
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<StekkieAdapter.Viewholder>() {

    //Options used by Glide when inserting images into the view
    private lateinit var options: RequestOptions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        //Inflate layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)

        //Default Glide options
        options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.ph150)
            .error(R.drawable.ph150)
            .override(100,100)

        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        //To set data to textview and imageview of each card layout
        val stekkie: StekkieModel = modelArraylist[position]

        holder.stekkeName.text = stekkie.name
        holder.stekkieDesc.text = stekkie.description
        holder.stekkieLoc.text = stekkie.getLocationName(holder.stekkieLoc.context) //Get context from TV

        if (stekkie.imagePath != null) {
            Glide.with(holder.stekkieImg.context).load(stekkie.getImagePathUri()).apply(options).into(holder.stekkieImg)
        } else {
            Glide.with(holder.stekkieImg.context).load(R.drawable.ph150).apply(options).into(holder.stekkieImg)
        }
    }

    override fun getItemCount(): Int {
        //Shows number of card items in recycler view
        return modelArraylist.size
    }

    /**
     * Clear the image loaded by Glide to keep the ram usage low.
     * This can cause some jitter in the view animation.
     */
    override fun onViewRecycled(holder: Viewholder) {
        super.onViewRecycled(holder)
        Glide.with(holder.stekkieImg.context).clear(holder.stekkieImg)
    }

    /**
     * Updates the modelArraylist of this class with the given ArrayList.
     * Returns this so we can chain this method.
     */
    fun updateData(newModelArraylist: ArrayList<StekkieModel>): StekkieAdapter {
        modelArraylist = ArrayList()
        modelArraylist.addAll(newModelArraylist)
        return this
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