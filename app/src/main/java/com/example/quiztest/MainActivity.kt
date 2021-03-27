package com.example.quiztest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startQuiz: Button = findViewById(R.id.btn_stat_quiz)

        startQuiz.setOnClickListener({
            startQuiz()
        })
    }

    fun startQuiz() {
        val intent = Intent(this@MainActivity, Quiz::class.java)
        startActivity(intent)
    }
}