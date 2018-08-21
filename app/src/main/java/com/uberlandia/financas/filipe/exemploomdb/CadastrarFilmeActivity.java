package com.uberlandia.financas.filipe.exemploomdb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarFilmeActivity extends AppCompatActivity {
    private  byte[] array;
    private String imdbId;
    private ImageView iv_poster;
    private Bitmap bitmapImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_filme);
        iv_poster = findViewById(R.id.img_filme);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imdbId = bundle.getString("imdbid");
        array = bundle.getByteArray("img");
        bitmapImage = BitmapFactory.decodeByteArray(array, 0, array.length);
        iv_poster.setImageBitmap(bitmapImage);

        Call<Result> call = new RetrofitConfig().getFilmeService().buscarFilme(imdbId, "45023bb7");
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result f = response.body();

                adapter = new MyAdapter(f.getSearch());
                listMovies.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("FilmeService   ", "Erro ao buscar o filme:" + t.getMessage());
            }
        });
    }
}
