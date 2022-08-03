package com.djw.mygithubuserapp.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.djw.mygithubuserapp.ListUserAdapter
import com.djw.mygithubuserapp.R
import com.djw.mygithubuserapp.database.remote.response.ItemsItem
import com.djw.mygithubuserapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.app_favPage)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val favoriteViewModel: FavoriteViewModel by viewModels {
            factory
        }

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvFavorite.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        }
        binding.rvFavorite.setHasFixedSize(true)

        favoriteViewModel.getFavorite().observe(this) { user ->
            binding.progressBar.visibility = View.GONE
            val listUser = user.map {
                ItemsItem(it.avatar_url, it.username)
            }

            val adapter = ListUserAdapter(listUser as ArrayList<ItemsItem> /* = java.util.ArrayList<com.djw.mygithubpage.database.remote.response.ItemsItem> */)
            binding.rvFavorite.adapter = adapter
            adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    selectedUser(data)
                }
            })
        }
    }

    private fun selectedUser(user: ItemsItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user.login)
        startActivity(intent)
    }
}