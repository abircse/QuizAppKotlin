package com.coxtunes.quizappjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    lateinit var correctAns: TextView
    lateinit var wrongAns: TextView
    lateinit var totalQuestion: TextView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val correctResult = intent.getIntExtra("totalcorrectans", 0)
        val wrongResult = intent.getIntExtra("totalwrongans", 0)
        val totalquestion = intent.getIntExtra("totalquestion", 0)
        correctAns = findViewById(R.id.result_correct_ans)
        wrongAns = findViewById(R.id.result_wrong_ans)
        totalQuestion = findViewById(R.id.result_total_question)
        correctAns.setText("Total Correct Ans: $correctResult")
        wrongAns.setText("Total Wrong Ans: $wrongResult")
        totalQuestion.setText("Total Question Was $totalquestion")
    }
}