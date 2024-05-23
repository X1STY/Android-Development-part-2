package com.example.lab16

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class HareFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hare, container, false)

        val navController = findNavController()

        val hareButtonRun = view.findViewById<Button>(R.id.hareButtonRun)
        val hareButtonKeep = view.findViewById<Button>(R.id.hareButtonKeep)

        hareButtonRun.setOnClickListener {
            navController.navigate(R.id.action_hareFragment_to_wolfFragment)
        }
        hareButtonKeep.setOnClickListener {
            navController.navigate(R.id.action_hareFragment_to_finalBadFragment)
        }

        return view
    }

}