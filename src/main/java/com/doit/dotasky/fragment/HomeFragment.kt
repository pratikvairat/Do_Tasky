package com.doit.dotasky.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.EditText

import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.doit.dotasky.databinding.FragmentHomeBinding
import com.doit.dotasky.model.Adaptor
import com.doit.dotasky.model.Task
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class homeFragment : Fragment(), AddTaskFragment.taskBtnClickLintener, Adaptor.TaskOperation {
    private lateinit var taskList: MutableList<Task>
    private  lateinit var auth:FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var databaseRef:DatabaseReference
    private lateinit var binding:FragmentHomeBinding
    private lateinit var addTaskFragment: AddTaskFragment
    private lateinit var adapter: Adaptor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getDataFromFireBase()
        registerEvent()
    }

    private fun getDataFromFireBase() {
        databaseRef.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                    taskList.clear()
                    for(task in snapshot.children){
                        val taskdata=snapshot.key?.let {
                           Task(task.key.toString(),task.value.toString())
                        }
                        if(taskdata!=null){
                            taskList.add(taskdata)
                        }
                    }
                    adapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,"Error in database",Toast.LENGTH_SHORT).show()
                }

        })

    }

    private fun registerEvent() {
        binding.add.setOnClickListener{
            addTaskFragment= AddTaskFragment()
            addTaskFragment.setLinstener(this)
            addTaskFragment.show(childFragmentManager,"addTask")

        }
    }

    private fun init(view: View) {
        navController=Navigation.findNavController(view)
        auth= FirebaseAuth.getInstance()
        databaseRef=FirebaseDatabase.getInstance().reference.child("Tasks").child(auth.currentUser?.uid.toString())
        binding.TaskView.setHasFixedSize(true)
        binding.TaskView.layoutManager=LinearLayoutManager(context)
        taskList= mutableListOf()
        adapter= Adaptor(taskList)
        adapter.setListener(this)
        binding.TaskView.adapter=adapter

    }

    override fun OnSaveTask(todo: String, dotoEt: EditText) {
        databaseRef.push().setValue(todo).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(context,"Task Added",Toast.LENGTH_SHORT).show()
                dotoEt.text=null
            }else{
                Toast.makeText(context,"Error Occurred",Toast.LENGTH_SHORT).show()
            }
            addTaskFragment.dismiss()
        }
    }

    override fun OnTaskDelete(task: Task) {
        databaseRef.child(task.taskId).removeValue().addOnCompleteListener(
            OnCompleteListener{
                if(it.isSuccessful) {
                    Toast.makeText(context,"Removed the Task",Toast.LENGTH_SHORT).show()

                }else {
                    Toast.makeText(context,"Error in Database",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

