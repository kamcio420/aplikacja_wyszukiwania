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
        var randomString = ""

        //RANDOM LETTERS
        fun randomLetters(amount: Int) {
            randomString = ""
            val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIKLMNOPRSTUVWXYZ123456789"
            for (i in 1..amount) {
                randomString += str.random()
            }
        }

        //BRUTE FORCE ALGORITHM
        fun bruteForce(str: CharArray, substr: CharArray): Int {
            if (substr.size > str.size) return 0

            var occurrences = 0

            for (cursor1 in 0 until str.size) {
                var matchCount = 0
                for (cursor2 in 0 until substr.size) {
                    if (str[cursor1 + cursor2] == substr[cursor2]) matchCount++
                }

                if (matchCount == substr.size) occurrences++
            }
            return occurrences
        }

        findViewById<Button>(R.id.uruchomButton).setOnClickListener {

            if(iloscInput.isNotEmpty() && wzorzecInput!="" )
            {
                findViewById<TextView>(R.id.errorText).text = ""
                randomLetters(parseInt(iloscInput))
                val wynik = bruteForce(randomString.toCharArray(), wzorzecInput.toCharArray())
                findViewById<TextView>(R.id.errorText).text = wynik.toString()
            }
            else
            {
                findViewById<TextView>(R.id.errorText).text = "Wprowadź poprawne dane!"
            }
        }
    }
}