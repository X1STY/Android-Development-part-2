package com.example.lab16

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class FoxFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fox, container, false)

        val navController = findNavController()

        val foxButtonRun = view.findViewById<Button>(R.id.foxButtonRun)
        val foxButtonKeep = view.findViewById<Button>(R.id.foxButtonKeep)

        foxButtonRun.setOnClickListener {
            navController.navigate(R.id.action_foxFragment_to_finalGoodFragment)
        }
        foxButtonKeep.setOnClickListener {
            navController.navigate(R.id.action_foxFragment_to_finalBadFragment)
        }

        return view
    }
}