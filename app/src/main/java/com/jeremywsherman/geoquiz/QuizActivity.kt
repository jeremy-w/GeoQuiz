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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        listOf(
            true_button to R.string.toast_correct,
            false_button to R.string.toast_incorrect
        )
            .forEach { (button, textID) ->
                button.setOnClickListener {
                    Toast
                        .makeText(this@QuizActivity, textID, Toast.LENGTH_SHORT)
                        .apply { setGravity(Gravity.TOP, 0, 0) }
                        .show()
                }
            }
    }
}
