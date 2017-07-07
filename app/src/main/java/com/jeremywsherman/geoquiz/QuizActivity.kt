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

        /** Key for [correctlyAnsweredCount] int. */
        private const val KEY_CORRECTLY_ANSWERED_COUNT = "correct"
    }

    val questions = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        true_button.setOnClickListener { answerQuestionAction(true) }
        false_button.setOnClickListener { answerQuestionAction(false) }

        prev_button.setOnClickListener(this::prevQuestionAction)
        next_button.setOnClickListener(this::nextQuestionAction)
        question_text_view.setOnClickListener(this::nextQuestionAction)

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_INDEX)
            answeredQuestionIndexes.addAll(
                savedInstanceState.getIntArray(KEY_ANSWERED_INDEXES)?.toTypedArray() ?: arrayOf())
            restoreCorrectlyAnsweredCount(savedInstanceState)
        }
        questionIndexDidChange()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val bundle = outState ?: return

        bundle.putInt(KEY_INDEX, currentQuestionIndex)
        bundle.putIntArray(KEY_ANSWERED_INDEXES, answeredQuestionIndexes.toIntArray())
        saveCorrectlyAnsweredCount(outState)
    }


    /** Provide feedback when the true/false button is clicked. */
    private fun answerQuestionAction(isTrue: Boolean) {
        val isCorrect = currentQuestion.isTrue == isTrue
        recordAnsweredQuestionAtIndex(currentQuestionIndex, isCorrect)
        questionAnsweredDidChange()

        showAnswerFeedbackToast(isCorrect)
        showGradeIfQuizFinished()
    }

    private fun  showAnswerFeedbackToast(isCorrect: Boolean) {
        val toastTextId = if (isCorrect) R.string.toast_correct else R.string.toast_incorrect
        Toast
            .makeText(this@QuizActivity, toastTextId, Toast.LENGTH_SHORT)
//            .apply { setGravity(Gravity.TOP, 0, 0) }
            .show()
    }

    private fun showGradeIfQuizFinished() {
        if (answeredQuestionIndexes.size != questions.size) { return }

        val message = getString(R.string.toast_score, quizPercentGrade)
        Toast
            .makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }


    // region Tracking the Current Question
    private var currentQuestionIndex = 0
    val currentQuestion get() = questions[currentQuestionIndex]

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
    // endregion


    // region Tracking Answered Questions
    val answeredQuestionIndexes = mutableSetOf<Int>()

    private fun questionAnsweredDidChange() {
        val shouldEnableAnswerButtons = !alreadyAnsweredQuestionAtIndex(currentQuestionIndex)
        true_button.isEnabled = shouldEnableAnswerButtons
        false_button.isEnabled = shouldEnableAnswerButtons
    }

    private fun  alreadyAnsweredQuestionAtIndex(index: Int) = index in answeredQuestionIndexes

    private fun  recordAnsweredQuestionAtIndex(index: Int) = answeredQuestionIndexes.add(index)
    // endregion


    // region Grading the Quiz
    var correctlyAnsweredCount = 0

    private fun  recordAnsweredQuestionAtIndex(index: Int, correctly: Boolean) {
        if (correctly) { correctlyAnsweredCount += 1 }
        answeredQuestionIndexes.add(index)
    }

    private fun saveCorrectlyAnsweredCount(bundle: Bundle) {
        bundle.putInt(KEY_CORRECTLY_ANSWERED_COUNT, correctlyAnsweredCount)
    }

    private fun restoreCorrectlyAnsweredCount(bundle: Bundle) {
        correctlyAnsweredCount  = bundle.getInt(KEY_CORRECTLY_ANSWERED_COUNT)
    }

    private val quizPercentGrade get() = correctlyAnsweredCount.toDouble() / questions.size * 100.0
    // endregion
}
