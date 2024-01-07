package com.zocky.githubapp.ui.theme

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.zocky.githubapp.data.pref.SettingPreferences
import com.zocky.githubapp.data.pref.dataStore
import com.zocky.githubapp.databinding.ActivityThemeBinding
import com.zocky.githubapp.ui.ViewModelFactory

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val themeViewModel = ViewModelProvider(this, ViewModelFactory(application, SettingPreferences.getInstance(application.dataStore)))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            AppCompatDelegate.setDefaultNightMode(if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
            binding.switchTheme.isChecked = isDarkModeActive
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            themeViewModel.saveThemeSetting(isChecked)
        }

        title = "Theme"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
