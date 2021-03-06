package com.example.cp;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class Utils {
    private Utils() {
    }

    @TargetApi(19)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            } else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                switch (type) {
                    case "image":
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "video":
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "audio":
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        break;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static double inchesToMeters(double measurement) {
        return measurement * 0.0254;
    }

    public static double yardsToMeters(double measurement) {
        return measurement * 0.9144;
    }

    public static double millimetersToMeters(double measurement) {
        return measurement * 0.001;
    }
    public static double centimetersToMeters(double measurement) {
        return measurement * 0.01;
    }

    public static double metersToYards(double measurement) {
        return measurement * 1.0936133;
    }

    public static double metersToFeet(double measurement) {
        return measurement * 3.2808399;
    }

    public static double metersToInch(double measurement) {
        return measurement * 39.3700787;
    }

    public static double metersToMillimeters(double measurement) {
        return measurement * 1000;
    }

    public static double metersToCentimeters(double measurement) {
        return measurement * 100;
    }



    public static double inchesToMetersS(double measurement) {
        return measurement * 0.0254 * 0.0254;
    }

    public static double yardsToMetersS(double measurement) {
        return measurement * 0.9144 * 0.9144;
    }

    public static double millimetersToMetersS(double measurement) {
        return measurement * 0.001 * 0.001;
    }

    public static double centimetersToMetersS(double measurement) {
        return measurement * 0.01 * 0.01;
    }

    public static double metersToYardsS(double measurement) {
        return measurement * 1.0936133 * 1.0936133;
    }

    public static double metersToFeetS(double measurement) {
        return measurement * 3.2808399 * 3.2808399;
    }

    public static double metersToInchS(double measurement) {
        return measurement * 39.3700787 * 39.3700787;
    }

    public static double metersToMillimetersS(double measurement) {
        return measurement * 1000 * 1000;
    }

    public static double metersToCentimetersS(double measurement) {
        return measurement * 100 * 100;
    }
}
