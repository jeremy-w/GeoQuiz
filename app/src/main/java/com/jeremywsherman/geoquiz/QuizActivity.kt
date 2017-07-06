package com.jeremywsherman.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {
    val questions = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        true_button.setOnClickListener { didAnswer(true) }
        false_button.setOnClickListener { didAnswer(false) }

        questionIndexDidChange()
    }

    private fun questionIndexDidChange() {
        val question = questions[currentQuestionIndex]
        question_text_view.setText(question.textResId)
    }

    private fun didAnswer(isTrue: Boolean) {
        val question = questions[currentQuestionIndex]
        val isCorrect = question.isTrue == isTrue
        val toastTextId = if (isCorrect) R.string.toast_correct else R.string.toast_incorrect

        Toast
            .makeText(this@QuizActivity, toastTextId, Toast.LENGTH_SHORT)
            .apply { setGravity(Gravity.TOP, 0, 0) }
            .show()
    }
}
