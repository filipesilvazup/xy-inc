package com.uberlandia.financas.filipe.exemploomdb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CadastrarFilmeActivity extends AppCompatActivity {
    private  byte[] array;
    private String imdbId;
    private ImageView iv_poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_filme);
        iv_poster = findViewById(R.id.img_filme);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imdbId = bundle.getString("imdbid");
        array = bundle.getByteArray("img");
        Bitmap bitmapImage = BitmapFactory.decodeByteArray(array, 0, array.length);
        iv_poster.setImageBitmap(bitmapImage);
    }
}
