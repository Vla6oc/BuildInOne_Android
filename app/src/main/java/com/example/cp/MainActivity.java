package com.example.cp;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.app.DialogFragment;
import android.content.Intent;

import android.net.Uri;
import android.provider.MediaStore;

import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.Source;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity implements com.example.cp.InputDialog.InputDialogListener {
    private RelativeLayout rl;
    private com.example.cp.DrawView drawView;
    private FrameLayout preview;
    private Button btn_ok;
    private Button btn_cancel;
    private TextView lab1;
    private TextView lab2;
    private ImageView btn;
    private ImageView btn1;
    private ImageView btn2;
    private File photoFile;
    private String result;
    private ImageView im;
    public int po = 1;
    public int flag = 1;
    static final int REQUEST_IMAGE_CAPTURE = 0;
    static final int REQUEST_SELECT_PHOTO = 2;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static long back_pressed;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animatoo.animateZoom(MainActivity.this);
        setContentView(R.layout.activity_main);
        Ruler ruler = new Ruler(this);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
        rl = (RelativeLayout) findViewById(R.id.input_area);
        btn = (ImageView) findViewById(R.id.ruler);
        btn1 = (ImageView) findViewById(R.id.leavel);
        btn2 = (ImageView) findViewById(R.id.light);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        btn_ok = (Button) findViewById(R.id.button_calculate);
        btn_cancel = (Button) findViewById(R.id.button_cancel);
        lab1 = (TextView) findViewById(R.id.info_lbl);
        lab2 = (TextView) findViewById(R.id.info_lbl2);
        im = (ImageView) findViewById(R.id.imageView2);
        addPictureButton();
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.f);
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.f1);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.f2);
        Animation alp = AnimationUtils.loadAnimation(this, R.anim.alpha);

        btn.startAnimation(anim);
        btn1.startAnimation(anim1);
        btn2.startAnimation(anim2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation(alp);
                new CountDownTimer(500, 500) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish() {
                        dispatchTakePictureIntent();
                    }
                }.start();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.startAnimation(alp);
                new CountDownTimer(500, 500) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish() {
                        Intent questionIntent = new Intent(MainActivity.this, level.class);
                        startActivity(questionIntent);
                    }
                }.start();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.startAnimation(alp);
                new CountDownTimer(500, 500) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish() {
                        Intent questionIntent = new Intent(MainActivity.this, light.class);
                        startActivity(questionIntent);
                    }
                }.start();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InputDialog().show(getFragmentManager(), "input_dialog");
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (back_pressed + 1000 > System.currentTimeMillis())
                   drawView.clearCanvas1();
               else
                    drawView.clearCanvas();
               back_pressed = System.currentTimeMillis();
            }
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            // Запрос разрешения
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
//            case R.id.action_settings:
//                break;
            case R.id.action_cleanStorage:
                cleanPhotoStorage();
                break;
            case R.id.action_avtor:
                information();
                break;
            case R.id.avtor:
                Avtor();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Avtor(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("")
                .setMessage(getResources().getString(R.string.fio) + "\n" + getResources().getString(R.string.gr))
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error creating image", Toast.LENGTH_SHORT);
            }
            if (photoFile != null) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void information() {
        Intent questionIntent = new Intent(MainActivity.this, AndV.class);
        startActivity(questionIntent);
    }

    public void addPictureButton() {
        preview.removeAllViews();
        btn_cancel.setVisibility(View.GONE);
        btn_ok.setVisibility(View.GONE);
        lab1.setVisibility(View.GONE);
        lab2.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);
        getActionBar().show();
    }

    private void pictureTaken() {
        preview.removeAllViews();
        preview.setForeground(null);
        btn.clearAnimation();
        getActionBar().hide();
        btn.setVisibility(View.GONE);
        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.VISIBLE);
        btn_ok.setVisibility(View.VISIBLE);
        rl.setVisibility(View.VISIBLE);
        lab1.setVisibility(View.VISIBLE);
        lab2.setVisibility(View.GONE);
        com.example.cp.ImageSurface image = new com.example.cp.ImageSurface(this, photoFile);
        preview.addView(image);
        im.setImageURI(Uri.fromFile(photoFile));
        ((TextView) findViewById(R.id.info_lbl)).setText(getResources().getString(R.string.setReferencePoints));
        drawView = new com.example.cp.DrawView(this);
        preview.addView(drawView);
        preview.setForeground(null);
    }
    @Override
    public void onBackPressed() {
        if (flag % 2 != 0)
        {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        else
        {
            super.onBackPressed();
        }
        flag++;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK)
                {
                    pictureTaken();
                }
                break;
            case REQUEST_SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String filePath = Utils.getPath(this, uri);
                    photoFile = new File(filePath);
                    pictureTaken();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );
    }

    private void cleanPhotoStorage() {
        File storageDir = getExternalFilesDir(null);
        File fList[] = storageDir.listFiles();
        for (int i = 0; i < fList.length; i++) {
            String pes = fList[i].getName();
            if (pes.endsWith(".png"))
                new File(fList[i].getAbsolutePath()).delete();
        }
        Toast.makeText(this, getResources().getString(R.string.storageDeleted), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        int inputUnit = ((Spinner) dialog.getDialog().findViewById(R.id.input_unit_chooser)).getSelectedItemPosition();
        int outputUnit = ((Spinner) dialog.getDialog().findViewById(R.id.output_unit_chooser)).getSelectedItemPosition();
        try {
            double reference = Double.parseDouble(((EditText) dialog.getDialog().findViewById(R.id.reference_input)).getText().toString());
            result = drawView.calculate(reference, inputUnit, outputUnit);
            showResult();
        } catch (NumberFormatException ex) {
            Toast.makeText(this, getResources().getString(R.string.error_numberFormat), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

    private void showResult() {
        if (result != null) {
            // DecimalFormat decimalFormat = new DecimalFormat("#.##");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(result);
            builder.create().show();
        }
    }
}