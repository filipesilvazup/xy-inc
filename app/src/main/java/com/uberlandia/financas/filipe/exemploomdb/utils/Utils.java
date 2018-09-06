package com.uberlandia.financas.filipe.exemploomdb.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static void setImagePicasso(final String url, final ImageView imageView){
        Picasso.get()
                .load(url)
                .resize(280, 390)
                .centerCrop()
                .into(imageView);
    }

    public static byte[] convertBitmapToArrayByte(final ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)  imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
