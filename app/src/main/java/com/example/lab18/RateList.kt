package com.example.lab18

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

data class RateClass(
    val name: String,
    val rate: Double
)

class RateAdapter(val rates: List<RateClass>) : RecyclerView.Adapter<RateAdapter.RatesHolder>() {
    class RatesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var rate: TextView
        init {
            name = itemView.findViewById(R.id.name)
            rate = itemView.findViewById(R.id.rate)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.exchange_rate_list, parent, false)
        val holder = RatesHolder(view)
        return holder
    }
    override fun onBindViewHolder(holder: RatesHolder, position: Int) {
        holder.name.text = rates[position].name
        holder.rate.text = DecimalFormat("#.##").format(rates[position].rate)
    }
    override fun getItemCount(): Int {
        return rates.size
    }
}