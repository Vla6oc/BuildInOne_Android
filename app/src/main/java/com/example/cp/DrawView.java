package com.example.cp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DrawView extends SurfaceView {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    List<Point> circlePoints;
    List<Point> circlePoints1;
    private Context context;
    private static int flag = 1;
    private static int REFERENCE_POINT_COLOR = Color.parseColor("#FF5F00");
    private static int MEASURE_POINT_COLOR = Color.GREEN;
    private static int k = 0;
    private static int ki = 0;

    public DrawView(Context context) {
        super(context);
        this.context = context;
        paint.setStyle(Paint.Style.FILL);
        circlePoints = new ArrayList<>();
        circlePoints1 = new ArrayList<>();
        setWillNotDraw(false);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int size = circlePoints.size();
        int size1 = circlePoints1.size();
        Paint paint1 = new Paint();
        for (int i = 0; i < size1; i++) {
            paint.setColor(REFERENCE_POINT_COLOR);
            paint.setStrokeWidth(7);
            Point p1 = circlePoints1.get(i);
            canvas.drawCircle(p1.x, p1.y, 7, paint);
            if (i == 1) {
                canvas.drawLine(circlePoints1.get(0).x, circlePoints1.get(0).y, circlePoints1.get(1).x, circlePoints1.get(1).y, paint);
            }
        }

        for (int i = 0; i < size; i++) {
                paint.setColor(MEASURE_POINT_COLOR);
                Point p = circlePoints.get(i);
                paint.setStrokeWidth(7);
                paint1.setStrokeWidth(7);
                if (ki < 1)
                {
                    canvas.drawCircle(p.x, p.y, 7, paint);
                    if (i == 1) {
                        canvas.drawLine(circlePoints.get(0).x, circlePoints.get(0).y, circlePoints.get(1).x, circlePoints.get(1).y, paint);
                    }
                }
                if (ki >= 1)
                {
                    paint.setColor(MEASURE_POINT_COLOR);
                    canvas.drawCircle(p.x, p.y, 7, paint);
                    if (i == 1) {
                        canvas.drawLine(circlePoints.get(0).x, circlePoints.get(0).y, circlePoints.get(1).x, circlePoints.get(1).y, paint);
                    }
                }
                ki++;
                canvas.drawCircle(p.x, p.y, 7, paint);
                if (i == 2){
                    canvas.drawLine(circlePoints.get(1).x, circlePoints.get(1).y, circlePoints.get(2).x, circlePoints.get(2).y, paint);
                    canvas.drawLine(circlePoints.get(2).x, circlePoints.get(2).y, circlePoints.get(0).x, circlePoints.get(0).y, paint);
                }
                if (i == 3) {
                    paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    canvas.drawLine(circlePoints.get(2).x, circlePoints.get(2).y, circlePoints.get(0).x, circlePoints.get(0).y, paint1);
                    canvas.drawCircle(circlePoints.get(2).x, circlePoints.get(2).y, 7, paint);
                    canvas.drawCircle(circlePoints.get(0).x, circlePoints.get(0).y, 7, paint);
                    canvas.drawLine(circlePoints.get(0).x, circlePoints.get(0).y, circlePoints.get(1).x, circlePoints.get(1).y, paint);
                    canvas.drawLine(circlePoints.get(1).x, circlePoints.get(1).y, circlePoints.get(2).x, circlePoints.get(2).y, paint);
                    canvas.drawLine(circlePoints.get(2).x, circlePoints.get(2).y, circlePoints.get(3).x, circlePoints.get(3).y, paint);
                    canvas.drawLine(circlePoints.get(3).x, circlePoints.get(3).y, circlePoints.get(0).x, circlePoints.get(0).y, paint);

                }
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            if (circlePoints.size() < 4) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (circlePoints1.size() < 2) circlePoints1.add(new Point(Math.round(event.getX()), Math.round(event.getY())));
                    else if (circlePoints1.size() >= 2) circlePoints.add(new Point(Math.round(event.getX()), Math.round(event.getY())));
                    /*if (k == 1)
                    {
                        clearCanvas();
                    }*/
                    invalidate();
                    if (circlePoints1.size() == 2) {
                        ((TextView) ((Activity) context).findViewById(R.id.info_lbl)).setText(getResources().getString(R.string.setMeasurePoints));
                    }
                    if (circlePoints.size() == 4) {
                        ((TextView) ((Activity) context).findViewById(R.id.info_lbl)).setText(getResources().getString(R.string.setScaleValue));
                    }
                    Log.d("log_tag", Integer.toString(circlePoints.size()));
                    k++;
                }
        }

        return false;
    }

    public void clearCanvas() {
            circlePoints.clear();
            ((TextView) ((Activity) context).findViewById(R.id.info_lbl)).setText(getResources().getString(R.string.setMeasurePoints));
            invalidate();
    }
    public void clearCanvas1() {
        circlePoints.clear();
        circlePoints1.clear();
        k = 0;
        ki = 0;
        ((TextView) ((Activity) context).findViewById(R.id.info_lbl)).setText(getResources().getString(R.string.setReferencePoints));
        invalidate();
    }

    public String calculate(double reference, int inputUnitIndex, int outputUnitIndex) {
        if (circlePoints.size() < 2) {
            Toast.makeText(context, getResources().getString(R.string.error_noPoints), Toast.LENGTH_SHORT).show();
            return null;
        }
        return Ruler.compute(circlePoints1, circlePoints, reference, inputUnitIndex, outputUnitIndex);
    }
}
