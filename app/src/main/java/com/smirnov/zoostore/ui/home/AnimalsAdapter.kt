package com.smirnov.zoostore.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smirnov.zoostore.R
import com.smirnov.zoostore.databinding.AnimalGridItemBinding
import com.smirnov.zoostore.domain.models.AnimalModel

@SuppressLint("NotifyDataSetChanged")
class AnimalsAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<AnimalsAdapter.ViewHolder>() {

    private var animals: List<AnimalModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AnimalGridItemBinding.inflate(inflater, parent, false)
        val viewHolder = ViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                listener.onItemClick(animals[viewHolder.bindingAdapterPosition])
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return animals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(animals[position])
    }

    fun setAnimals(value: List<AnimalModel>) {
        animals = value
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: AnimalGridItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: AnimalModel) = with(binding) {
            Glide.with(animalGripItemImage)
                .load(animal.imageUrl)
                .into(animalGripItemImage)
            animalGripItemNameText.text = animal.name
            animalGripItemPriceText.text = root.context.getString(R.string.price, animal.price)
        }
    }

    interface Listener {
        fun onItemClick(item: AnimalModel)
    }
}