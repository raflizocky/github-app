package com.zocky.githubapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zocky.githubapp.R
import com.zocky.githubapp.data.pref.SettingPreferences
import com.zocky.githubapp.data.pref.dataStore
import com.zocky.githubapp.data.response.ItemsItem
import com.zocky.githubapp.databinding.ActivityMainBinding
import com.zocky.githubapp.ui.ViewModelFactory
import com.zocky.githubapp.ui.detail.DetailActivity
import com.zocky.githubapp.ui.favorite.FavoriteActivity
import com.zocky.githubapp.ui.theme.ThemeActivity
import com.zocky.githubapp.ui.theme.ThemeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val githubAdapter = MainAdapter { user -> onUserItemClick(user) }
    private var isDataFound = true
    private var snackbar: Snackbar? = null

    private var searchCache: List<ItemsItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //theme
        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel: ThemeViewModel by viewModels {
            ViewModelFactory(application, pref)
        }
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean? ->
            isDarkModeActive?.let {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        setupRecyclerView()
        setupViews()

        if (searchCache == null) {
            binding.progressBar.visibility = View.VISIBLE
            mainViewModel.searchUsers("arif")
        } else {
            githubAdapter.submitList(searchCache)
            binding.progressBar.visibility = View.GONE
        }

        mainViewModel.users.observe(this) { users ->
            if (searchCache == null) {
                searchCache = users
            }

            githubAdapter.submitList(users)


            if (users.isEmpty()) {
                isDataFound = false
                binding.progressBar.visibility = View.VISIBLE
                snackbar = Snackbar.make(
                    binding.root,
                    "Data not found. Check your search keywords or internet connection.",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar?.setAction("OK") {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                snackbar?.show()
            } else {
                isDataFound = true
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.progressBar.visibility = View.GONE
                    snackbar?.dismiss()
                }, 1000)
            }
        }
    }

    private fun setupViews() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchView.text.toString()
                    if (query.isNotEmpty()) {
                        binding.progressBar.visibility = View.VISIBLE
                        mainViewModel.searchUsers(query)
                    }
                    searchView.hide()
                }
                false
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = githubAdapter
    }

    private fun onUserItemClick(user: ItemsItem) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
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
