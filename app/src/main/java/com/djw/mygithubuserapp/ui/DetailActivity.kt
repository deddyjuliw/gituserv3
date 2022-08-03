package com.djw.mygithubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.djw.mygithubuserapp.R
import com.djw.mygithubuserapp.database.Result
import com.djw.mygithubuserapp.database.local.entity.User
import com.djw.mygithubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var username: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.app_nameDetail)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        username = intent.getStringExtra(EXTRA_USER) as String
        detailViewModel.getDetailUser(username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter

        supportActionBar?.elevation = 0f

        detailViewModel.getDetailUser(username).observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val user = result.data
                        setData(user)
                        binding.btnFavorite.setOnClickListener {
                            if (user.isFavorite) {
                                detailViewModel.deleteFavorite(user)
                            } else {
                                detailViewModel.saveFavorite(user)
                            }
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setData(user: User) {
        binding.apply {
            tvName.text = user.name
            tvUsername.text = user.username
            tvRepo.text = resources.getString(R.string.repo, user.repository)
            tvCompany.text = user.company
            tvLocation.text = user.location
            if (user.isFavorite) {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        btnFavorite.context,
                        R.drawable.ic_favorite_24
                    )
                )
            } else {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        btnFavorite.context,
                        R.drawable.ic_favorite_out_24
                    )
                )
            }
        }
        Glide.with(this)
                .load(user.avatar_url)
                .circleCrop()
                .into(binding.imageProfile)

        val countFollow = arrayOf(user.followers, user.following)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position], countFollow[position])
        }.attach()
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }
}