package com.coxtunes.quizappjava

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var tv_question: TextView
    lateinit var tv_correctans: TextView
    lateinit var tv_feedback: TextView
    lateinit var tv_status: TextView
    lateinit var tv_categoryname: TextView
    lateinit var b_answer1: Button
    lateinit var b_answer2: Button
    lateinit var b_answer3: Button
    lateinit var b_answer4: Button
    lateinit var b_next_question: Button
    lateinit var questionItems: MutableList<QuestionItems?>
    var currentQuestion = 0
    var correct = 0
    var wrong = 0
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_question = findViewById(R.id.text_quesion) as TextView
        tv_correctans = findViewById(R.id.text_correct_ans) as TextView
        tv_feedback = findViewById(R.id.text_feedback) as TextView
        b_answer1 = findViewById(R.id.button_ans1) as Button
        b_answer2 = findViewById(R.id.button_ans2) as Button
        b_answer3 = findViewById(R.id.button_ans3) as Button
        b_answer4 = findViewById(R.id.button_ans4) as Button
        tv_status = findViewById(R.id.text_status) as TextView
        b_next_question = findViewById(R.id.button_next)  as Button
        tv_categoryname = findViewById(R.id.textView_category_set) as TextView

        // get all question
        LoadAllQuestion()
        // Suffle the question if you want  (it meance Random Question)
        Collections.shuffle(questionItems)
        // Load First Question as position 0 meance array first object of data
        SetQuestionScreen(currentQuestion)
        b_answer1.setOnClickListener(View.OnClickListener {
            // Disable Ans Button as Ans already input
            b_answer1.setClickable(false)
            b_answer2.setClickable(false)
            b_answer3.setClickable(false)
            b_answer4.setClickable(false)
            if (questionItems[currentQuestion]!!.answer1 == questionItems[currentQuestion]!!.correct) {
                // increment for result if correct in final UI
                correct++
                tv_status.setText("Great! Correct Answer")
                tv_feedback.setText(questionItems[currentQuestion]!!.feedback)
            } else {
                // increment for result if wrong in final UI
                wrong++
                tv_status.setText("Wrong Answer!")
                tv_correctans.setText("Correct Ans is: " + questionItems[currentQuestion]!!.correct)
            }
        })
        b_answer2.setOnClickListener(View.OnClickListener {
            // Disable Ans Button as Ans already input
            b_answer1.setClickable(false)
            b_answer2.setClickable(false)
            b_answer3.setClickable(false)
            b_answer4.setClickable(false)
            if (questionItems[currentQuestion]!!.answer2 == questionItems[currentQuestion]!!.correct) {
                // increment for result if correct in final UI
                correct++
                tv_status.setText("Great! Correct Answer")
                tv_feedback.setText(questionItems[currentQuestion]!!.feedback)
            } else {
                // increment for result if wrong in final UI
                wrong++
                tv_status.setText("Wrong Answer!")
                tv_correctans.setText("Correct Ans is: " + questionItems[currentQuestion]!!.correct)
            }
        })
        b_answer3.setOnClickListener(View.OnClickListener {
            // Disable Ans Button as Ans already input
            b_answer1.setClickable(false)
            b_answer2.setClickable(false)
            b_answer3.setClickable(false)
            b_answer4.setClickable(false)
            if (questionItems[currentQuestion]!!.answer3 == questionItems[currentQuestion]!!.correct) {
                // increment for result if correct in final UI
                correct++
                tv_status.setText("Great! Correct Answer")
                tv_feedback.setText(questionItems[currentQuestion]!!.feedback)
            } else {
                // increment for result if wrong in final UI
                wrong++
                tv_status.setText("Wrong Answer!")
                tv_correctans.setText("Correct Ans is: " + questionItems[currentQuestion]!!.correct)
            }
        })
        b_answer4.setOnClickListener(View.OnClickListener {
            // Disable Ans Button as Ans already input
            b_answer1.setClickable(false)
            b_answer2.setClickable(false)
            b_answer3.setClickable(false)
            b_answer4.setClickable(false)
            if (questionItems[currentQuestion]!!.answer4 == questionItems[currentQuestion]!!.correct) {
                // increment for result if correct in final UI
                correct++
                tv_status.setText("Great! Correct Answer")
                tv_feedback.setText(questionItems[currentQuestion]!!.feedback)
            } else {
                // increment for result if wrong in final UI
                wrong++
                tv_status.setText("Wrong Answer!")
                tv_correctans.setText("Correct Ans is: " + questionItems[currentQuestion]!!.correct)
            }
        })

        // Next Button for New Question, if no question then go to result of quiz page
        b_next_question.setOnClickListener(View.OnClickListener {
            // Load Next Question if available
            if (currentQuestion < questionItems.size - 1) {
                currentQuestion++
                SetQuestionScreen(currentQuestion)
                tv_correctans.setText("")
                tv_feedback.setText("")
                tv_status.setText("")

                // Enable Ans Button as new question on display
                b_answer1.setClickable(true)
                b_answer2.setClickable(true)
                b_answer3.setClickable(true)
                b_answer4.setClickable(true)
            } else {
                // No more question available so go to result UI
                val intent = Intent(applicationContext, ResultActivity::class.java)
                intent.putExtra("totalcorrectans", correct)
                intent.putExtra("totalwrongans", wrong)
                intent.putExtra("totalquestion", questionItems.size)
                startActivity(intent)
                finish()
            }
        })
    }

    // Set Question to UI/Screen
    private fun SetQuestionScreen(number: Int) {
        tv_question.text = questionItems[number]!!.question
        b_answer1.text = questionItems[number]!!.answer1
        b_answer2.text = questionItems[number]!!.answer2
        b_answer3.text = questionItems[number]!!.answer3
        b_answer4.text = questionItems[number]!!.answer4
    }

    // load QUESTION FROM JSON FILE
    private fun LoadAllQuestion() {
        questionItems = ArrayList()
        // Load All Question from json
        val jsonStr = loadJSONFromAssets("quiz.json")
        try {
            val jsonObj = JSONObject(jsonStr)
            // Set category Name
            val questionCat = jsonObj.getString("category")
            tv_categoryname.text = questionCat
            // Get Question Array Data
            val questionsdata = jsonObj.getJSONArray("questions")
            for (i in 0 until questionsdata.length()) {
                val question = questionsdata.getJSONObject(i)
                val questionString = question.getString("question")
                val answer1String = question.getString("answer1")
                val answer2String = question.getString("answer2")
                val answer3String = question.getString("answer3")
                val answer4String = question.getString("answer4")
                val correctString = question.getString("correct")
                val feedbackString = question.getString("feedback")

                // Add data to model
                questionItems.add(QuestionItems(
                        questionString,
                        answer1String,
                        answer2String,
                        answer3String,
                        answer4String,
                        correctString,
                        feedbackString
                ))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    // Load JSON FILE FROM ASSETS FOLDER
    private fun loadJSONFromAssets(file: String): String {
        var json = ""
        try {
            val `is` = assets.open(file)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }
}