package com.smirnov.zoostore.ui.animal

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import com.smirnov.zoostore.R
import com.smirnov.zoostore.core.getArgs
import com.smirnov.zoostore.core.navigateUp
import com.smirnov.zoostore.databinding.AnimalActivityBinding

@Parcelize
class AnimalActivityArguments(
    val animalId: Long = -1L,
) : Parcelable

@AndroidEntryPoint
class AnimalActivity : AppCompatActivity() {

    private val viewModel: AnimalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AnimalActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args: AnimalActivityArguments? = getArgs()
        if (args != null) {
            viewModel.initWithArgs(args)
        }

        binding.animalBackIcon.setOnClickListener {
            navigateUp()
        }

        binding.animalCartBtn.setOnClickListener {
            viewModel.onCartClick()
        }

        viewModel.animal.observe(this) { animal ->
            with(binding) {
                Glide.with(animalImage)
                    .load(animal.imageUrl)
                    .into(animalImage)

                animalTitleText.text = animal.name
                animalPriceText.text = getString(R.string.price, animal.price)
                animalAgeText.text = animal.age.toString() + " года"
                animalDescriptionText.text = animal.description

                if (animal.inCart) {
                    animalCartBtn.setImageResource(R.drawable.ic_cart_remove)
                    animalCartBtn.background.setTint(getColor(R.color.red))
                } else {
                    animalCartBtn.setImageResource(R.drawable.ic_cart_add)
                    animalCartBtn.background.setTint(getColor(R.color.secondary_background))
                }
            }
        }
    }
}