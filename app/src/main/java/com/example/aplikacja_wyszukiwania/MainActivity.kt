package com.example.aplikacja_wyszukiwania

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.lang.Integer.parseInt
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //DECLARATIONS
        val errorOutput = findViewById<TextView>(R.id.errorText)
        val bfOccurrencesOutput = findViewById<TextView>(R.id.bfOccurrencesOutput)
        val kpmOccurrencesOutput = findViewById<TextView>(R.id.kmpOccurrencesOutput)
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

                for (element in str) {
                    stateMachine.add(element)
                    if (stateMachine.isMatch()) {
                        stateMachine.reset()
                        numberOfOccurrences++
                    }
                }

                return numberOfOccurrences
        }

        //KMP ALGORITHM
        fun computeLPSArray(pattern: String): IntArray {
            val lps = IntArray(pattern.length)
            var len = 0
            var i = 1
            lps[0] = 0
            while (i < pattern.length) {
                if (pattern[i] == pattern[len]) {
                    len++
                    lps[i] = len
                    i++
                } else {
                    if (len != 0) {
                        len = lps[len - 1]
                    } else {
                        lps[i] = len
                        i++
                    }
                }
            }
            return lps
        }

        fun kmp(text: String, pattern: String): Int {
            val lps = computeLPSArray(pattern)
            var i = 0
            var j = 0
            while (i < text.length) {
                if (pattern[j] == text[i]) {
                    j++
                    i++
                }
                if (j == pattern.length) {
                    return i - j
                } else if (i < text.length && pattern[j] != text[i]) {
                    if (j != 0) {
                        j = lps[j - 1]
                    } else {
                        i++
                    }
                }
            }
            return -1
        }


        //URUCHOM BUTTON ACTION
        findViewById<Button>(R.id.uruchomButton).setOnClickListener {

            val iloscInput = findViewById<TextInputEditText>(R.id.iloscZnakowInput).text.toString()
            val wzorzecInput = findViewById<TextInputEditText>(R.id.wzorzecInput).text.toString()
            val bfOutputOccurrences: Int
            val kmpOutputOccurrences: Int

            if(iloscInput.isNotEmpty() && wzorzecInput!="")
            {
                errorOutput.text = ""
                if(parseInt(iloscInput) <= 10000) {
                    errorOutput.text = ""
                    randomLetters(parseInt(iloscInput))

                    //START BRUTE FORCE
                    val bruteForceTime = measureTimeMillis {
                        bfOutputOccurrences = bruteForce(randomString.toCharArray(), wzorzecInput.toCharArray())
                    }
                    bruteForceOutput.text = bruteForceTime.toString()
                    bfOccurrencesOutput.text = bfOutputOccurrences.toString()
                    //END BRUTE FORCE

                    //START KMP
                    val kmpTime = measureTimeMillis {
                        kmpOutputOccurrences = kmp(randomString, wzorzecInput)
                    }
                    kmpOutput.text = kmpTime.toString()
                    kpmOccurrencesOutput.text = kmpOutputOccurrences.toString()

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
