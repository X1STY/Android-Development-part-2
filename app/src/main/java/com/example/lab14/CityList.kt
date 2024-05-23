package com.example.lab14

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

data class CityClass(
    val title: String,
    val region: String,
    val district: String,
    val postalCode: String,
    val timezone: String,
    val population: String,
    val founded: String,
    val lat: Float,
    val lon: Float
)

class CitiesAdapter(val citites: List<CityClass>) : RecyclerView.Adapter<CitiesAdapter.CityHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(f: (Int) -> Unit) { onItemClickListener = f }

    class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var region: TextView
        init {
            name = itemView.findViewById(R.id.name)
            region = itemView.findViewById(R.id.region)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.city_list, parent, false)
        val holder = CityHolder(view)
        view.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION)
                onItemClickListener?.invoke(pos)
        }
        return holder
    }
    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.name.text = citites[position].title
        holder.region.text = citites[position].region
    }
    override fun getItemCount(): Int {
        return citites.size
    }
}


object Common {
    val cities = mutableListOf<CityClass>()

    fun initCities(ctx: Fragment) {
        if (cities.isEmpty()) {
            val lines = ctx.resources.openRawResource(R.raw.cities).bufferedReader().lines().toArray()
            for (i in 1 until lines.count()) {
                val parts = lines[i].toString().split(";")
                val city = CityClass(
                    parts[3], // city
                    parts[2], // region
                    parts[1], // federal_district
                    parts[0], // postal_code
                    parts[4], // timezone
                    parts[7], // population
                    parts[8], // founded
                    parts[5].toFloat(), // lat
                    parts[6].toFloat()  // lon
                )
                cities.add(city)
            }
            cities.sortBy { it.title }
        }
    }
    fun CityToString(chosenId: Int): String {
        if (chosenId < 0 || chosenId > cities.count()) return "Ошибка выбора города."
        var city = cities[chosenId]
        return "Город: ${city.title}\n" +
                "Федеральный округ: ${city.district}\n" +
                "Регион: ${city.region}\n" +
                "Почтовый индекс: ${city.postalCode}\n" +
                "Часовой пояс: ${city.timezone}\n" +
                "Население: ${city.population}\n" +
                "Основан в: ${city.founded}"
    }
}