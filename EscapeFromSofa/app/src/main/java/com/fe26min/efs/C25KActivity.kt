package com.fe26min.efs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.fe26min.efs.databinding.ActivityC25kBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.Timer
import kotlin.concurrent.timer

class C25KActivity : AppCompatActivity() {
    private lateinit var binding: ActivityC25kBinding
    private lateinit var thisDay: JSONArray

    private var entireTime = 0
    private val countdownSecond = 10
    private var currentCountDownDeciSecond = countdownSecond * 10
    private var currentDeciSecond = 0
    private var currentTimerDeciSecond = 0
    private var timer: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityC25kBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val week = intent.getIntExtra("week", 0)
        val day = intent.getIntExtra("day", 0)

        Log.e("week", week.toString())
        Log.e("day", day.toString())



        val jsonString = assets.open("c25k_info.json").reader().readText()

        val json = JSONObject(jsonString)

        val thisWeek = json.getJSONObject("${week}")

        Log.e("thisWeek", thisWeek.toString())

        thisDay = thisWeek.getJSONArray("${day}")
        Log.e("thisDay", thisDay.toString())

        for (i in 0..<thisDay.length()) {
            val timeObject = thisDay.getJSONObject(i)
            Log.e("json no.$i", timeObject.toString())
            entireTime += timeObject.get("time") as Int
        }
        Log.e("totalTime", entireTime.toString())

        initViews()

        binding.startButton.setOnClickListener {
            start()
        }
        binding.stopButton.setOnClickListener {
            showAlertDialog()
        }
        binding.pauseButton.setOnClickListener {
            pause()
        }

    }

    private fun initViews() {
        binding.stateTextView.text = thisDay.getJSONObject(0).get("state").toString()
        binding.entireTimeTextView.text = String.format("%02d:%02d", entireTime / 60, entireTime % 60)
        binding.sectionTimeText.text = String.format("%02d:%02d", (thisDay.getJSONObject(0).get("time") as Int / 60),(thisDay.getJSONObject(0).get("time") as Int % 60))

        binding.c25kProgressBar.progress = 0
    }

    private fun stop() {
        binding.stopButton.isVisible = false
        binding.pauseButton.isVisible = false
        binding.startButton.isVisible = true

        pause()
        currentTimerDeciSecond = 0
        currentDeciSecond = 0
        binding.totalTimeTextView.text = "00:00"
        binding.timerTextView.text = "00:00"
        binding.tickTextView.text = "0"
        initViews()
    }

    private fun pause() {
        timer?.cancel()
        timer = null

        Log.e("pause",timer.toString())

        binding.startButton.isVisible = true
        binding.pauseButton.isVisible = false
    }

    private fun start() {
        var idx = 0
        var time = thisDay.getJSONObject(idx).get("time")
        binding.stateTextView.text = thisDay.getJSONObject(idx).get("state").toString()
        binding.startButton.isVisible = false
        binding.stopButton.isVisible = true
        binding.pauseButton.isVisible = true

        timer = timer(initialDelay = 0, period = 100) {

            currentDeciSecond += 1
            currentTimerDeciSecond += 1

            val minutes = currentDeciSecond.div(10) / 60
            val second = currentDeciSecond.div(10) % 60
            val deciSeconds = currentDeciSecond % 10

            if(currentTimerDeciSecond.div(10) == time) {
                Log.e("start", timer.toString())
                currentTimerDeciSecond = 0
                idx++
                time = thisDay.getJSONObject(idx).get("time")
                runOnUiThread {
                    binding.stateTextView.text = thisDay.getJSONObject(idx).get("state").toString()
                    binding.sectionTimeText.text = String.format("%02d:%02d", (time as Int / 60),(time as Int % 60))
                }
            }

            val timerMinutes = currentTimerDeciSecond.div(10) / 60
            val timerSecond = currentTimerDeciSecond.div(10) % 60

            runOnUiThread {
                binding.totalTimeTextView.text =
                    String.format("%02d:%02d", minutes, second)
                binding.tickTextView.text = deciSeconds.toString()
                binding.timerTextView.text =
                    String.format("%02d:%02d", timerMinutes, timerSecond)
                Log.e("progress", "${currentDeciSecond}")
                Log.e("progress", "${currentDeciSecond.div(10)}")
                Log.e("progress", "${currentDeciSecond.div(10) / 100}")
                binding.c25kProgressBar.progress = ((currentDeciSecond / (entireTime * 10f)) * 100).toInt()
            }
        }

    }
    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") {_, _ ->
                stop()
            }
            setNegativeButton("아니오", null)
        }.show()
    }


}