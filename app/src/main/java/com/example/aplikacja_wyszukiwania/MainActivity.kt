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
        val bfOutputTime = findViewById<TextView>(R.id.bruteForceOutput)
        val kmpOutputTime = findViewById<TextView>(R.id.kmpOutput)
        val bmOutputTime = findViewById<TextView>(R.id.bmOutput)
        val rkOutputTime = findViewById<TextView>(R.id.rkOutput)
        val occurrencesOutput = findViewById<TextView>(R.id.occurrencesText)
        val firstPositionOutput = findViewById<TextView>(R.id.firstPositionText)
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

        //BM ALGORITHM
        fun bm(source: String, pattern: String): Int {

            val pattChar = pattern.toCharArray()
            val patternLength: Int = pattChar.size
            if (patternLength == 0) {
                return 0
            }
            val src = source.toCharArray()
            val srcLength: Int = src.size

            val shift = IntArray(200) { patternLength }
            for (k in 0..(patternLength - 2)) {
                shift[pattChar[k].toInt()] = patternLength - 1 - k
            }

            var i = 0
            var j: Int
            while ((i + patternLength) <= srcLength) {
                j = patternLength - 1
                while (source[i + j] == pattChar[j]) {
                    j -= 1
                    if (j < 0) {
                        return i
                    }
                }
                i += shift[src[i + patternLength - 1].toInt()]
            }
            return -1
        }

        //RK ALGORITHM

            // d is the number of characters in the input alphabet
            val d = 256
            fun search(txt: String, pat: String, q: Int): Int {
                val M = pat.length
                val N = txt.length
                var i: Int
                var j: Int
                var p = 0 // hash value for pattern
                var t = 0 // hash value for txt
                var h = 1

                // The value of h would be "pow(d, M-1)%q"
                i = 0
                while (i < M - 1) {
                    h = h * d % q
                    i++
                }

                // Calculate the hash value of pattern and first
                // window of text
                i = 0
                while (i < M) {
                    p = (d * p + pat[i].code) % q
                    t = (d * t + txt[i].code) % q
                    i++
                }

                // Slide the pattern over text one by one
                i = 0
                while (i <= N - M) {


                    // Check the hash values of current window of
                    // text and pattern. If the hash values match
                    // then only check for characters one by one
                    if (p == t) {
                        /* Check for characters one by one */
                        j = 0
                        while (j < M) {
                            if (txt[i + j] != pat[j]) break
                            j++
                        }

                        // if p == t and pat[0...M-1] = txt[i, i+1,
                        // ...i+M-1]
                        if (j == M)
                            return i

                    }

                    // Calculate hash value for next window of text:
                    // Remove leading digit, add trailing digit
                    if (i < N - M) {
                        t = ((d * (t - txt[i].code * h)
                                + txt[i + M].code)
                                % q)

                        // We might get negative value of t,
                        // converting it to positive
                        if (t < 0) t = t + q
                    }
                    i++
                }
                return -1
            }


        //URUCHOM BUTTON ACTION
        findViewById<Button>(R.id.uruchomButton).setOnClickListener {

            val iloscInput = findViewById<TextInputEditText>(R.id.iloscZnakowInput).text.toString()
            val wzorzecInput = findViewById<TextInputEditText>(R.id.wzorzecInput).text.toString()
            val bfOutput: Int
            val kmpOutput: Int
            val bmOutput: Int
            val rkOutput: Int

            if(iloscInput.isNotEmpty() && wzorzecInput!="")
            {
                errorOutput.text = ""
                if(parseInt(iloscInput) <= 10000) {
                    errorOutput.text = ""
                    randomLetters(parseInt(iloscInput))

                    //START BRUTE FORCE
                    val bruteForceTime = measureTimeMillis {
                        bfOutput = bruteForce(randomString.toCharArray(), wzorzecInput.toCharArray())
                    }
                    bfOutputTime.text = bruteForceTime.toString()
                    //END BRUTE FORCE

                    //START KMP
                    val kmpTime = measureTimeMillis {
                        kmpOutput = kmp(randomString, wzorzecInput)
                    }
                    kmpOutputTime.text = kmpTime.toString()
                    //END KMP

                    //START KMP
                    val bmTime = measureTimeMillis {
                        bmOutput = bm(randomString, wzorzecInput)
                    }
                    bmOutputTime.text = bmTime.toString()
                    //END KMP

                    //START RK
                    val rkTime = measureTimeMillis {
                        rkOutput = search(randomString, wzorzecInput, 101)
                    }
                    rkOutputTime.text = rkTime.toString()
                    //END RK

                    occurrencesOutput.text = "Occurrences: $bfOutput"
                    firstPositionOutput.text = "First position: $kmpOutput"
                    firstPositionOutput.text = "First position: $bmOutput"
                    firstPositionOutput.text = "First position: $rkOutput"

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
