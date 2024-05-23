package com.example.lab14

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment


class DetailedCity: Fragment(R.layout.fragment_detailed_city) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Common.initCities(this)
        val tv = view.findViewById<TextView>(R.id.textView)
        val btn = view.findViewById<Button>(R.id.button)
        val chosenId = arguments?.getInt("chosenID") ?: -1

        tv.text = Common.CityToString(chosenId)
        btn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:${Common.cities[chosenId].lat},${Common.cities[chosenId].lon}")
            )
            startActivity(intent)
        }
    }
}