package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.geoquiz.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER : String = ""
const val EXTRA_SHOWN : String = ""

private const val TAG = "CheatActivity"
class CheatViewModel : ViewModel() {
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    var hasViewed: Boolean = false
}
class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private var answer: Boolean = false

    private val cvm : CheatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        Log.d(TAG, "Cheat Activity Created")

        binding.ButtonYesCheat.setOnClickListener {
            Log.d(TAG, "Button Yes pressed")
            binding.ButtonYesCheat.visibility = View.INVISIBLE
            cvm.hasViewed = true

            setAnswerShownResult()
            if (answer){binding.CheatAnswer.text = resources.getString(R.string.cheat_true)}
            else{binding.CheatAnswer.text = resources.getString(R.string.cheat_false)}

        }
        binding.ButtonNoCheat.setOnClickListener {
            Log.d(TAG, "Button No pressed")
            setAnswerShownResult()
            super.finish()
        }

    }

    private fun setAnswerShownResult(){
        val data = Intent().apply{
            putExtra(EXTRA_SHOWN, cvm.hasViewed)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext : Context, answer:Boolean) : Intent {
            return Intent(packageContext, CheatActivity::class.java).apply{
                putExtra(EXTRA_ANSWER, answer)
            }
        }
    }
}