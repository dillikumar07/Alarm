package com.example.alarmset;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public TimePicker timePicker;
    public ToggleButton toggleButton;
    public Button SetAlarm;
    public AlarmManager alarmManager;
    public PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker1);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        SetAlarm = (Button) findViewById(R.id.button);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE);

        SetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlarm();
            }
        });
    }

    public void setAlarm(){
        Calendar calendar =Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
        calendar.set(Calendar.MINUTE,timePicker.getMinute());
        calendar.set(Calendar.SECOND,0);
        if(calendar.getTimeInMillis() <= System.currentTimeMillis()){
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
        if(toggleButton.isChecked()){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
            Toast.makeText(this, "Alarm Set at "+timePicker.getHour()+":"+timePicker.getMinute(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
