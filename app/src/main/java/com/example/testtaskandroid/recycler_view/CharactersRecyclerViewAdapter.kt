package com.example.testtaskandroid.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtaskandroid.R
import com.example.testtaskandroid.databinding.ItemInRecyclerViewBinding
import com.example.testtaskandroid.fragments.CharactersViewModel
import javax.inject.Inject

class CharactersRecyclerViewAdapter @Inject constructor(var viewModel: CharactersViewModel, var placeholderCharacters: PlaceholderCharacters) : RecyclerView.Adapter<CharactersRecyclerViewAdapter.MyViewHolder>() {

    private lateinit var context: Context
    private val idWhiteUnknown = R.drawable.white_circle
    private val idRedDead = R.drawable.red_circle
    private val idGreenAlive = R.drawable.green_circle

    private lateinit var itemClickListener: ((pos: Int) -> Unit)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        context = parent.context
        return MyViewHolder(
            ItemInRecyclerViewBinding.inflate(
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

        val item = placeholderCharacters.CHARACTERS[position]

        holder.firstSeen.text = item.character.nameOfFirstEpisode
        holder.lastSeen.text = item.character.location.name
        holder.status.text = (" " + item.character.status + " - " + item.character.species)
        holder.name.text = item.character.name
        Glide.with(context)
            .load(item.character.image)
            .into(holder.image)

        when (item.character.status) {
            "unknown" -> {holder.status.setCompoundDrawablesWithIntrinsicBounds(idWhiteUnknown,0,0,0)}
            "Alive" -> {holder.status.setCompoundDrawablesWithIntrinsicBounds(idGreenAlive,0,0,0)}
            "Dead" -> {holder.status.setCompoundDrawablesWithIntrinsicBounds(idRedDead,0,0,0)}
        }
    }

    fun notifyDataInserted(countInserted: Int) {
        notifyItemRangeInserted(viewModel.countItems - countInserted, countInserted)
    }

    override fun getItemCount(): Int = placeholderCharacters.CHARACTERS.size


    fun setOnItemClickListener(onItemClickListener: (pos: Int) -> Unit) {
        this.itemClickListener = onItemClickListener
    }


    inner class MyViewHolder(binding: ItemInRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root){

        val firstSeen: TextView = binding.FirstSeenIn
        val lastSeen: TextView = binding.LastSeen
        val name: TextView = binding.Name
        val status: TextView = binding.StatusInItem
        val image: ImageView = binding.imageView
        init {
            binding.root.setOnClickListener{
                itemClickListener.invoke(adapterPosition)
            }
        }


        override fun toString(): String {
            return super.toString() + " {firstSeen = ${firstSeen.text}; lastSeen = ${lastSeen.text}; name = ${name.text}; status = ${status.text}; image = $image}"
        }
    }
}