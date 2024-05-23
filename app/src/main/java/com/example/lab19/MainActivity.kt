package com.example.lab19

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab19.databinding.ActivityMainBinding
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Разрешения пока нет, нужно ли показать пояснения?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.READ_CONTACTS
                    )
                ) {
                    // Да, показываем пояснения пользователю
                    showPermissionExplanationDialog()
                } else {
                    // Пояснений не требуется, запрашиваем разрешение
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
                }
            } else {
                // Разрешение уже есть, выполняем действие
                val contacts = getContacts()
                binding.textView.text = contacts.toString()
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val contacts = getContacts()
            binding.textView.text = contacts.toString()
        } else {
            showPermissionDeniedDialog()
        }
    }


    fun getContacts(): List<String> {
        val result = mutableListOf<String>()
        val contentResolver: ContentResolver = this.contentResolver
        val cur = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null)
        if (cur != null) {
            val colName = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            while (cur.moveToNext()) {
                val name = cur.getString(colName)
                result.add(name)
            }
            cur.close()
        }
        return result
    }

    private fun showPermissionExplanationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Разрешение на доступ к контактам")
            .setMessage("Для получения списка контактов необходимо разрешение на доступ к контактам.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                // Запрос разрешения после того, как пользователь прочитает объяснение
                requestPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Отказано в доступе к контактам")
            .setMessage("Без разрешения на доступ к контактам функционал приложения ограничен. " +
                    "Вы можете предоставить разрешение в настройках приложения.")
            .setPositiveButton("Настройки") { dialog, _ ->
                dialog.dismiss()
                // Открываем настройки приложения для предоставления разрешения
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}

