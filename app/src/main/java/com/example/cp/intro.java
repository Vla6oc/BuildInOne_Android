package com.example.cp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class intro extends Activity {
    private FrameLayout logo;
    private ImageView logo1;
    private ImageView logo2;
    private ImageView logo3;
    private ConstraintLayout cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animatoo.animateZoom(intro.this);
        setContentView(R.layout.intro);
        logo1 = (ImageView) findViewById(R.id.logo1);
        logo2 = (ImageView) findViewById(R.id.logo2);
        logo3 = (ImageView) findViewById(R.id.logo3);
        cl = (ConstraintLayout) findViewById(R.id.clc);
        Animation lo1 = AnimationUtils.loadAnimation(this, R.anim.log1);
        Animation lo2 = AnimationUtils.loadAnimation(this, R.anim.log2);
        Animation lo3 = AnimationUtils.loadAnimation(this, R.anim.log3);
        getActionBar().hide();
        logo1.startAnimation(lo1);
        logo2.startAnimation(lo2);
        logo3.startAnimation(lo3);
            new CountDownTimer(2500, 500) {
                @Override
                public void onTick(long l) {

                }
                @Override
                public void onFinish() {
                    Intent questionIntent = new Intent(intro.this, MainActivity.class);
                    startActivity(questionIntent);
                }
            }.start();
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questionIntent = new Intent(intro.this, MainActivity.class);
                startActivity(questionIntent);
            }
        });
    }
}