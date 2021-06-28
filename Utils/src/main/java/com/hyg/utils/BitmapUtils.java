package com.hyg.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @Author 韩永刚
 * @Date 2021/06/01
 * @Desc
 */
public final class BitmapUtils {

    public BitmapUtils() {
        throw new RuntimeException("Stub!");
    }


    public static Bitmap getBitmap(@NonNull Context context,int resId){
        return BitmapFactory.decodeResource(context.getResources(),resId);
    }
    public static boolean compress(@NonNull Bitmap bt, @NonNull Bitmap.CompressFormat format, int quality, String targetPath) {
        File file = new File(targetPath);
        if (file.exists()) {
            file.delete();
        }
        try {
            return compress(bt, format, quality, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compress(@NonNull Bitmap bt, int quality, @NonNull OutputStream os) {
        return compress(bt, Bitmap.CompressFormat.PNG, quality, os);
    }

    public static boolean compress(@NonNull Bitmap bt, @NonNull Bitmap.CompressFormat format, int quality, @NonNull OutputStream os) {
        return bt.compress(format, quality, os);
    }
}
