package com.doit.dotasky.fragment

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.doit.dotasky.R
import com.doit.dotasky.databinding.FragmentSignBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class SignFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignBinding

    private lateinit var navControl:NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvent()
    }

    private fun registerEvent() {

        binding.signUp.setOnClickListener {
            val email=binding.email.text.toString().trim()
            val pass=binding.psword.text.toString().trim()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener (
                    OnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(context, "Succuss", Toast.LENGTH_SHORT).show()
                            navControl.navigate(R.id.action_signFragment_to_homeFragment)
                        }else{
                            Toast.makeText(context, "Error occur", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
        binding.signIn.setOnClickListener {
            val email=binding.email.text.toString().trim()
            val pass=binding.psword.text.toString().trim()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(
                    OnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(context,"Long In Succussfully,Please Wait...",Toast.LENGTH_SHORT).show()
                            navControl.navigate((R.id.action_signFragment_to_homeFragment))
                        }else{
                            Toast.makeText(context,"Error Occured",Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }



    private fun init(view: View) {
        navControl= Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()

    }


}