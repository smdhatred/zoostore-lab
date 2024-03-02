package com.smirnov.zoostore.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.smirnov.zoostore.core.navigate
import com.smirnov.zoostore.core.navigateWithArgs
import com.smirnov.zoostore.databinding.HomeActivityBinding
import com.smirnov.zoostore.domain.models.AnimalModel
import com.smirnov.zoostore.ui.cart.CartActivity
import com.smirnov.zoostore.ui.animal.AnimalActivity
import com.smirnov.zoostore.ui.animal.AnimalActivityArguments
import com.smirnov.zoostore.ui.profile.ProfileActivity

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), AnimalsAdapter.Listener {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeProfileLayout.setOnClickListener {
            navigate<ProfileActivity>()
        }

        binding.homeCartIcon.setOnClickListener {
            navigate<CartActivity>()
        }

        val animalsAdapter = AnimalsAdapter(this)
        with(binding.homeAnimalRecycler) {
            layoutManager = GridLayoutManager(
                this@HomeActivity, 2,
            )
            adapter = animalsAdapter
            setHasFixedSize(true)
        }
        viewModel.animals.observe(this, animalsAdapter::setAnimals)

        viewModel.user.observe(this) { user ->
            binding.homeTitleText.text = user.firstName

            if (user.photoUrl != null) {
                Glide.with(binding.homeProfileIcon)
                    .load(user.photoUrl)
                    .circleCrop()
                    .into(binding.homeProfileIcon)
            }
        }
    }

    override fun onItemClick(item: AnimalModel) {
        navigateWithArgs<AnimalActivity, AnimalActivityArguments>(
            args = AnimalActivityArguments(
                animalId = item.id
            )
        )
    }
}