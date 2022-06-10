package com.manabie.sonvh.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.manabie.sonvh.databinding.FragmentCreateTaskBinding
import com.manabie.sonvh.domain.Event
import com.manabie.sonvh.domain.InsertEvent
import com.manabie.sonvh.domain.TaskType
import com.manabie.sonvh.domain.MainViewModel
import com.manabie.sonvh.model.entity.TodoTasks

class CreateTaskFragment: DialogFragment() {

    private var _binding:FragmentCreateTaskBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreate.setOnClickListener {
            if(binding.textInputText.text.isNullOrEmpty()){
                binding.textInputLayout.error = "Task must not be empty"
            }else{
                val task = TodoTasks(
                    isCompleted = false,
                    task = binding.textInputText.text.toString()
                )
                val event = InsertEvent(task)
                mainViewModel.sendEvent(event)
                dismiss()
            }
        }
    }
}