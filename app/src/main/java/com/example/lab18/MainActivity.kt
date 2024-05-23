package com.example.lab18

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab18.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.URL
import java.nio.charset.Charset
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.rateView.visibility = View.GONE

            CoroutineScope(Dispatchers.Main).launch {
                val rates = getRates()
                Log.d("asd", rates.toString())
                val adapter = RateAdapter(rates)
                binding.rateView.adapter = adapter

                binding.progressBar.visibility = View.GONE
                binding.rateView.visibility = View.VISIBLE
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

suspend fun getRates(): List<RateClass> {
    val xml = CoroutineScope(Dispatchers.IO).async {
        URL("https://www.cbr.ru/scripts/XML_daily.asp")
            .readText(Charset.forName("Windows-1251"))
    }.await()

    return parseXml(xml)
}

fun parseXml(xml: String): List<RateClass> {
    val rates = mutableListOf<RateClass>()

    val factory = XmlPullParserFactory.newInstance()
    val parser = factory.newPullParser()
    parser.setInput(StringReader(xml))

    var eventType = parser.eventType
    var name: String? = null
    var rate: Double? = null

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                when (parser.name) {
                    "Valute" -> {
                        name = ""
                        rate = null
                    }
                    "Name" -> name = parser.nextText()
                    "Value" -> {
                        val rateString = parser.nextText().replace(',', '.')
                        rate = rateString.toDoubleOrNull()
                    }
                }
            }
            XmlPullParser.END_TAG -> {
                if (parser.name == "Valute") {
                    if (name != null && rate != null) {
                        rates.add(RateClass(name, rate))
                    }
                }
            }
        }
        eventType = parser.next()
    }

    return rates
}

