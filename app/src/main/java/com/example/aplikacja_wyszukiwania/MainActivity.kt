package com.example.aplikacja_wyszukiwania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.uruchomButton).setOnClickListener {
            val iloscInput = findViewById<TextInputEditText>(R.id.iloscZnakowInput)
            val wzorzecInput = findViewById<TextInputEditText>(R.id.wzorzecInput)

            if(parseInt(iloscInput.toString()) > 0 && wzorzecInput.length() >= 1)
            {
                val bruteForceOutput = findViewById<TextView>(R.id.bruteForceOutput)
                val kmpOutput = findViewById<TextView>(R.id.kmpOutput)
                val bmOutput = findViewById<TextView>(R.id.bmOutput)
                val rkOutput = findViewById<TextView>(R.id.rkOutput)
            }
            else
            {
                findViewById<TextView>(R.id.errorText).text = "Wprowadź poprawne dane!"
            }
        }
    }
}