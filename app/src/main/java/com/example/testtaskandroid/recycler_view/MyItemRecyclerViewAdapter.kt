package com.example.testtaskandroid.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtaskandroid.R
import com.example.testtaskandroid.databinding.ItemInRecyclerVewBinding
import com.example.testtaskandroid.fragments.CharactersViewModel

class MyItemRecyclerViewAdapter(context: Context, viewModel: CharactersViewModel) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.MyViewHolder>() {

    private val fvv = context
    val viewModel = viewModel
    private val idWhiteUnknown = R.drawable.white_circle
    private val idRedDead = R.drawable.red_circle
    private val idGreenAlive = R.drawable.green_circle
    private lateinit var itemClickListener: OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            ItemInRecyclerVewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (position == viewModel.countItems - 1 && viewModel.pageCount!! > viewModel.currentPage){
            viewModel.appendCharacters()
        }

        val item = PlaceholderContent.ITEMS[position]

        holder.firstSeen.text = item.nameOfFirstEpisode
        holder.lastSeen.text = item.character.location.name
        holder.status.text = (" " + item.character.status + " - " + item.character.species)
        holder.name.text = item.character.name
        Glide.with(fvv)
            .load(item.character.image)
            .into(holder.image)

        when (item.character.status) {
            "unknown" -> {holder.status.setCompoundDrawablesWithIntrinsicBounds(idWhiteUnknown,0,0,0)}
            "Alive" -> {holder.status.setCompoundDrawablesWithIntrinsicBounds(idGreenAlive,0,0,0)}
            "Dead" -> {holder.status.setCompoundDrawablesWithIntrinsicBounds(idRedDead,0,0,0)}
        }
    }

    fun notifyAboutData() {
        notifyDataSetChanged()
        notifyItemRangeChanged(PlaceholderContent.ITEMS.size, itemCount)
    }

    override fun getItemCount(): Int = PlaceholderContent.ITEMS.size


    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }


    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }


    inner class MyViewHolder(binding: ItemInRecyclerVewBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        val firstSeen: TextView = binding.FirstSeenIn
        val lastSeen: TextView = binding.LastSeen
        val name: TextView = binding.Name
        val status: TextView = binding.StatusInItem
        val image: ImageView = binding.imageView
        init {
            binding.root.setOnClickListener(this)
        }


        override fun toString(): String {
            return super.toString() + " {firstSeen = ${firstSeen.text}; lastSeen = ${lastSeen.text}; name = ${name.text}; status = ${status.text}; image = $image}"
        }


        override fun onClick(p0: View?) {
            itemClickListener.onItemClick(p0, adapterPosition)
        }



    }


}