package com.zocky.githubapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zocky.githubapp.R
import com.zocky.githubapp.data.response.DetailResponse
import com.zocky.githubapp.databinding.ActivityDetailBinding
import com.zocky.githubapp.ui.detail.fragment.SectionsPagerAdapter
import com.zocky.githubapp.ui.favorite.FavoriteActivity
import com.zocky.githubapp.ui.theme.ThemeActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    private val TAG = "DetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        Log.d(TAG, "Username from Intent: $username")

        if (username != null) {
            detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

            val progressBar1 = binding.progressBar1
            val progressBar = binding.progressBar

            // detail user
            progressBar1.visibility = View.VISIBLE

            // followers, following
            progressBar.visibility = View.VISIBLE

            detailViewModel.getUserDetail(username)

            setupViewPager(username)

            detailViewModel.userDetail.observe(this) { userDetail ->
                progressBar1.visibility = View.GONE
                progressBar.visibility = View.GONE

                if (userDetail != null) {
                    Log.d(TAG, "User Detail Retrieved Successfully")
                    bindUserDetail(userDetail)

                    detailViewModel.checkFavoriteStatus(username)
                }
            }

            //fab
            detailViewModel.isFavorite.observe(this) { isFavorite ->
                Log.d(TAG, "isFavorite: $isFavorite")
                if (isFavorite) {
                    binding.fabAdd.setImageResource(R.drawable.ic_favorite_full)
                } else {
                    binding.fabAdd.setImageResource(R.drawable.ic_favorite_border)
                }
            }

            // check user favorite status
            detailViewModel.checkFavoriteStatus(username)

            binding.fabAdd.setOnClickListener {
                detailViewModel.toggleFavoriteStatus(username)
            }
        }

        // back button
        val actionBarTitle = "Detail User"
        supportActionBar?.title = actionBarTitle
    }

    private fun setupViewPager(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs

        // tab layout - view pager
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun bindUserDetail(userDetail: DetailResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(userDetail.avatarUrl)
                .into(imgUser)

            tvUserName.text = userDetail.name
            tvUserUsername.text = userDetail.login
            tvFollowersCount.text = userDetail.followers.toString()
            tvFollowingCount.text = userDetail.following.toString()
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

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }
}
