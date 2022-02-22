package com.example.pushups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Train extends AppCompatActivity implements SensorEventListener {
    TextView pushUps;
    Integer push=0;
    Button start;
    SensorManager sensorManager;
    Sensor proximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        pushUps = findViewById(R.id.pushUps);
        start = findViewById(R.id.start);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,proximity,sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //taking the values from the sensor and increasing the counter if the value of the
        //proximity is 0
        if(start.getText().equals("STOP")&& event.values[0] == 0){
            push += 1;
            pushUps.setText(push.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void start(View view) {
        if(start.getText().equals("START")){
            start.setText("STOP");
        }else {
            start.setText("START");
        }
    }

    public void exit(View view) {
        update();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }


    @Override
    public void onBackPressed() {
        update();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
    
    public void update(){
        SharedPreferences h=getSharedPreferences("MR",MODE_PRIVATE);
        int pmonth = h.getInt("pmonth",0);
        int pweek = h.getInt("pweek",0);
        int pday = h.getInt("pday",0);
        h.edit().putInt("pmonth",push+pmonth).putInt("pweek",push+pweek).putInt("pday",pday+push).commit();
    }
}