package com.smirnov.zoostore.ui.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.smirnov.zoostore.R
import com.smirnov.zoostore.core.navigateUp
import com.smirnov.zoostore.databinding.ProfileActivityBinding

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileBackIcon.setOnClickListener {
            navigateUp()
        }

        viewModel.user.observe(this) {user ->
            binding.profileNameText.text = user.fullName
            binding.profileGroupText.text = getString(R.string.profile_group, user.group)

            if (user.photoUrl != null) {
                Glide.with(binding.profilePhotoImage)
                    .load(user.photoUrl)
                    .circleCrop()
                    .into(binding.profilePhotoImage)
            }
        }
    }
}