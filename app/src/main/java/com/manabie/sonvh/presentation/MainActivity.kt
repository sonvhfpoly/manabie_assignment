package com.manabie.sonvh.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.manabie.sonvh.R
import com.manabie.sonvh.databinding.ActivityMainBinding
import com.manabie.sonvh.domain.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.appToolbar.title = "Todo tasks"
        supportFragmentManager.beginTransaction().replace(binding.container.id, ListTasksFragment::class.java, null)
            .commit()
        binding.appToolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.create_task){
                CreateTaskFragment().show(supportFragmentManager, null)
            }
            false
        }
    }

}