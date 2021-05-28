package com.example.cp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.Locale;

public class light extends Activity {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private TextView textView;
    private ImageView imageView;
    private ImageView norma;
    private ImageView qes;
    public boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animatoo.animateZoom(light.this);
        setContentView(R.layout.light);
        textView = (TextView) findViewById(R.id.ttt);
        imageView = (ImageView)findViewById(R.id.imageView4);
        qes = (ImageView)findViewById(R.id.imQ);
        norma = (ImageView)findViewById(R.id.norm);
        Animation alp = AnimationUtils.loadAnimation(this, R.anim.alpha);
        qes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qes.startAnimation(alp);
                new CountDownTimer(500, 500) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish() {
                        textView.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        norma.setVisibility(View.VISIBLE);
                        qes.setVisibility(View.GONE);
                        flag = true;
                    }
                }.start();
            }
        });
       // String lang = Locale.getDefault().getLanguage();
       // if(lang.equals("ru"))
       //     imageView.setBackground(getResources().getDrawable(R.drawable.norma));
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
                    if ((int)event.values[0] <= 600)
                        imageView.setImageAlpha((int)event.values[0]/5);
                    if ((int)event.values[0] > 600)
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
        if (flag == false) {
            Intent questionIntent = new Intent(light.this, MainActivity.class);
            startActivity(questionIntent);
        }
        if (flag == true)
        {
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            norma.setVisibility(View.GONE);
            qes.setVisibility(View.VISIBLE);
            flag = false;
        }
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