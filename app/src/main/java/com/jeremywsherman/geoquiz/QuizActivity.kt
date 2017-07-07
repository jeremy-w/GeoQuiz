package com.jeremywsherman.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {
    companion object {
        /** Used by [onSaveInstanceState] to save the [currentQuestionIndex]. */
        private const val KEY_INDEX = "index"

        /** Saves [answeredQuestionIndexes] as int array. */
        private const val KEY_ANSWERED_INDEXES = "answered"
    }

    val questions = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentQuestionIndex = 0
    val currentQuestion get() = questions[currentQuestionIndex]
    val answeredQuestionIndexes = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        true_button.setOnClickListener { didAnswer(true) }
        false_button.setOnClickListener { didAnswer(false) }
        prev_button.setOnClickListener(this::prevQuestionAction)
        next_button.setOnClickListener(this::nextQuestionAction)
        question_text_view.setOnClickListener(this::nextQuestionAction)

        answeredQuestionIndexes.addAll((savedInstanceState?.getIntArray(KEY_ANSWERED_INDEXES) ?: intArrayOf()).toTypedArray())
        currentQuestionIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        questionIndexDidChange()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val bundle = outState ?: return

        bundle.putInt(KEY_INDEX, currentQuestionIndex)
        bundle.putIntArray(KEY_ANSWERED_INDEXES, answeredQuestionIndexes.toIntArray())
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
        questionAnsweredDidChange()
    }

    private fun questionAnsweredDidChange() {
        val shouldEnableAnswerButtons = !alreadyAnsweredQuestionAtIndex(currentQuestionIndex)
        true_button.isEnabled = shouldEnableAnswerButtons
        false_button.isEnabled = shouldEnableAnswerButtons
    }

    private fun  alreadyAnsweredQuestionAtIndex(index: Int) = index in answeredQuestionIndexes

    private fun didAnswer(isTrue: Boolean) {
        recordAnsweredQuestionAtIndex(currentQuestionIndex)
        questionAnsweredDidChange()

        val isCorrect = currentQuestion.isTrue == isTrue
        val toastTextId = if (isCorrect) R.string.toast_correct else R.string.toast_incorrect

        Toast
            .makeText(this@QuizActivity, toastTextId, Toast.LENGTH_SHORT)
//            .apply { setGravity(Gravity.TOP, 0, 0) }
            .show()
    }

    private fun  recordAnsweredQuestionAtIndex(index: Int) = answeredQuestionIndexes.add(index)
}
