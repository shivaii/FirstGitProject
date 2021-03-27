package com.example.quiztest

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Quiz : AppCompatActivity() {
    private lateinit var textViewQuetion: TextView
    private lateinit var textViewScore: TextView
    private lateinit var textViewQuetionCount: TextView
    private lateinit var textViewQuetionCountDown: TextView
    private lateinit var rbGroup: RadioGroup
    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var buttonConformNext: Button
    private lateinit var textColorDefaulRb: ColorStateList
    private lateinit var quetionsList: List<Quetion>
    private lateinit var currentQuetion: Quetion
    private var quetionCounter: Int = 0
    private var quetionCountTotal: Int = 0
    private var score: Int = 0
    private var answered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        textViewQuetion = findViewById(R.id.text_view_quetion)
        textViewScore = findViewById(R.id.text_view_score)
        textViewQuetionCount = findViewById(R.id.text_view_quetion_cout)
//        textViewQuetionCountDown = findViewById(R.id.text_view_countdown)
        rbGroup = findViewById(R.id.radio_group)
        rb1 = findViewById(R.id.radio_button_1)
        rb2 = findViewById(R.id.radio_button_2)
        rb3 = findViewById(R.id.radio_button_3)
        buttonConformNext = findViewById(R.id.button_confirm_next)
        textColorDefaulRb = rb1.textColors;
        var dbHelper: QuizDBHelper = QuizDBHelper(this);
        quetionsList =  dbHelper.getAllQuetions()
        quetionCountTotal = quetionsList.size
        Collections.shuffle(quetionsList)
        showNextQuetion()
        buttonConformNext.setOnClickListener({

            if (!answered) {
                if (rb1.isChecked || rb2.isChecked || rb3.isChecked) {
                    checkAnswer()
                } else {
                    Toast.makeText(this, "Please select any answer", Toast.LENGTH_SHORT).show()
                }
            } else {
                showNextQuetion()
            }
        })
    }

    private fun checkAnswer() {
        answered = true
        var rbSelected: Button = findViewById(rbGroup.checkedRadioButtonId)
        var answerNr: Int = rbGroup.indexOfChild(rbSelected) + 1

        Log.d("answerNr", answerNr.toString());
        Log.d("currentQuetion.answerNr", currentQuetion.answerNr.toString());
        if (answerNr == currentQuetion.answerNr) {

            score ++
            textViewScore.text = "Score: " + score
        }

        showSolution()
    }

    private fun showSolution() {
        rb1.setTextColor(Color.RED)
        rb2.setTextColor(Color.RED)
        rb3.setTextColor(Color.RED)

        when (currentQuetion.answerNr) {
            1 -> {
                rb1.setTextColor(Color.GREEN)
                textViewQuetion.text = "Answer 1 is correct"
            }
            2 -> {
                rb2.setTextColor(Color.GREEN)
                textViewQuetion.text = "Answer 2 is correct"
            }
            3 -> {
                rb3.setTextColor(Color.GREEN)
                textViewQuetion.text = "Answer 3 is correct"
            }
        }

        if (quetionCounter < quetionCountTotal) {
            buttonConformNext.text = "Next"
        } else {
            buttonConformNext.text = "Finish"
        }
    }

    private fun showNextQuetion() {
        rb1.setTextColor(textColorDefaulRb)
        rb2.setTextColor(textColorDefaulRb)
        rb3.setTextColor(textColorDefaulRb)
        rbGroup.clearCheck()

        if (quetionCounter < quetionCountTotal) {
            currentQuetion = quetionsList.get(quetionCounter)
            textViewQuetion.text = currentQuetion.quetion
            rb1.text = currentQuetion.option1
            rb2.text = currentQuetion.option2
            rb3.text = currentQuetion.option3

            quetionCounter++

            textViewQuetionCount.text = "Quetion: " + quetionCounter + "/" + quetionCountTotal
            answered = false
            buttonConformNext.text = "Confirm"

        } else {
            finishQuiz()
        }

    }

    private fun finishQuiz() {
        finish()
    }
}