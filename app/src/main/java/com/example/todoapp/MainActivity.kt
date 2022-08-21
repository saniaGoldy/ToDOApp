package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.todoapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel:SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.addItemFloatingButton.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.addItemFloatingButton)
                .setAction("Action", null).show()
        }

        sharedViewModel.isYellowThemeSelected.observe(this) { yellowThemeSelected ->
            binding.toolbar.setTitleTextColor(
                if (yellowThemeSelected) {
                    getColor(R.color.yellow)
                } else {
                    getColor(R.color.white)
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_switch_colors -> {
                sharedViewModel.switchColorPrefs()
                reload()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
}