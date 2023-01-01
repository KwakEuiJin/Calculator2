package com.kej.calculator2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kej.calculator2.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButton()
    }

    override fun onClick(v: View?) {
        val currentText = (v as? Button)?.text?.toString()
        currentText?.let { inCurrentText ->
            when {
                isNumber(inCurrentText) -> {
                    numberClicked(inCurrentText)
                }
                inCurrentText == "C" -> {
                    clearClicked()
                }
                inCurrentText == "=" -> {
                    equalClicked()
                }
                else -> {
                    operatorClicked(inCurrentText)
                }
            }
        }
    }

    private fun initButton() {
        with(binding) {
            button0.setOnClickListener(this@MainActivity)
            button1.setOnClickListener(this@MainActivity)
            button2.setOnClickListener(this@MainActivity)
            button3.setOnClickListener(this@MainActivity)
            button4.setOnClickListener(this@MainActivity)
            button5.setOnClickListener(this@MainActivity)
            button6.setOnClickListener(this@MainActivity)
            button7.setOnClickListener(this@MainActivity)
            button8.setOnClickListener(this@MainActivity)
            button9.setOnClickListener(this@MainActivity)
            buttonC.setOnClickListener(this@MainActivity)
            buttonEqual.setOnClickListener(this@MainActivity)
            buttonPlus.setOnClickListener(this@MainActivity)
            buttonMinus.setOnClickListener(this@MainActivity)
        }
    }

    private fun numberClicked(number: String) {
        if (operatorText.isEmpty()) {
            firstNumberText.append(number)
        } else {
            secondNumberText.append(number)
        }
        updateEquationTextView()
    }

    private fun operatorClicked(currentText: String) {
        if (firstNumberText.isEmpty()) {
            Toast.makeText(this, "숫자를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        } else if (secondNumberText.isNotEmpty() || operatorText.isNotEmpty()) {
            Toast.makeText(this, "1개의 연산자에 대해서만 연산이 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        operatorText.append(currentText)
        updateEquationTextView()
    }

    private fun clearClicked() {
        firstNumberText.clear()
        operatorText.clear()
        secondNumberText.clear()
        binding.resultTextView.text = ""
        updateEquationTextView()
    }


    private fun equalClicked() {
        if (firstNumberText.isEmpty() || secondNumberText.isEmpty() || operatorText.isEmpty()) {
            Toast.makeText(this, "정확하게 수식을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val first = firstNumberText.toString().toBigDecimal()
        val second = secondNumberText.toString().toBigDecimal()

        val result = when (operatorText.toString()) {
            "+" -> {
                first + second
            }
            "-" -> {
                first - second
            }
            else -> {
                "잘못된 수식입니다."
            }
        }.toString()
        binding.resultTextView.text = result
    }


    @SuppressLint("SetTextI18n")
    private fun updateEquationTextView() {
        val firstFormattedNumber = if (firstNumberText.isNotEmpty()) decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if (secondNumberText.isNotEmpty()) decimalFormat.format(secondNumberText.toString().toBigDecimal()) else ""
        binding.equationTextView.text = "$firstFormattedNumber$operatorText$secondFormattedNumber"
    }

    private fun isNumber(text: String): Boolean {
        return try {
            text.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}