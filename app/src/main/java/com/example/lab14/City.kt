package com.example.lab14


import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var listView: RecyclerView
private var state: Parcelable? = null

class City : Fragment(R.layout.fragment_city) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.recycler_view)
        Common.initCities(this)
        val adapter = CitiesAdapter(Common.cities)
        adapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putInt("chosenID", it)
            parentFragmentManager.setFragmentResult("citySelected", bundle)
        }
        listView.adapter = adapter
    }
}