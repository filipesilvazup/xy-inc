package com.uberlandia.financas.filipe.exemploomdb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.dao.FilmeDatabase;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static void setImagePicasso(final String url, final ImageView imageView) {
        Picasso.get()
                .load(url)
                .resize(280, 390)
                .centerCrop()
                .error(R.drawable.ic_wallpaper)
                .placeholder(R.drawable.ic_wallpaper)
                .into(imageView);
    }

    public static byte[] convertBitmapToArrayByte(final ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static FilmeDatabase getFilmeDatabaseInstance(Context context) {
        return FilmeDatabase.getInstance(context);
    }
}
