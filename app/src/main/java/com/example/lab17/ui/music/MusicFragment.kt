package com.example.lab17.ui.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab17.R
import com.example.lab17.databinding.FragmentMusicBinding
import com.example.lab17.ui.music.pager.MusicInsideFragment
import com.example.lab17.ui.music.pager.MusicViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pager = binding.viewPager2
        val tabs = binding.musicTab
        val adapter = MusicViewPagerAdapter(listOf(
            MusicInsideFragment(resources.getString(R.string.genre_rock)),
            MusicInsideFragment(resources.getString(R.string.genre_jazz)),
            MusicInsideFragment(resources.getString(R.string.genre_classical)),
            MusicInsideFragment(resources.getString(R.string.genre_chanson)),
            MusicInsideFragment(resources.getString(R.string.genre_blues)),
            MusicInsideFragment(resources.getString(R.string.genre_pop)),
            ),this)
        pager.adapter = adapter

        TabLayoutMediator(tabs, pager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.genre_rock)
                1 -> tab.text = resources.getString(R.string.genre_jazz)
                2 -> tab.text = resources.getString(R.string.genre_classical)
                3 -> tab.text = resources.getString(R.string.genre_chanson)
                4 -> tab.text = resources.getString(R.string.genre_blues)
                5 -> tab.text = resources.getString(R.string.genre_pop)
            }
        }.attach()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}