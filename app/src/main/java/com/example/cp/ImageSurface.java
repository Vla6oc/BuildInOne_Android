package com.example.cp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;


public class ImageSurface extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap icon;
    private Paint paint;

    public ImageSurface(Context context, File image) {
        super(context);
        try{
            String imageFile = image.getAbsolutePath();
            getHolder().addCallback(this);
            icon = BitmapFactory.decodeFile(imageFile);
            paint = new Paint();
        } catch (Exception ex)
        {
            Log.d("log_tag",ex.getMessage());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        int newWidth = canvas.getHeight() * icon.getWidth() / icon.getHeight();
        if (canvas.getHeight() < newWidth)
        {
            icon = Bitmap.createScaledBitmap(icon, newWidth/2, canvas.getHeight()/2, true);
            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(icon, 0, 0, icon.getWidth(), icon.getHeight(), matrix, true);
            int cx = (canvas.getWidth() - rotatedBitmap.getWidth())/2;
            int cy = (canvas.getHeight()- rotatedBitmap.getHeight())/2;
            canvas.drawBitmap(rotatedBitmap, cx, cy, paint);
        }
        if (canvas.getHeight() > newWidth)
        {
            icon = Bitmap.createScaledBitmap(icon, newWidth, canvas.getHeight(), true);
            int cx = (canvas.getWidth() - icon.getWidth())/2;
            int cy = (canvas.getHeight()- icon.getHeight())/2;
            canvas.drawBitmap(icon, cx, cy, paint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @SuppressLint("WrongCall")
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas(null);
            synchronized (holder) {
                onDraw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
