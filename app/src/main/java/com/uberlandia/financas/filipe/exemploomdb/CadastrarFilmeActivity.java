package com.uberlandia.financas.filipe.exemploomdb;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.uberlandia.financas.filipe.exemploomdb.dao.FilmeDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarFilmeActivity extends AppCompatActivity {
    private FilmeDatabase movieDatabase;
    private FilmeSelecionado f;
    private static final String DATABASE_NAME = "filmes_db";
    private  byte[] array;
    private String imdbId;
    private SquareImageView iv_poster;
    private TextView tv_info;
    private TextView tv_descricao;
    private TextView tv_director;
    private TextView tv_actors;
    private TextView tv_rated;
    private TextView tv_released;
    private TextView tv_writer;
    private TextView tv_language;
    private TextView tv_country;
    private TextView imdb_rating;
    private TextView metascore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_cadastrar_filme);
        iv_poster = findViewById(R.id.img_filme);
        tv_descricao = findViewById(R.id.tv_descricao);
        tv_info = findViewById(R.id.tv_info);
        tv_director = findViewById(R.id.tv_director);
        tv_actors = findViewById(R.id.tv_actors);
        tv_rated = findViewById(R.id.tv_rated);
        tv_released = findViewById(R.id.tv_released);
        tv_writer = findViewById(R.id.tv_writer);
        tv_language = findViewById(R.id.tv_language);
        tv_country = findViewById(R.id.tv_country);
        imdb_rating = findViewById(R.id.imdb_rating);
        metascore = findViewById(R.id.metascore_rating);

        //criação do banco de dados
        movieDatabase = Room.databaseBuilder(getApplicationContext(),
                FilmeDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imdbId = bundle.getString("imdbid");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("FILIPE \n " +
                         f.toString());
                        movieDatabase.daoAccess().insertFilme(f);
                        System.out.println("DEU CERTO "+movieDatabase.daoAccess().findAll());
                    }
                }).start();



            }
        });


        Call<FilmeSelecionado> call = new RetrofitConfig().getFilmeService().buscarFilme(imdbId, "45023bb7");
        call.enqueue(new Callback<FilmeSelecionado>() {
            @Override
            public void onResponse(Call<FilmeSelecionado> call, Response<FilmeSelecionado> response) {
                f = response.body();
                Picasso.get()
                        .load(f.getPoster())
                        .resize(150, 150)
                        .centerCrop()
                        .into(iv_poster);
                System.out.println(f.toString());
                setTitle(f.getTitle());
                tv_info.setText(f.getYear() + "  -  "+ f.getRuntime() + "  -  "+ f.getGenre());
                tv_descricao.setText(f.getPlot());
                tv_director.setText(f.getDirector());
                tv_actors.setText(f.getActors());
                tv_rated.setText(f.getRated());
                tv_released.setText(f.getReleased());
                tv_writer.setText(f.getWriter());
                tv_language.setText(f.getLanguage());
                tv_country.setText(f.getCountry());
                imdb_rating.setText(f.getImdbRating());
                metascore.setText(f.getMetascore());

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
