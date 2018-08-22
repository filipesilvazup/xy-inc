package com.uberlandia.financas.filipe.exemploomdb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarFilmeActivity extends AppCompatActivity {
    private  byte[] array;
    private String imdbId;
    private SquareImageView iv_poster;
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

        Call<FilmeSelecionado> call = new RetrofitConfig().getFilmeService().buscarFilme(imdbId, "45023bb7");
        call.enqueue(new Callback<FilmeSelecionado>() {
            @Override
            public void onResponse(Call<FilmeSelecionado> call, Response<FilmeSelecionado> response) {
                FilmeSelecionado f = response.body();
                System.out.println(f.toString());


                //adapter = new MyAdapter(f.getSearch());
                //listMovies.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<FilmeSelecionado> call, Throwable t) {
                Log.e("FilmeService   ", "Erro ao buscar o filme:" + t.getMessage());
            }
        });
    }
}
