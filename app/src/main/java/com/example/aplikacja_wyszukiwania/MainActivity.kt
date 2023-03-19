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

        //DECLARATIONS
        val iloscInput = findViewById<TextInputEditText>(R.id.iloscZnakowInput).text.toString()
        val wzorzecInput = findViewById<TextInputEditText>(R.id.wzorzecInput).text.toString()
        val bruteForceOutput = findViewById<TextView>(R.id.bruteForceOutput)
        val kmpOutput = findViewById<TextView>(R.id.kmpOutput)
        val bmOutput = findViewById<TextView>(R.id.bmOutput)
        val rkOutput = findViewById<TextView>(R.id.rkOutput)

        findViewById<Button>(R.id.uruchomButton).setOnClickListener {

            if(iloscInput.isNotEmpty() && wzorzecInput!="" )
            {
                findViewById<TextView>(R.id.errorText).text = "git"
            }
            else
            {
                findViewById<TextView>(R.id.errorText).text = "Wprowadź poprawne dane!"
            }
        }
    }
}