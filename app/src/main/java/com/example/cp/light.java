package com.example.cp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class light extends Activity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animatoo.animateZoom(light.this);
        setContentView(R.layout.light);
        textView = (TextView) findViewById(R.id.ttt);
        imageView = (ImageView)findViewById(R.id.imageView4);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null)
        {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                if (event.sensor.getType() == Sensor.TYPE_LIGHT)
                {
                    textView.setText("" + (int)event.values[0] + " " + getResources().getString(R.string.lx));
                    if ((int)event.values[0] <= 1275)
                        imageView.setImageAlpha((int)event.values[0]/5);
                    if ((int)event.values[0] > 1275)
                        imageView.setImageAlpha(255);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    @Override
    public void onBackPressed() {
        Intent questionIntent = new Intent(light.this, MainActivity.class);
        startActivity(questionIntent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}