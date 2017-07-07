package com.jeremywsherman.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
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
    val currentQuestion get() = questions[currentQuestionIndex]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        true_button.setOnClickListener { didAnswer(true) }
        false_button.setOnClickListener { didAnswer(false) }
        prev_button.setOnClickListener(this::prevQuestionAction)
        next_button.setOnClickListener(this::nextQuestionAction)
        question_text_view.setOnClickListener(this::nextQuestionAction)

        questionIndexDidChange()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun nextQuestionAction(view: View) {
        currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
        questionIndexDidChange()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun prevQuestionAction(view: View) {
        currentQuestionIndex = (currentQuestionIndex  + questions.size - 1) % questions.size
        questionIndexDidChange()
    }

    private fun questionIndexDidChange() {
        question_text_view.setText(currentQuestion.textResId)
    }

    private fun didAnswer(isTrue: Boolean) {
        val isCorrect = currentQuestion.isTrue == isTrue
        val toastTextId = if (isCorrect) R.string.toast_correct else R.string.toast_incorrect

        Toast
            .makeText(this@QuizActivity, toastTextId, Toast.LENGTH_SHORT)
//            .apply { setGravity(Gravity.TOP, 0, 0) }
            .show()
    }
}
