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
        val trueButton = findViewById<Button>(R.id.true_button)
        trueButton.setOnClickListener {
            Toast
                .makeText(this@QuizActivity, R.string.toast_correct, Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.TOP, 0, 0) }
                .show()
        }

        val falseButton: Button = findViewById(R.id.false_button)
        falseButton.setOnClickListener {
            Toast
                .makeText(this@QuizActivity, R.string.toast_incorrect, Toast.LENGTH_SHORT)
                .apply { setGravity(Gravity.TOP, 0, 0) }
                .show()
        }
    }
}
