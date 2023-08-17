package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse : Button

    private lateinit var sbCorrect : Snackbar
    private lateinit var sbIncorrect : Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sbCorrect = Snackbar.make(
            this,
            findViewById(R.id.root),
            resources.getString(R.string.notif_correct),
            Snackbar.LENGTH_SHORT
            )

        sbIncorrect = Snackbar.make(
            this,
            findViewById(R.id.root),
            resources.getString(R.string.notif_incorrect),
            Snackbar.LENGTH_SHORT
        )

        // Initialize the buttons
        buttonTrue = findViewById(R.id.ButtonTrue)
        buttonFalse = findViewById(R.id.ButtonFalse)

        buttonTrue.setOnClickListener {
            sbCorrect.show()
        }

        buttonFalse.setOnClickListener {
            sbIncorrect.show()
        }

    }
}