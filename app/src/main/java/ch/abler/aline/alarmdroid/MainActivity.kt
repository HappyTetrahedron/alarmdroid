package ch.abler.aline.alarmdroid

import android.app.AlarmManager
import android.app.Service
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.main_text)
        val alarmManager = getSystemService(Service.ALARM_SERVICE) as AlarmManager
        val alarmClockInfo = alarmManager.nextAlarmClock
        val data = alarmClockInfo?.triggerTime.toString()

        text.text = data

    }

    companion object {
        lateinit var text: TextView
    }
}
