package com.zocky.githubapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zocky.githubapp.R
import com.zocky.githubapp.databinding.ActivityFavoriteBinding
import com.zocky.githubapp.data.pref.SettingPreferences
import com.zocky.githubapp.ui.ViewModelFactory
import com.zocky.githubapp.data.pref.dataStore
import com.zocky.githubapp.ui.theme.ThemeActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(this.dataStore)
        val factory = ViewModelFactory(application, pref)

        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        title = "Favorite User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        observeFavoriteUsers()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter()
        binding.rvFollow.layoutManager = LinearLayoutManager(this)
        binding.rvFollow.adapter = adapter
    }

    private fun observeFavoriteUsers() {
        viewModel.getAllFavoriteUsers().observe(this) { favoriteUsers ->
            adapter.submitList(favoriteUsers)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val favoriteMenuItem = menu?.findItem(R.id.action_favorite)
        favoriteMenuItem?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_theme -> {
                startActivity(Intent(this, ThemeActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}