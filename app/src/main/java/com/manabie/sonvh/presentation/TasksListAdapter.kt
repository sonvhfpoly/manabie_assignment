package com.manabie.sonvh.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.manabie.sonvh.databinding.HolderTaskItemBinding
import com.manabie.sonvh.model.entity.TodoTasks

class TasksListAdapter(val onCompleteListener: (Int, Boolean) -> Unit) : ListAdapter<TodoTasks, TodoTaskViewHolder>(TodoTaskComparator()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoTaskViewHolder {
        return TodoTaskViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: TodoTaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onCompleteListener)
    }
}

class TodoTaskComparator: DiffUtil.ItemCallback<TodoTasks>(){
    override fun areItemsTheSame(oldItem: TodoTasks, newItem: TodoTasks): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: TodoTasks, newItem: TodoTasks): Boolean {
        return oldItem.isCompleted == newItem.isCompleted
    }
}

class TodoTaskViewHolder(private val binding: HolderTaskItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(todoTasks: TodoTasks, onCompleteListener: (Int, Boolean) -> Unit){
        binding.todoItem.isChecked = todoTasks.isCompleted
        binding.todoItem.text = todoTasks.task
        binding.todoItem.setOnCheckedChangeListener { compoundButton, checked ->
            if(compoundButton.isPressed) onCompleteListener(todoTasks.uid, checked)
        }
    }

    companion object{
        fun newInstance(parent: ViewGroup) : TodoTaskViewHolder{
            val binding = HolderTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TodoTaskViewHolder(binding)
        }
    }
}