package com.smirnov.zoostore.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smirnov.zoostore.R
import com.smirnov.zoostore.databinding.CartListItemBinding
import com.smirnov.zoostore.domain.models.AnimalModel

@SuppressLint("NotifyDataSetChanged")
class CartAnimalsAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<CartAnimalsAdapter.ViewHolder>() {

    private var animals: List<AnimalModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CartListItemBinding.inflate(inflater, parent, false)
        val viewHolder = ViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                listener.onItemClick(animals[viewHolder.bindingAdapterPosition])
            }
            cartListItemDeleteIcon.setOnClickListener {
                listener.onDeleteClick(animals[viewHolder.bindingAdapterPosition])
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
        private val binding: CartListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(animal: AnimalModel) = with(binding) {
            Glide.with(cartListItemLogoImage)
                .load(animal.imageUrl)
                .into(cartListItemLogoImage)
            cartListItemNameText.text = animal.name
            cartListItemPriceText.text = root.context.getString(R.string.price, animal.price)
        }
    }

    interface Listener {
        fun onItemClick(item: AnimalModel)
        fun onDeleteClick(item: AnimalModel)
    }
}