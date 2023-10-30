package com.fe26min.efs

import android.content.Context
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.fe26min.efs.databinding.ActivityC25kBinding
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Timer
import kotlin.concurrent.timer

class C25KActivity : AppCompatActivity() {
    private lateinit var binding: ActivityC25kBinding
    private lateinit var thisWeek: JSONObject
    private lateinit var thisDay: JSONArray
    private var week = 0
    private var day = 0
    private var idx = 0


    private var entireTime = 0
    private val countdownSecond = 10
    private var currentCountDownDeciSecond = countdownSecond * 10
    private var currentDeciSecond = 0
    private var currentTimerDeciSecond = 0
    private var timer: Timer? = null
    private var time: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityC25kBinding.inflate(layoutInflater)
        setContentView(binding.root)

        week = intent.getIntExtra("week", 0)
        day = intent.getIntExtra("day", 0)

        Log.e("week", week.toString())
        Log.e("day", day.toString())

        val jsonString = assets.open(getString(R.string.c25k_json)).reader().readText()

        val json = JSONObject(jsonString)

        try {
            thisWeek = json.getJSONObject("${week}")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "잘못된 Week 정보입니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        Log.e(
            "first check data",
            getSharedPreferences(C25K_HISTORY, Context.MODE_PRIVATE).getString(
                "$week$day",
                "NOT FOUND"
            ).toString()
        )
        Log.e(
            "first check num data",
            getSharedPreferences(C25K_HISTORY, Context.MODE_PRIVATE).getString(
                "11",
                "NOT FOUND"
            ).toString()
        )


        Log.e("thisWeek", thisWeek.toString())

        try {
            thisDay = thisWeek.getJSONArray("${day}")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "잘못된 day 정보입니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
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
            showStopDialog()
        }
        binding.pauseButton.setOnClickListener {
            pause()
        }

        binding.leftArrow.setOnClickListener {
            if(timer != null && idx != 0) {
                idx--;
                updateState(timer)
            }
        }

        binding.rightArrow.setOnClickListener {
            if(timer != null && idx < thisDay.length()) {
                Log.e("timer", "${timer}")
                idx++;
                updateState(timer)
            }
        }

    }

    override fun onBackPressed() {
        if(timer == null)
            super.onBackPressed()
        showFinishDialog("온동 기록이 종료가 됩니다")
    }
    private fun initViews() {
        binding.stateTextView.text = thisDay.getJSONObject(0).get("state").toString()
        binding.entireTimeTextView.text =
            String.format("%02d:%02d", entireTime / 60, entireTime % 60)
        binding.sectionTimeText.text = String.format(
            "%02d:%02d",
            (thisDay.getJSONObject(0).get("time") as Int / 60),
            (thisDay.getJSONObject(0).get("time") as Int % 60)
        )

        binding.c25kProgressBar.progress = 0
    }

    private fun stop() {
        binding.stopButton.isVisible = false
        binding.pauseButton.isVisible = false
        binding.startButton.isVisible = true

        pause()
        currentTimerDeciSecond = 0
        currentDeciSecond = 0
        binding.totalTimeTextView.text = getString(R.string.zero_time)
        binding.timerTextView.text = getString(R.string.zero_time)
        binding.tickTextView.text = getString(R.string.zero_tick)
        initViews()
    }

    private fun pause() {
        timer?.cancel()
        timer = null

        Log.e("pause", timer.toString())

        binding.startButton.isVisible = true
        binding.pauseButton.isVisible = false
    }

    private fun start() {
        idx = 0
        time = thisDay.getJSONObject(idx).get("time") as Int
        binding.leftArrow.setColorFilter(Color.GRAY);
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

            if (currentTimerDeciSecond.div(10) >= time as Int) {
//                Log.e("start", timer.toString())
                idx++

                // 다음으로 넘어간 것을 소리로 알림
                val toneType = ToneGenerator.TONE_CDMA_HIGH_L
                ToneGenerator(AudioManager.STREAM_ALARM, 50)
                    .startTone(toneType, 300)
                Log.e("idx", idx.toString())
                updateState(timer)

            }

            val timerMinutes = currentTimerDeciSecond.div(10) / 60
            val timerSecond = currentTimerDeciSecond.div(10) % 60

            runOnUiThread {
                binding.totalTimeTextView.text =
                    String.format("%02d:%02d", minutes, second)
                binding.tickTextView.text = deciSeconds.toString()
                binding.timerTextView.text =
                    String.format("%02d:%02d", timerMinutes, timerSecond)
//                Log.e("progress", "${currentDeciSecond}")
//                Log.e("progress", "${currentDeciSecond.div(10)}")
//                Log.e("progress", "${currentDeciSecond.div(10) / 100}")
                binding.c25kProgressBar.progress =
                    ((currentDeciSecond / (entireTime * 10f)) * 100).toInt()
            }
        }

    }

    private fun updateState(timerTask: Timer?) {
        Log.e("length idx", "${thisDay.length()} ${idx}")
        if (idx == 0) {
            binding.leftArrow.setColorFilter(Color.GRAY);
            binding.leftArrow.isClickable = false
        }
        else {
            binding.leftArrow.setColorFilter(Color.BLACK);
            binding.leftArrow.isClickable = true

        }

        if (idx == thisDay.length()) {
            // timer 정지
            timerTask?.cancel()
            timer = null

            runOnUiThread {
                saveData()
                showFinishDialog("운동이 끝났습니다")
            }
            return
        }

        if(idx == thisDay.length() - 1) {
            binding.rightArrow.setColorFilter(Color.GRAY);
            binding.rightArrow.isClickable = false
        }
        else {
            binding.rightArrow.setColorFilter(Color.BLACK);
            binding.rightArrow.isClickable = true
        }

        currentTimerDeciSecond = 0
        time = thisDay.getJSONObject(idx).get("time") as Int

        runOnUiThread {
            binding.stateTextView.text =
                thisDay.getJSONObject(idx).get("state").toString()
            binding.sectionTimeText.text =
                String.format("%02d:%02d", (time as Int / 60), (time as Int % 60))
        }
        return
    }


    private fun showStopDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                stop()
            }
            setNegativeButton("아니오", null)
        }.show()
    }

    private fun showFinishDialog(message : String) {
        AlertDialog.Builder(this).apply {
            setMessage("$message, 종료하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                finish()
            }
            setNegativeButton("아니오", null)
        }.show()
    }

    private fun saveData() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formatted = current.format(formatter)

        with(getSharedPreferences(C25K_HISTORY, Context.MODE_PRIVATE).edit()) {
            putString("$week$day", formatted)
            apply()
        }
        Log.e("check key", "$week$day")
        Log.e(
            "check save data",
            getSharedPreferences(C25K_HISTORY, Context.MODE_PRIVATE).getString(
                "$week$day",
                "NOT FOUND"
            ).toString()
        )
        Toast.makeText(this, "운동이 기록되었습니다.", Toast.LENGTH_SHORT).show()
    }
}