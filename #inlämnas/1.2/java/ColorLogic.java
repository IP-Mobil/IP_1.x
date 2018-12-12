package com.example.mattiashellman.comp_colors;

import android.graphics.Color;
import android.util.Log;

/**
 * Represents a complementary color calculated from a specified input color
 */
class ColorLogic {

    private int red;
    private int green;
    private int blue;
    private int compColor;
    private MidwayBrightnessListener listener;

    /**
     * Constructs a new ColorLogic set to the specified RGB values
     */
    ColorLogic(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Constructs a new ColorLogic set to the specified int color
     */
    ColorLogic(int color) {
        this.red = Color.red(color);
        this.green = Color.green(color);
        this.blue = Color.blue(color);
    }

    int getColor() {
        return Color.rgb(red, green, blue);
    }

    int getRed() {
        return red;
    }

    int getGreen() {
        return green;
    }

    int getBlue() {
        return blue;
    }

    void setRed(int red) {
        this.red = red;
    }

    void setGreen(int green) {
        this.green = green;
    }

    void setBlue(int blue) {
        this.blue = blue;
    }

    int getCompColor() {
        calcCompColor();
        return compColor;
    }

    int getCompRed() {
        calcCompColor();
        return Color.red(compColor);
    }

    int getCompGreen() {
        calcCompColor();
        return Color.green(compColor);
    }

    int getCompBlue() {
        calcCompColor();
        return Color.blue(compColor);
    }

    /**
     * Checks luminance on current complementary color and makes calls to MidwayBrightnessListener
     */
    private void checkLuminance() {
        float luma = Color.luminance(compColor);

        if (luma > 0.48) {
            listener.onLightThreshold();
        } else if (luma < 0.44) {
            listener.onDarkThreshold();
        }

        Log.d("1.x", String.valueOf(luma));
    }

    /**
     * Calculates and updates the complementary color
     */
    private void calcCompColor() {
        float[] hsvCol = new float[3];
        Color.RGBToHSV(getRed(), getGreen(), getBlue(), hsvCol);
        hsvCol[0] = (hsvCol[0] + 180) % 360;

        compColor = Color.HSVToColor(hsvCol);
        checkLuminance();
    }

    /**
     * Interface class used for listening to changes to brightness
     */
    interface MidwayBrightnessListener {
        void onDarkThreshold();
        void onLightThreshold();
    }

    /**
     * Assign a MidwayBrightnessListener to get callbacks on brightness changes
     */
    void setMidwayBrightnessListener(MidwayBrightnessListener listener) {
        this.listener = listener;
    }
}
