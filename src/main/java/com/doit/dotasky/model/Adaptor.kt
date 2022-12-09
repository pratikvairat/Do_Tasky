package com.doit.dotasky.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.doit.dotasky.databinding.TasklistBinding

class Adaptor(private val list:MutableList<Task>):RecyclerView.Adapter<Adaptor.TaskViewHolder>(){
    private  var listener:TaskOperation?=null
    fun setListener(listener:TaskOperation){
        this.listener=listener
    }
    inner class TaskViewHolder(val binding: TasklistBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding=TasklistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.Task.text=this.todo
                binding.remove.setOnClickListener {
                    listener?.OnTaskDelete(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface TaskOperation{
        fun OnTaskDelete(task:Task)
    }
}