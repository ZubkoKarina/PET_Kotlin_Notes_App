package com.example.to_doapp.utils.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.to_doapp.R
import com.example.to_doapp.databinding.EachTodoItemBinding
import com.example.to_doapp.utils.model.ToDoData
import java.text.SimpleDateFormat
import java.util.Date

class TaskAdapter(private var list: MutableList<ToDoData>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private val TAG = "TaskAdapter"
    private var listener: TaskAdapterInterface? = null
    var isSelectionMode = false
    fun setListener(listener: TaskAdapterInterface) {
        this.listener = listener
    }
    class TaskViewHolder(val binding: EachTodoItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTask.text = this.task
                val sdf = SimpleDateFormat("HH:mm")
                val date = Date(this.timestamp)
                binding.textTime.text = sdf.format(date)
                binding.selectionCircle.visibility = if (isSelectionMode) View.VISIBLE else View.GONE

                val backgroundDrawableRes = when {
                    list.size == 1 -> R.drawable.rounded_corners
                    position == 0 -> R.drawable.top_rounded_corners
                    position == list.size - 1 -> R.drawable.bottom_rounded_corners
                    else -> R.drawable.sharp_corners
                }
                binding.root.setBackgroundResource(backgroundDrawableRes)



                binding.editTask.setOnClickListener {
                    listener?.onEditItemClicked(this, position)
                }
                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteItemClicked(this, position)
                }
            }
        }
    }








    fun getSelectedItems(): List<ToDoData> {
        return list.filter { it.isSelected }
    }
    fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ToDoData>) {
        list = newList.toMutableList()
        notifyDataSetChanged()
    }
    interface TaskAdapterInterface{
        fun onDeleteItemClicked(toDoData: ToDoData, position: Int)
        fun onEditItemClicked(toDoData: ToDoData, position: Int)
    }
}
