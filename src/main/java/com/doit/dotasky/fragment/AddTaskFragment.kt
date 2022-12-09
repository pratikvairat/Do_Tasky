package com.doit.dotasky.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.doit.dotasky.databinding.FragmentAddTaskBinding


class AddTaskFragment : DialogFragment() {
    private lateinit var binding:FragmentAddTaskBinding

    private lateinit var listener:taskBtnClickLintener
    fun setLinstener(listener: homeFragment){
        this.listener=listener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()
    }

    private fun registerEvents() {
        binding.submit.setOnClickListener {
            val TaskTxt=binding.taskTxt.text.toString()
            if(TaskTxt.isNotEmpty()){
                listener.OnSaveTask(TaskTxt,binding.taskTxt)

            }else{
                Toast.makeText(context,"Please Enter Task",Toast.LENGTH_SHORT).show()
            }

        }
        binding.close.setOnClickListener {
            dismiss()
        }
    }

    interface taskBtnClickLintener{
        fun OnSaveTask(todo:String,dotoEt:EditText)

    }


}