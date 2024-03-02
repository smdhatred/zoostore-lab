package com.smirnov.zoostore.ui.cart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import com.smirnov.zoostore.R
import com.smirnov.zoostore.core.navigateUp
import com.smirnov.zoostore.core.navigateWithArgs
import com.smirnov.zoostore.databinding.CartActivityBinding
import com.smirnov.zoostore.domain.models.AnimalModel
import com.smirnov.zoostore.ui.animal.AnimalActivity
import com.smirnov.zoostore.ui.animal.AnimalActivityArguments

@AndroidEntryPoint
class CartActivity : AppCompatActivity(), CartAnimalsAdapter.Listener {

    private val viewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CartActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartBackIcon.setOnClickListener {
            navigateUp()
        }

        binding.cartBuyButton.setOnClickListener {
            viewModel.buyAnimals()
        }

        val animalsAdapter = CartAnimalsAdapter(this)
        with(binding.cartAnimalRecycler) {
            layoutManager = GridLayoutManager(
                context, RecyclerView.VERTICAL,
            )
            adapter = animalsAdapter
            setHasFixedSize(true)
        }
        viewModel.animals.observe(this) { animals ->
            animalsAdapter.setAnimals(animals)

            if (animals.isEmpty()) {
                binding.cartBuyButton.visibility = View.GONE
            } else {
                binding.cartBuyButton.visibility = View.VISIBLE
            }
        }

        viewModel.buySuccessEffect.observe(this) { sideEffect ->
            sideEffect.handle {
                Toast.makeText(this, R.string.cart_buy_success, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadAnimals()
    }

    override fun onItemClick(item: AnimalModel) {
        navigateWithArgs<AnimalActivity, AnimalActivityArguments>(
            args = AnimalActivityArguments(
                animalId = item.id
            )
        )
    }

    override fun onDeleteClick(item: AnimalModel) {
        viewModel.removeFromCart(item)
    }
}