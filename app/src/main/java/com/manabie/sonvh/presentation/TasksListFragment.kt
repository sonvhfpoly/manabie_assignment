package com.manabie.sonvh.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.manabie.sonvh.databinding.FragmentListTasksBinding
import com.manabie.sonvh.domain.GetTasksEvent
import com.manabie.sonvh.domain.MainViewModel
import com.manabie.sonvh.domain.TaskType
import com.manabie.sonvh.domain.UpdateEvent
import com.manabie.sonvh.model.entity.TodoTasks

class TasksListFragment: Fragment(){
    private var _binding: FragmentListTasksBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: TasksListAdapter
    private val dataObserver = Observer<List<TodoTasks>> {
        adapter.submitList(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TasksListAdapter{taskId, isCompleted ->
            mainViewModel.sendEvent(UpdateEvent(taskId, isCompleted))
        }
        binding.rcvListTasks.adapter = adapter
        mainViewModel.observeData(viewLifecycleOwner, dataObserver)
    }
}