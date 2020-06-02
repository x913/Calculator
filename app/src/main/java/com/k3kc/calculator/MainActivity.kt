package com.k3kc.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    enum class CalculatorAction {
        ADD, DIV, SUB, MUL
    }

    class CalculationInput(var value: Long) {}

    class ArithemticActions(var calculatorAction: CalculatorAction)

    var calculationInput: ArrayList<CalculationInput> = ArrayList()
    var arithemticActions: ArrayList<ArithemticActions> = ArrayList()

    lateinit var digits: StringBuilder
    lateinit var calculatorAction: CalculatorAction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        digits = java.lang.StringBuilder()

        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
        btnReset.setOnClickListener(this)
        btnPlus.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnMul.setOnClickListener(this)
        btnEquals.setOnClickListener(this)
    }

    private fun showDigitOnDisplay(digit: Int) {
        if(digits.length >= 9)
            return

        if(digit == 0) {
            if(!digits.isEmpty())
                digits.append(digit)
        } else
            digits.append(digit)

        if(digits.isEmpty())
            textDisplay.text = "0"
        else
            textDisplay.text = digits.toString()
    }

    private fun resetDigits() {
        digits.clear()
        calculationInput.clear()
        textDisplay.text = "0"
    }

    // show result on button equals
    private fun displayResult() {
        var result: Long = 0

        if(digits.isNotEmpty())
            calculationInput.add(CalculationInput(digits.toString().toLong(), CalculatorAction.ADD))

        for(calcInput in calculationInput) {
            when(calcInput.calculatorAction) {
                CalculatorAction.ADD -> result += calcInput.value
                CalculatorAction.SUB -> result -= calcInput.value
                CalculatorAction.MUL -> result *= calcInput.value
            }
        }

        textDisplay.text = result.toString()
    }

    private fun arithmeticAction(arithmeticAction: ArithemticActions) {
        if(digits.isEmpty())
            return
        arithemticActions.add(arithmeticAction)
        digits.clear()
    }

    override fun onClick(v: View?) {

        var userInputDigit:Int = -1

        when(v?.id) {
            R.id.btn0 -> userInputDigit = 0
            R.id.btn1 -> userInputDigit = 1
            R.id.btn2 -> userInputDigit = 2
            R.id.btn3 -> userInputDigit = 3
            R.id.btn4 -> userInputDigit = 4
            R.id.btn5 -> userInputDigit = 5
            R.id.btn6 -> userInputDigit = 6
            R.id.btn7 -> userInputDigit = 7
            R.id.btn8 -> userInputDigit = 8
            R.id.btn9 -> userInputDigit = 9
            R.id.btnReset -> resetDigits()
            R.id.btnPlus -> arithmeticAction(CalculatorAction.ADD)
            R.id.btnSub -> arithmeticAction(CalculatorAction.SUB)
            R.id.btnMul -> arithmeticAction(CalculatorAction.MUL)
            R.id.btnEquals -> displayResult()
        }

        if(userInputDigit != -1)
            showDigitOnDisplay(userInputDigit)

    }
}
