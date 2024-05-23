package com.example.lab17.ui.music.pager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab17.databinding.FragmentMusicInsideBinding

class MusicInsideFragment(private val genre: String) : Fragment() {

    private var _binding: FragmentMusicInsideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMusicInsideBinding.inflate(inflater, container, false)
        val view: View = binding.root

       binding.musicInside.text = "Music / $genre"

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}