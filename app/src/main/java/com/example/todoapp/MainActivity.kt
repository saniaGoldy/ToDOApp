package com.example.todoapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.todoapp.databinding.ActivityMainBinding


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
            showAlertDialog(view)
        }

        sharedViewModel.isYellowThemeSelected.observe(this) { yellowThemeSelected ->
            Log.d("MyApp", "MainActivity.ColourObserver: $yellowThemeSelected")
            binding.toolbar.setTitleTextColor(
                if (yellowThemeSelected) {
                    getColor(R.color.yellow)
                } else {
                    getColor(R.color.white)
                }
            )
        }
    }

    private fun showAlertDialog(view: View) {
        val alertView = layoutInflater.inflate(
            R.layout.fragment_add_to_do_task,
            null
        )

        AlertDialog.Builder(this)
            .setTitle("Create new ToDo Task")
            .setView(alertView)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.apply)) { _, _ ->
                sharedViewModel.addAListEntry(alertView.findViewById<EditText>(R.id.editText).text.toString())
            }.create().show()
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