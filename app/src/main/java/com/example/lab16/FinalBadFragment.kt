package com.example.lab16

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class FinalBadFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_final_bad, container, false)

        val navController = findNavController()

        val restartButton = view.findViewById<Button>(R.id.finalBadButtonRestart)

        restartButton.setOnClickListener {
            navController.navigate(R.id.action_finalBadFragment_to_introFragment)
        }

        return view
    }
}