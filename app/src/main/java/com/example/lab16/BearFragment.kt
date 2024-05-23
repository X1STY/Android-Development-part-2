package com.example.lab16

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class BearFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bear, container, false)

        val navController = findNavController()

        val bearButtonRun = view.findViewById<Button>(R.id.bearButtonRun)
        val bearButtonKeep = view.findViewById<Button>(R.id.bearButtonKeep)

        bearButtonRun.setOnClickListener {
            navController.navigate(R.id.action_bearFragment_to_foxFragment)
        }
        bearButtonKeep.setOnClickListener {
            navController.navigate(R.id.action_bearFragment_to_finalBadFragment)
        }

        return view
    }
}