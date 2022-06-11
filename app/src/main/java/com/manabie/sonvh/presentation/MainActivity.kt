package com.manabie.sonvh.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.manabie.sonvh.R
import com.manabie.sonvh.databinding.ActivityMainBinding
import com.manabie.sonvh.domain.GetTasksEvent
import com.manabie.sonvh.domain.MainViewModel
import com.manabie.sonvh.domain.TaskType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.appToolbar.title = "Todo tasks"
        supportFragmentManager.beginTransaction().replace(binding.container.id, TasksListFragment::class.java, null)
            .commit()
        binding.appToolbar.setOnMenuItemClickListener {
            if(it.itemId == R.id.create_task){
                CreateTaskFragment().show(supportFragmentManager, null)
                return@setOnMenuItemClickListener true
            }
            false
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.all_tasks -> {
                    mainViewModel.sendEvent(GetTasksEvent(TaskType.ALL))
                    return@setOnItemSelectedListener true
                }
                R.id.completed_tasks -> {
                    mainViewModel.sendEvent(GetTasksEvent(TaskType.COMPLETED))
                    return@setOnItemSelectedListener true
                }
                R.id.incompleted_tasks -> {
                    mainViewModel.sendEvent(GetTasksEvent(TaskType.INCOMPLETE))
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}