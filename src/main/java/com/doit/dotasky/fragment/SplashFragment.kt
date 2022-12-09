package com.doit.dotasky.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.doit.dotasky.R
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {
    private lateinit var auth:FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth= FirebaseAuth.getInstance()
        navController= Navigation.findNavController(view)
        val isLogin:Boolean=auth.currentUser!=null
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            if(isLogin){
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }else {
                navController.navigate(R.id.action_splashFragment_to_signFragment)
            }
        },2000)
    }
}