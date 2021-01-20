package com.coxtunes.quizappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv_question, tv_correctans, tv_feedback, tv_status, tv_categoryname;
    Button b_answer1, b_answer2, b_answer3, b_answer4, b_next_question;
    List<QuestionItems> questionItems;
    int currentQuestion = 0;
    int correct = 0, wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_question = findViewById(R.id.text_quesion);
        tv_correctans = findViewById(R.id.text_correct_ans);
        tv_feedback = findViewById(R.id.text_feedback);
        b_answer1 = findViewById(R.id.button_ans1);
        b_answer2 = findViewById(R.id.button_ans2);
        b_answer3 = findViewById(R.id.button_ans3);
        b_answer4 = findViewById(R.id.button_ans4);
        tv_status = findViewById(R.id.text_status);
        b_next_question = findViewById(R.id.button_next);
        tv_categoryname = findViewById(R.id.textView_category_set);

        // get all question
        LoadAllQuestion();
        // Suffle the question if you want  (it meance Random Question)
        Collections.shuffle(questionItems);
        // Load First Question as position 0 meance array first object of data
        SetQuestionScreen(currentQuestion);

        b_answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Disable Ans Button as Ans already input
                b_answer1.setClickable(false);
                b_answer2.setClickable(false);
                b_answer3.setClickable(false);
                b_answer4.setClickable(false);

                if (questionItems.get(currentQuestion).getAnswer1().equals(questionItems.get(currentQuestion).getCorrect()))
                {
                    // increment for result if correct in final UI
                    correct++;
                    tv_status.setText("Great! Correct Answer");
                    tv_feedback.setText(questionItems.get(currentQuestion).getFeedback());
                }
                else
                {
                    // increment for result if wrong in final UI
                    wrong++;
                    tv_status.setText("Wrong Answer!");
                    tv_correctans.setText("Correct Ans is: "+questionItems.get(currentQuestion).getCorrect());
                }
            }
        });

        b_answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Disable Ans Button as Ans already input
                b_answer1.setClickable(false);
                b_answer2.setClickable(false);
                b_answer3.setClickable(false);
                b_answer4.setClickable(false);

                if (questionItems.get(currentQuestion).getAnswer2().equals(questionItems.get(currentQuestion).getCorrect()))
                {
                    // increment for result if correct in final UI
                    correct++;
                    tv_status.setText("Great! Correct Answer");
                    tv_feedback.setText(questionItems.get(currentQuestion).getFeedback());
                }
                else
                {
                    // increment for result if wrong in final UI
                    wrong++;
                    tv_status.setText("Wrong Answer!");
                    tv_correctans.setText("Correct Ans is: "+questionItems.get(currentQuestion).getCorrect());
                }

            }
        });

        b_answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Disable Ans Button as Ans already input
                b_answer1.setClickable(false);
                b_answer2.setClickable(false);
                b_answer3.setClickable(false);
                b_answer4.setClickable(false);

                if (questionItems.get(currentQuestion).getAnswer3().equals(questionItems.get(currentQuestion).getCorrect()))
                {
                    // increment for result if correct in final UI
                    correct++;
                    tv_status.setText("Great! Correct Answer");
                    tv_feedback.setText(questionItems.get(currentQuestion).getFeedback());
                }
                else
                {
                    // increment for result if wrong in final UI
                    wrong++;
                    tv_status.setText("Wrong Answer!");
                    tv_correctans.setText("Correct Ans is: "+questionItems.get(currentQuestion).getCorrect());
                }

            }
        });

        b_answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Disable Ans Button as Ans already input
                b_answer1.setClickable(false);
                b_answer2.setClickable(false);
                b_answer3.setClickable(false);
                b_answer4.setClickable(false);


                if (questionItems.get(currentQuestion).getAnswer4().equals(questionItems.get(currentQuestion).getCorrect()))
                {
                    // increment for result if correct in final UI
                    correct++;
                    tv_status.setText("Great! Correct Answer");
                    tv_feedback.setText(questionItems.get(currentQuestion).getFeedback());
                }
                else
                {
                    // increment for result if wrong in final UI
                    wrong++;
                    tv_status.setText("Wrong Answer!");
                    tv_correctans.setText("Correct Ans is: "+questionItems.get(currentQuestion).getCorrect());
                }
            }
        });

        // Next Button for New Question, if no question then go to result of quiz page
        b_next_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Load Next Question if available
                if (currentQuestion < questionItems.size()-1)
                {
                    currentQuestion++;
                    SetQuestionScreen(currentQuestion);
                    tv_correctans.setText("");
                    tv_feedback.setText("");
                    tv_status.setText("");

                    // Enable Ans Button as new question on display
                    b_answer1.setClickable(true);
                    b_answer2.setClickable(true);
                    b_answer3.setClickable(true);
                    b_answer4.setClickable(true);

                }
                else
                {
                    // No more question available so go to result UI
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("totalcorrectans", correct);
                    intent.putExtra("totalwrongans", wrong);
                    intent.putExtra("totalquestion",questionItems.size());
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    // Set Question to UI/Screen
    private void SetQuestionScreen(int number)
    {
        tv_question.setText(questionItems.get(number).getQuestion());
        b_answer1.setText(questionItems.get(number).getAnswer1());
        b_answer2.setText(questionItems.get(number).getAnswer2());
        b_answer3.setText(questionItems.get(number).getAnswer3());
        b_answer4.setText(questionItems.get(number).getAnswer4());
    }

    // load QUESTION FROM JSON FILE
    private void LoadAllQuestion() {
        questionItems = new ArrayList<>();
        // Load All Question from json
        String jsonStr = loadJSONFromAssets("quiz.json");
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            // Set category Name
            String questionCat = jsonObj.getString("category");
            tv_categoryname.setText(questionCat);
            // Get Question Array Data
            JSONArray questionsdata = jsonObj.getJSONArray("questions");
            for (int i = 0; i < questionsdata.length(); i++) {
                JSONObject question = questionsdata.getJSONObject(i);
                String questionString = question.getString("question");
                String answer1String = question.getString("answer1");
                String answer2String = question.getString("answer2");
                String answer3String = question.getString("answer3");
                String answer4String = question.getString("answer4");
                String correctString = question.getString("correct");
                String feedbackString = question.getString("feedback");

                questionItems.add(new QuestionItems(
                        questionString,
                        answer1String,
                        answer2String,
                        answer3String,
                        answer4String,
                        correctString,
                        feedbackString
                ));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    // Load JSON FILE FROM ASSETS FOLDER
    private String loadJSONFromAssets(String file)
    {
        String json = "";
        try {
            InputStream is = getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}