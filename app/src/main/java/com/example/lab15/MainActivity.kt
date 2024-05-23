package com.example.lab15

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.lab15.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), AddProductDialog.DataListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        val adapter = ProductsAdapter(data)
        adapter.setOnItemClickListener {
            val bundle: Bundle = Bundle()
            bundle.putString("name", data[it].name)
            bundle.putString("quantity", data[it].quantity)
            bundle.putInt("pos", it)
            val dialog = AddProductDialog()
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "edit_product_dialog")
        }
        binding.list.adapter = adapter

        val swipeHelper = ItemTouchHelper(swipeCallback)
        swipeHelper.attachToRecyclerView(binding.list)

        binding.fabAdd.setOnClickListener {
            val dialog = AddProductDialog()
            dialog.show(supportFragmentManager, "add_product_dialog")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDialogData(name: String, quantity: String) {
        data.add(Product(name, quantity))
        binding.list.adapter?.notifyItemInserted(data.size-1)
        Log.d("@", name + " | " + quantity)
    }

    override fun onDialogDataItemChanged(name: String, quantity: String, position: Int) {
        data[position].name = name
        data[position].quantity = quantity
        binding.list.adapter?.notifyItemChanged(position)
    }

    val swipeCallback = object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT + ItemTouchHelper.RIGHT) {
        // Перетскивание не требуется, поэтому возвращаем false
        override fun onMove(recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return false
        }
        // Смахивание – удаляем элемент и оповещаем об этом адаптер
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            data.removeAt(position)
            binding.list.adapter?.notifyItemRemoved(position)
        }
    }

}

