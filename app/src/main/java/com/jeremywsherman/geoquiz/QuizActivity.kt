package com.jeremywsherman.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        listOf(
            R.id.true_button to R.string.toast_correct,
            R.id.false_button to R.string.toast_incorrect
            )
            .map { findViewById<Button>(it.first) to it.second}
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
