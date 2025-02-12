package com.example.tentangsaya

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalculatorActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private lateinit var tvOperator: TextView
    private var isinum: Double = 0.0
    private var currentNumber: String = "0"
    private var operation: String = ""
    private var isNewNumber: Boolean = true
    private var hasDecimal: Boolean = false
    private lateinit var mediaPlayer1: MediaPlayer
    private lateinit var mediaPlayer2: MediaPlayer
    private lateinit var mediaPlayer3: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mediaPlayer1 = MediaPlayer.create(this, R.raw.audio1)
        mediaPlayer2 = MediaPlayer.create(this, R.raw.audio2)
        mediaPlayer3 = MediaPlayer.create(this, R.raw.audio4)

        // Initialize Views
        tvResult = findViewById(R.id.tvResult)
        tvOperator = TextView(this).apply {
            visibility = View.GONE
            text = ""
        }

        // Number buttons
        val numberButtons = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        // Set click listeners for number buttons
        numberButtons.forEachIndexed { index, buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                onNumberClick(index.toString())
                mediaPlayer1.start()
            }
        }

        // Operation buttons
        findViewById<Button>(R.id.btnPlus).setOnClickListener {
            onOperationClick("+")
            mediaPlayer2.start()
        }
        findViewById<Button>(R.id.btnMinus).setOnClickListener {
            onOperationClick("-")
            mediaPlayer2.start()
        }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            onOperationClick("×")
            mediaPlayer2.start()
        }
        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            onOperationClick("÷")
            mediaPlayer2.start()
        }
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            onEqualsClick()
            mediaPlayer3.start()
        }
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            onClearClick()
            mediaPlayer3.start()
        }

        findViewById<Button>(R.id.buttonKembali).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer1.release()
        mediaPlayer2.release()
        mediaPlayer3.release()
    }

    private fun onNumberClick(number: String) {
        if (isNewNumber) {
            currentNumber = number
            isNewNumber = false
        } else {
            if (currentNumber == "0") {
                currentNumber = number
            } else {
                currentNumber += number
            }
        }
        tvResult.text = currentNumber
    }

    private fun onOperationClick(op: String) {
        try {
            if (operation.isNotEmpty() && !isNewNumber) {
                // Jika sudah ada operasi sebelumnya, hitung dulu
                calculateResult()
            }
            isinum = currentNumber.toDouble()
            operation = op
            tvOperator.text = op
            tvOperator.visibility = View.VISIBLE
            isNewNumber = true
            hasDecimal = false
        } catch (e: Exception) {
            tvResult.text = "Error"
            resetCalculator()
        }
    }

    private fun onEqualsClick() {
        try {
            if (operation.isNotEmpty()) {
                calculateResult()
                operation = ""
                tvOperator.visibility = View.GONE
                isNewNumber = true
                hasDecimal = currentNumber.contains(".")
            }
        } catch (e: Exception) {
            tvResult.text = "Error"
            resetCalculator()
        }
    }

    private fun calculateResult() {
        val secondNumber = currentNumber.toDouble()
        val result = when (operation) {
            "+" -> isinum + secondNumber
            "-" -> isinum - secondNumber
            "×" -> isinum * secondNumber
            "÷" -> if (secondNumber != 0.0) isinum / secondNumber else throw ArithmeticException("Division by zero")
            else -> secondNumber
        }

        currentNumber = if (result.toLong().toDouble() == result) {
            result.toLong().toString()
        } else {
            result.toString()
        }
        tvResult.text = currentNumber
        isinum = result
    }

    private fun onClearClick() {
        resetCalculator()
    }

    private fun resetCalculator() {
        currentNumber = "0"
        isinum = 0.0
        operation = ""
        isNewNumber = true
        hasDecimal = false
        tvResult.text = "0"
        tvOperator.visibility = View.GONE
    }
}