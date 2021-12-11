package com.example.trackrecorder.helpers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

/**
 * Created by Oleh Filipchuk ( olegf.droid@gmail.com ) on 06.12.2021.
 */

public class Adapters {

    @BindingAdapter("error_field")
    public static void setError(TextView view, String error) {

        if (error != null) {
            view.setError(error);
        }
    }

    @BindingAdapter("setBitmap")
    public static void setBitmapDrawable(ImageView view, Bitmap bitmap) {
        view.setImageDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
    }

}
