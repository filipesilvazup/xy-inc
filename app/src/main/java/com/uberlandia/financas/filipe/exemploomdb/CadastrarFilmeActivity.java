package com.uberlandia.financas.filipe.exemploomdb;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uberlandia.financas.filipe.exemploomdb.dao.FilmeDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarFilmeActivity extends AppCompatActivity {
    private FilmeDatabase movieDatabase;
    private String fragment;
    private FilmeSelecionado f;
    private byte[] array;
    private String imdbId;
    private ImageView iv_poster;
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
    private TextView tv_duracao;
    private TextView tv_year;
    private FloatingActionButton fab;
    private FloatingActionButton fabRemove;
    private Call<FilmeSelecionado> call;
    int vergonhaUnicode = 0x1F605;
    int felizUnicode = 0x1F609;
    String jacadastrou;
    String sucesso;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_cadastrar_filme);
        // layoutIMG = findViewById(R.id.layoutIMG);
        jacadastrou = new String(Character.toChars(vergonhaUnicode));
        sucesso = new String(Character.toChars(felizUnicode));
        iv_poster = findViewById(R.id.img_filme);
        tv_descricao = findViewById(R.id.tv_descricao);
        tv_director = findViewById(R.id.tv_director);
        tv_actors = findViewById(R.id.tv_actors);
        tv_rated = findViewById(R.id.tv_rated);
        tv_released = findViewById(R.id.tv_released);
        tv_writer = findViewById(R.id.tv_writer);
        tv_language = findViewById(R.id.tv_language);
        tv_country = findViewById(R.id.tv_country);
        imdb_rating = findViewById(R.id.imdb_rating);
        metascore = findViewById(R.id.metascore_rating);
        tv_duracao = findViewById(R.id.runtime_text);
        tv_year = findViewById(R.id.year_text);
        fab = findViewById(R.id.fab);
        fabRemove = findViewById(R.id.fabRemove);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imdbId = bundle.getString("imdbid");
        fragment = bundle.getString("fragment");
        movieDatabase = FilmeDatabase.getInstance(getApplicationContext());


        f = movieDatabase.daoAccess().findFilmeById(imdbId);

        toolbar = (Toolbar) findViewById(R.id.toolbar12);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (f != null) {
            fab.setVisibility(View.GONE);
            fabRemove.setVisibility(View.VISIBLE);

            if (!f.getPoster().equals("N/A")) {
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(f.getImagem(), 0, f.getImagem().length);
                iv_poster.setImageBitmap(bitmapImage);
            }
            Preencher();

        } else {
            fab.setVisibility(View.VISIBLE);
            fabRemove.setVisibility(View.GONE);
            call = new RetrofitConfig().getFilmeService().buscarFilme(imdbId, "45023bb7");
            call.enqueue(new Callback<FilmeSelecionado>() {
                @Override
                public void onResponse(Call<FilmeSelecionado> call, Response<FilmeSelecionado> response) {
                    f = response.body();
                    System.out.println(f.getPoster());
                    if (!f.getPoster().equals("N/A")) {
                        Picasso.get()
                                .load(f.getPoster())
                                .resize(200, 300)
                                .centerCrop()
                                .into(iv_poster);
                    }
                    Preencher();

                }

                @Override
                public void onFailure(Call<FilmeSelecionado> call, Throwable t) {
                    Snackbar.make(findViewById(android.R.id.content), "FALHA NA COMUNICAÇÃO " + new String(Character.toChars(0x1F61E)), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarFilmeActivity.this);
                builder.setMessage("Deseja salvar o filme: " + f.getTitle() + " ?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if (movieDatabase.daoAccess().findFilmeById(f.getImdbID()) == null) {
                                    if (!f.getPoster().equals("N/A")) {
                                        Bitmap bitmap = ((BitmapDrawable) iv_poster.getDrawable()).getBitmap();
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                        f.setImagem(stream.toByteArray());
                                    }
                                    movieDatabase.daoAccess().insertFilme(f);
                                    Snackbar.make(view, "FILME SALVO COM SUCESSO " + sucesso, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                } else {
                                    Snackbar.make(view, "VOCÊ JÁ CADASTROU ESSE FILME " + jacadastrou, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.show();
            }
        });

        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarFilmeActivity.this);
                builder.setMessage("Deseja remover o filme: " + f.getTitle() + " ?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                movieDatabase.daoAccess().deleteByID(f.getImdbID());
                                Snackbar.make(view, "FILME REMOVIDO COM SUCESSO " + sucesso, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.show();
            }
        });

    }

    public void Preencher() {

        toolbar.setTitle(f.getTitle());
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
        tv_year.setText(f.getYear());
        tv_duracao.setText(f.getRuntime());
    }
}
