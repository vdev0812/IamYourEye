package com.example.iamyoureye;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.camera.view.PreviewView;

import kotlin.jvm.internal.Intrinsics;

public class ColorDetectHandler {
    private String hex = "";
    private int red;
    private int green;
    private int blue;
    private int rgb;
    private Bitmap bitmap;

    private void reset() {
        this.hex = "";
        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    public UserColor detect(PreviewView cameraView, View pointer) {
        Intrinsics.checkNotNullParameter(cameraView, "cameraView");
        Intrinsics.checkNotNullParameter(pointer, "pointer");
        this.reset();
        float x = pointer.getX() + (float) (pointer.getWidth() / 2);
        float y = pointer.getY() + (float) (pointer.getHeight() / 2);
        this.bitmap = cameraView.getBitmap();
        Bitmap bitmap2 = this.bitmap;
        int pixel = bitmap2.getPixel((int) x, (int) y);
        this.red = Color.red(pixel);
        this.blue = Color.blue(pixel);
        this.green = Color.green(pixel);
        this.rgb = Color.rgb(red, green, blue);
        String hexColor = Integer.toHexString(this.rgb & 16777215);
        this.hex = hexColor;
        int typeColor = 0;
        for(int typeColor2 = 6 - this.hex.length(); typeColor < typeColor2; typeColor++) {
            this.hex = '0' + this.hex;
        }
        this.hex = '#' + this.hex;
        return new UserColor(this.hex, String.valueOf(this.red), String.valueOf(this.green), String.valueOf(this.blue));
    }
}
