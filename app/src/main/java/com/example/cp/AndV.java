package com.example.cp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintSet;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class AndV extends Activity {
    FrameLayout f1;
    FrameLayout f2;
    FrameLayout fs1;
    FrameLayout f;
    TextView tv;
    private int flag = 0;
    private int height = 0;
    private int width = 0;
    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endv);
        f1 = (FrameLayout) findViewById(R.id.Layout);
        f2 = (FrameLayout) findViewById(R.id.Layout);
        f = (FrameLayout) findViewById(R.id.frf);
        tv = (TextView) findViewById(R.id.tex);
        fs1 = (FrameLayout) findViewById(R.id.but_swap1);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;
        width = size.x;

        getActionBar().hide();
        Animation start = AnimationUtils.loadAnimation(this, R.anim.start);
        Animation end = AnimationUtils.loadAnimation(this, R.anim.end);
        tv.setText(getResources().getText(R.string.t) + "\n" + getResources().getText(R.string.t0));
        fs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    f.setVisibility(View.GONE);
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f1.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t1));
                        }
                        @Override
                        public void onFinish() {
                            f2.setForeground(getResources().getDrawable(R.drawable.camera));
                            f2.startAnimation(start);
                        }
                    }.start();
                    tv.setTranslationY(-height / 2 + 200);
                }
                if (flag == 1) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f2.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t2));
                        }
                        @Override
                        public void onFinish() {
                            f1.setForeground(getResources().getDrawable(R.drawable.but));
                            f1.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 2) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f1.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t2));
                        }
                        @Override
                        public void onFinish() {
                            f2.setForeground(getResources().getDrawable(R.drawable.line1));
                            f2.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 3) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f2.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t3));
                        }
                        @Override
                        public void onFinish() {
                            f1.setForeground(getResources().getDrawable(R.drawable.line2));
                            f1.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 4) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f1.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t4));
                        }
                        @Override
                        public void onFinish() {
                            f2.setForeground(getResources().getDrawable(R.drawable.ok));
                            f2.startAnimation(start);
                        }
                    }.start();
                    tv.setTranslationY(-height / 2 + 200);
                }
                if (flag == 5) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f2.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t5));
                        }
                        @Override
                        public void onFinish() {
                            f1.setForeground(getResources().getDrawable(R.drawable.len));
                            f1.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 6) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f1.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t6) + "\n" + getResources().getText(R.string.t66));
                        }
                        @Override
                        public void onFinish() {
                            f2.setForeground(getResources().getDrawable(R.drawable.len1));
                            f2.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 7) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f2.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t7));
                        }
                        @Override
                        public void onFinish() {
                            f1.setForeground(getResources().getDrawable(R.drawable.tre));
                            f1.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 8) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f1.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t7));
                        }
                        @Override
                        public void onFinish() {
                            f2.setForeground(getResources().getDrawable(R.drawable.tre1));
                            f2.startAnimation(start);
                        }
                    }.start();
                    tv.setTranslationY(-height / 2 + 200);
                }
                if (flag == 9) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f2.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t7));
                        }
                        @Override
                        public void onFinish() {
                            f1.setForeground(getResources().getDrawable(R.drawable.tre3));
                            f1.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 10) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f1.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t8));
                        }
                        @Override
                        public void onFinish() {
                            f2.setForeground(getResources().getDrawable(R.drawable.pr1));
                            f2.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 11) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f2.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t8));
                        }
                        @Override
                        public void onFinish() {
                            f1.setForeground(getResources().getDrawable(R.drawable.pr2));
                            f1.startAnimation(start);
                        }
                    }.start();
                }
                if (flag == 12) {
                    new CountDownTimer(300, 300) {
                        @Override
                        public void onTick(long l) {
                            f1.startAnimation(end);
                            tv.setText(getResources().getText(R.string.t8));
                        }
                        @Override
                        public void onFinish() {
                            f2.setForeground(getResources().getDrawable(R.drawable.pr3));
                            f2.startAnimation(start);
                            flag = -1;
                        }
                    }.start();
                }
                flag++;
            }
        });
    }
}
