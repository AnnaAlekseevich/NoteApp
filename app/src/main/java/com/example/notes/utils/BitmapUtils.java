package com.example.notes.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;

import com.example.notes.R;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BitmapUtils  {

    private static String TAG = "SHARE_BITMAP";

    public static List<Bitmap> createBitmaps(Context context, String text) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(context.getResources().getDimensionPixelSize(
                R.dimen.text_image_size
        ));
        String checked_text_start = "";
        String delimeter = " "; // Разделитель
        String[] words = text.split(delimeter); // Разделения строки text с помощью метода split()
        int bitmap_width = 500, bitmap_height = 500;
        List<Bitmap> bitmapList = new ArrayList();
        int padding = context.getResources().getDimensionPixelSize(
                R.dimen.text_image_padding
        );

        int available_text_width = bitmap_width - 2 * padding;




        Bitmap bitmap = Bitmap.createBitmap(bitmap_width, bitmap_height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(ContextCompat.getColor(context, R.color.bitmap_background));


//        -----------------------узнать высоту строчки с нужным шрифтом
        Rect lb = new Rect();
        paint.getTextBounds("I", 0, 1, lb);
//------------------------------------------------------------------------
        int available_text_height_end = bitmap_height - padding;

        int currentH = padding + lb.height();

        for (int i = 0; i < words.length; i++) {
            String newWord = words[i] + " ";

            String checked_text_end = checked_text_start + newWord;

            Rect bounds = new Rect();
            paint.getTextBounds(checked_text_end, 0, checked_text_end.length(), bounds);
            int current_text_width = bounds.width();

            if (current_text_width < available_text_width) {
                checked_text_start += newWord;
            } else {
                canvas.drawText(checked_text_start, padding,currentH, paint);
                currentH += bounds.height() + 10;
                checked_text_start = newWord;

                if (currentH >= available_text_height_end) {
                    currentH = padding + lb.height();
                    bitmapList.add(bitmap);
                    bitmap = Bitmap.createBitmap(bitmap_width, bitmap_height, Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(bitmap);
                    canvas.drawColor(ContextCompat.getColor(context, R.color.bitmap_background));
                }
            }
        }
        canvas.drawText(checked_text_start, padding,currentH, paint);
        bitmapList.add(bitmap);

        return bitmapList;
    }

    public static Uri saveImage(Context context, Bitmap image) {
        //TODO - Should be processed in another thread
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            File file = new File(imagesFolder, "shared_image.png");

            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", file);

        } catch (IOException e) {
            Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }



}
