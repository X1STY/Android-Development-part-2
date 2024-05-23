package com.example.lab15

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductsAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private var onItemClickListener: ((Int) -> Unit)? = null
    fun setOnItemClickListener(f: (Int) -> Unit) { onItemClickListener = f }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_product, parent,false)
        val holder = ProductsViewHolder(itemView)
        itemView.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION)
                onItemClickListener?.invoke(pos)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.nameTV.text = products[position].name
        holder.quantityTV.text = "Количество: " + products[position].quantity
    }

    class ProductsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nameTV: TextView
        var quantityTV: TextView
        init {
            nameTV = itemView.findViewById(R.id.textViewName)
            quantityTV = itemView.findViewById(R.id.textViewQuantity)
        }
    }
}