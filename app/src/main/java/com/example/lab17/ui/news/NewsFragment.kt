package com.example.lab17.ui.news

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab17.databinding.FragmentNewsBinding
import com.google.android.material.badge.BadgeDrawable

class NewsFragment() : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var timer: CountDownTimer
    private lateinit var badge: BadgeDrawable
    private lateinit var btn: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        btn = binding.button
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        timer = newsViewModel.timerLiveData.value ?: return
        badge = newsViewModel.badgeLiveData.value ?: return
        btn.setOnClickListener {
            badge.number = 0
            badge.isVisible = false
        }
        timer.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timer.start()
    }
}