package com.coxtunes.quizappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {


    TextView correctAns, wrongAns, totalQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int correctResult = getIntent().getIntExtra("totalcorrectans",0);
        int wrongResult = getIntent().getIntExtra("totalwrongans", 0);
        int totalquestion = getIntent().getIntExtra("totalquestion", 0);

        correctAns = findViewById(R.id.result_correct_ans);
        wrongAns = findViewById(R.id.result_wrong_ans);
        totalQuestion = findViewById(R.id.result_total_question);

        correctAns.setText("Total Correct Ans: "+correctResult);
        wrongAns.setText("Total Wrong Ans: "+wrongResult);
        totalQuestion.setText("Total Question Was "+totalquestion);
    }
}