package com.example.aplikacja_wyszukiwania

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.lang.Integer.parseInt
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //DECLARATIONS
        val errorOutput = findViewById<TextView>(R.id.errorText)
        val findOutput = findViewById<TextView>(R.id.findOutput)
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
                class StateMachine(val pattern: CharArray) {
                    var cursor = 0
                    fun add(character: Char) {
                        if (pattern[cursor] == character) cursor++
                        else cursor = 0
                    }
                    fun isMatch() = cursor == pattern.size
                    fun reset() {cursor = 0}
                }

                val stateMachine = StateMachine(substr)
                var numberOfOccurrences = 0

                for (cursor in 0 until str.size) {
                    stateMachine.add(str[cursor])
                    if (stateMachine.isMatch()) {
                        stateMachine.reset()
                        numberOfOccurrences++
                    }
                }

                return numberOfOccurrences
        }

        findViewById<Button>(R.id.uruchomButton).setOnClickListener {

            var iloscInput = findViewById<TextInputEditText>(R.id.iloscZnakowInput).text.toString()
            val wzorzecInput = findViewById<TextInputEditText>(R.id.wzorzecInput).text.toString()
            val bfOutput: Int

            if(iloscInput.isNotEmpty() && wzorzecInput!="")
            {
                errorOutput.text = ""
                if(parseInt(iloscInput) <= 10000) {
                    errorOutput.text = ""
                    randomLetters(parseInt(iloscInput))
                    val bruteForceTime = measureTimeMillis {
                        bfOutput = bruteForce(randomString.toCharArray(), wzorzecInput.toCharArray())
                    }
                    bruteForceOutput.text = bruteForceTime.toString()
                    findOutput.text = bfOutput.toString()
                }
                else
                {
                    errorOutput.text = "Ilość znaków nie może być większa od 10000!"
                }
            }
            else
            {
                errorOutput.text = "Wprowadź poprawne dane!"
            }
        }
    }
}