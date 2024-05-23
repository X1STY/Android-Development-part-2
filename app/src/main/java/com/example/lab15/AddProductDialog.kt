package com.example.lab15

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.FieldPosition


class AddProductDialog: DialogFragment() {
    lateinit var listener: DataListener
    private lateinit var name: String
    private lateinit var quantity: String
    private var position: Int = -1
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("name", "") ?: ""
        quantity = arguments?.getString("quantity", "") ?: ""
        position = arguments?.getInt("pos", -1) ?: -1
        Log.d("@@@", name + " | " + quantity)
        if (name.isNotBlank() || quantity.isNotBlank() || position != -1) isEditMode = true
    }

    interface DataListener {
        fun onDialogData(name: String, quantity: String)
        fun onDialogDataItemChanged(name: String, quantity: String, position: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DataListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_product, null)

        val nameEditText = dialogView.findViewById<EditText>(R.id.textProductNaming)
        val quantityEditText = dialogView.findViewById<EditText>(R.id.textProductQuantity)

        nameEditText.setText(name)
        quantityEditText.setText(quantity)

        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Добавить продукт")
            .setView(dialogView)
            .setPositiveButton(if (!isEditMode) "Добавить" else "Изменить") { _, _ ->
                name = nameEditText.text.toString()
                quantity = quantityEditText.text.toString()
                if (name.isNotBlank() && quantity.isNotBlank()) {
                    if (!isEditMode) listener.onDialogData(name, quantity)
                    if (isEditMode) listener.onDialogDataItemChanged(name, quantity, position)
                }
                else {
                    Toast.makeText(context, "No Text Entered in one of fields", Toast.LENGTH_SHORT).show();
                }
            }
            .setNegativeButton("Отмена") { _, _ ->
            }

        return builder.create()
    }

}