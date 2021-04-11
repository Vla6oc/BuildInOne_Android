package com.example.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class level extends Activity {

    private SensorManager sm;
    private Sensor s;
    private ImageView iv;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private TextView tv;
    private TextView tv1;
    private FrameLayout fr;
    private SensorEventListener sl;
    public int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animatoo.animateZoom(level.this);
        setContentView(R.layout.level);
        tv = findViewById(R.id.textView);
        tv1 = findViewById(R.id.textView2);
        iv = findViewById(R.id.im);
        fr = findViewById(R.id.frl);
        iv1 = findViewById(R.id.im2);
        iv2 = findViewById(R.id.im4);
        iv3 = findViewById(R.id.im6);
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if (sm != null)
        {
            s = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        }
        sl = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(
                        rotationMatrix, event.values);
                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_Z ,
                        SensorManager.AXIS_Y,
                        remappedRotationMatrix);
                float[] orient = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orient);
                for (int i = 0; i < 3; i++)
                {
                    orient[i] = (float)(Math.toDegrees(orient[i]));
                }
                int y_b = (int)(orient[1]);
                //---Уровень по Y
                tv1.setText(String.valueOf(y_b) + "°");
                tv1.setRotation((int)(y_b+90));
                iv2.setTranslationY(y_b*10);
                if (y_b == 0)
                    iv2.setImageResource(R.drawable.ball_green);
                else
                    iv2.setImageResource(R.drawable.ball);
                if (iv2.getTranslationY() >= 350)
                {
                    iv2.setTranslationY(350);
                }
                if (iv2.getTranslationY() <= -350)
                {
                    iv2.setTranslationY(-350);
                }


                float[] rotationMatrix1 = new float[16];
                SensorManager.getRotationMatrixFromVector(
                        rotationMatrix1, event.values);
                float[] remappedRotationMatrix1 = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_Y,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix1);
                float[] orient1 = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix1, orient1);
                for (int i = 0; i < 3; i++)
                {
                    orient1[i] = (float)(Math.toDegrees(orient1[i]));
                }
                //---Уровень по X
                int x_b = (int)(orient1[1]);
                tv.setText(String.valueOf(-x_b) + "°");
                tv.setRotation(-x_b);
                iv.setTranslationX(-x_b*10);
                if (x_b == 0)
                    iv.setImageResource(R.drawable.ball_green);
                else
                    iv.setImageResource(R.drawable.ball);
                if (iv.getTranslationX() >= 350)
                {
                    iv.setTranslationX(350);
                }
                if (iv.getTranslationX() <= -350)
                {
                    iv.setTranslationX(-350);
                }



                iv3.setTranslationX(-x_b*10);
                iv3.setTranslationY(y_b*10);
                if (x_b == 0 && y_b == 0)
                    iv3.setImageResource(R.drawable.ball_green);
                else
                    iv3.setImageResource(R.drawable.ball);
                if (iv3.getTranslationX() >= 225)
                {
                    iv3.setTranslationX(225);
                }
                if (iv3.getTranslationX() <= -225)
                {
                    iv3.setTranslationX(-225);
                }
                if (iv3.getTranslationY() >= 225)
                {
                    iv3.setTranslationY(225);
                }
                if (iv3.getTranslationY() <= -225)
                {
                    iv3.setTranslationY(-225);
                }

            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    public void onBackPressed() {
            Intent questionIntent = new Intent(level.this, MainActivity.class);
            startActivity(questionIntent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sl, s, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sl);
    }
}