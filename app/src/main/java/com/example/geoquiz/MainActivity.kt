package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.math.max
import kotlin.math.min

private const val TAG = "MainActivity"

class QuizViewModel : ViewModel(){
    init {
        Log.d(TAG, "ViewModel instance created")
    }

    var activeIndex : Int = 0
    var displayIndex : Int = 0

    val questionBank = listOf(
        Question(R.string.q_aus1, true),
        Question(R.string.q_vatican, true),
        Question(R.string.q_trickQuestion, false))
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val qvm : QuizViewModel by viewModels()

    private lateinit var sbCorrect : Snackbar
    private lateinit var sbIncorrect : Snackbar

    private var activeIndex : Int
        get(){return qvm.activeIndex}
        set(v){qvm.activeIndex=v}

    private var displayIndex : Int
        get(){return qvm.displayIndex}
        set(v){qvm.displayIndex=v}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $qvm")

        // Initialize the buttons and connections
        binding.ButtonTrue.setOnClickListener {onAnswerPressed(true)}
        binding.ButtonFalse.setOnClickListener {onAnswerPressed(false)}

        binding.ButtonNext.setOnClickListener { changeQuestion(1)}
        binding.ButtonPrev.setOnClickListener { changeQuestion(-1)}

        sbCorrect = Snackbar.make(
            this,
            binding.root,
            resources.getString(R.string.notif_correct),
            Snackbar.LENGTH_SHORT)
        sbIncorrect = Snackbar.make(
            this,
            binding.root,
            resources.getString(R.string.notif_incorrect),
            Snackbar.LENGTH_SHORT)

        changeQuestion(0, true)
    }

    private fun onAnswerPressed(answer:Boolean){
        if (qvm.questionBank[activeIndex].answer == answer){
            sbCorrect.show()
        }
        else{
            sbIncorrect.show()
        }
        // hide the buttons for now
        binding.ButtonFalse.visibility = View.GONE
        binding.ButtonTrue.visibility = View.GONE
        binding.ButtonNext.visibility = View.VISIBLE

        qvm.activeIndex+=1


    }

    private fun changeQuestion(offset:Int, forceUpdate:Boolean=false){
        val oldDI : Int = displayIndex
        displayIndex = max(0, min(displayIndex+offset, activeIndex))
        if ((oldDI==displayIndex && !forceUpdate)||
            (displayIndex >= qvm.questionBank.size)){
                return}// Display Index hasn't changed, no need to update

        binding.Question.text = resources.getString(qvm.questionBank[displayIndex].textResId)

        if (displayIndex < activeIndex){// The displayed question is not the active question
            binding.ButtonFalse.visibility = View.GONE // we use Gone instead of invisible for formatting
            binding.ButtonTrue.visibility = View.GONE
            binding.ButtonNext.visibility = View.VISIBLE
        }
        else{
            binding.ButtonFalse.visibility = View.VISIBLE
            binding.ButtonTrue.visibility = View.VISIBLE
            binding.ButtonNext.visibility = View.GONE
        }

        if (displayIndex == 0){
            binding.ButtonPrev.visibility = View.GONE
        }
        else if (activeIndex > 0){
            binding.ButtonPrev.visibility = View.VISIBLE
        }

    }

    override fun onStart() {super.onStart(); Log.d(TAG, "onStart called")}
    override fun onStop() {super.onStop(); Log.d(TAG, "onStop called")}
    override fun onResume() {super.onResume(); Log.d(TAG, "onResume called")}
    override fun onPause() {super.onPause(); Log.d(TAG, "onPause called")}
    override fun onDestroy() {super.onDestroy(); Log.d(TAG, "onDestroy called")}
}