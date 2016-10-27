package com.egco428.accelerometer;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.GREEN);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event){
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();


        if (accelationSquareRoot >= 2 && accelationSquareRoot <= 4)
        {
            Random ran = new Random();
            int num = ran.nextInt(100);
            TextView number = (TextView)findViewById(R.id.number);
            number.setText(String.valueOf(num));

            if (actualTime - lastUpdate < 700) {
                return;
            }

            lastUpdate = actualTime;
//            Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT).show();

            if (color) {
                view.setBackgroundColor(Color.GREEN);

            } else {
                view.setBackgroundColor(Color.RED);
            }

            color = !color;

        }
//        else{
//            Toast.makeText(this, "Device was not shuffled", Toast.LENGTH_SHORT).show();
//        }
        else if (accelationSquareRoot >= 5 && accelationSquareRoot <= 8)
        {
            if (actualTime - lastUpdate < 700) {
                return;
            }

            lastUpdate = actualTime;
//            Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT).show();

            if (color) {
                view.setBackgroundColor(Color.GREEN);

            } else {
                view.setBackgroundColor(Color.BLUE);
            }

            color = !color;

        }
//        else{
//            Toast.makeText(this, "Device was not shuffled", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
