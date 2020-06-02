package com.k3kc.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    enum class ArithmeticActions {
        ADD, DIV, SUB, MUL, EMPTY
    }

    data class CalculatorInput(var value: Long, var arithmeticAction: ArithmeticActions) {}

    var calculatorInput: ArrayList<CalculatorInput> = ArrayList()

    lateinit var digits: StringBuilder

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
        calculatorInput.clear()
        textDisplay.text = "0"
    }

    // show result on button equals
    private fun displayResult() {

        if(digits.isEmpty())
            return


        calculatorInput.add(CalculatorInput(digits.toString().toLong(), ArithmeticActions.EMPTY))
        Log.d("AAA", "current digits is " + digits.toString())

        var lastArithmeticAction: ArithmeticActions = ArithmeticActions.EMPTY
        var lastValue: Long = 0
        var result: Long = 0
        for(input in calculatorInput) {
            if(input.arithmeticAction != ArithmeticActions.EMPTY) {
                lastArithmeticAction = input.arithmeticAction
                lastValue = input.value
            } else {
                when(lastArithmeticAction) {
                    ArithmeticActions.ADD -> {
                        var tmpValue: Long = lastValue + input.value
                        result += tmpValue
                        calculatorInput.clear();
                        calculatorInput.add(CalculatorInput(result, ArithmeticActions.ADD))
                    }
                    ArithmeticActions.SUB -> {
                        var tmpValue: Long = lastValue - input.value
                        result += tmpValue
                        calculatorInput.clear();
                        calculatorInput.add(CalculatorInput(result, ArithmeticActions.SUB))
                    }
                    ArithmeticActions.MUL -> {
                        var tmpValue: Long = lastValue * input.value
                        result += tmpValue
                        calculatorInput.clear();
                        calculatorInput.add(CalculatorInput(result, ArithmeticActions.MUL))
                    }
                }
            }
        }

        textDisplay.text = result.toString()
    }

    private fun setArithmeticAction(arithmeticAction: ArithmeticActions) {
        if(digits.isEmpty())
            return

        var digitsValue: Long =  digits.toString().toLong()
        var displayValue: Long = textDisplay.text.toString().toLong()

        if(digitsValue != displayValue)
            digitsValue = displayValue;

        calculatorInput.add(CalculatorInput(digitsValue, arithmeticAction))
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
            R.id.btnPlus -> setArithmeticAction(ArithmeticActions.ADD)
            R.id.btnSub -> setArithmeticAction(ArithmeticActions.SUB)
            R.id.btnMul -> setArithmeticAction(ArithmeticActions.MUL)
            R.id.btnEquals -> displayResult()
        }

        if(userInputDigit != -1)
            showDigitOnDisplay(userInputDigit)

    }
}
