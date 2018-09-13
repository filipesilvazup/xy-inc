package com.uberlandia.financas.filipe.exemploomdb.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.uberlandia.financas.filipe.exemploomdb.databinding.ActivityCadastrarFilmeBinding;
import com.uberlandia.financas.filipe.exemploomdb.model.FilmeSelecionado;
import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.service.RetrofitConfig;
import com.uberlandia.financas.filipe.exemploomdb.dao.FilmeDatabase;
import com.uberlandia.financas.filipe.exemploomdb.utils.Utils;
import com.uberlandia.financas.filipe.exemploomdb.viewmodel.CadastrarViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarFilmeActivity extends AppCompatActivity {
    private FilmeDatabase movieDatabase;
    private FilmeSelecionado f;
    private String imdbId;
    private Call<FilmeSelecionado> call;
    int vergonhaUnicode = 0x1F605;
    int felizUnicode = 0x1F609;
    private String jacadastrou;
    private String sucesso;
    ActivityCadastrarFilmeBinding binding;
    CadastrarViewModel cadastrarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");

        cadastrarViewModel = new CadastrarViewModel();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cadastrar_filme);
        binding.setCadastrarViewModel(cadastrarViewModel);
        binding.executePendingBindings();

        jacadastrou = new String(Character.toChars(vergonhaUnicode));
        sucesso = new String(Character.toChars(felizUnicode));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imdbId = bundle.getString("imdbid");
        movieDatabase = Utils.getFilmeDatabaseInstance(getApplicationContext());

        f = movieDatabase.daoAccess().findFilmeById(imdbId);
        binding.toolbar12.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (f != null) {
            cadastrarViewModel.fabSalvar.set(false);
            cadastrarViewModel.fabRemover.set(true);

            if (!f.getPoster().equals("N/A")) {
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(f.getImagem(), 0, f.getImagem().length);
                binding.imgFilme.setImageBitmap(bitmapImage);
            }
            preencherView();

        } else {
            cadastrarViewModel.fabSalvar.set(true);
            cadastrarViewModel.fabRemover.set(false);

            call = new RetrofitConfig().getFilmeService().buscarFilme(imdbId, "45023bb7");
            call.enqueue(new Callback<FilmeSelecionado>() {
                @Override
                public void onResponse(Call<FilmeSelecionado> call, Response<FilmeSelecionado> response) {
                    f = response.body();
                    System.out.println(f.toString());
                    if (!f.getPoster().equals("N/A")) {
                        Utils.setImagePicasso(f.getPoster(), binding.imgFilme);
                        
                    }
                    preencherView();
                }

                @Override
                public void onFailure(Call<FilmeSelecionado> call, Throwable t) {
                    Snackbar.make(findViewById(android.R.id.content), "FALHA NA COMUNICAÇÃO " + new String(Character.toChars(0x1F61E)), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    public void Salvar(final View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarFilmeActivity.this);
        builder.setMessage("Deseja salvar o filme: " + f.getTitle() + " ?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (movieDatabase.daoAccess().findFilmeById(f.getImdbID()) == null) {
                            if (!f.getPoster().equals("N/A")) {
                                f.setImagem(Utils.convertBitmapToArrayByte(binding.imgFilme));
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

    public void Remover(final View view) {
        if (movieDatabase.daoAccess().findFilmeById(f.getImdbID()) != null) {
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
        } else {
            Snackbar.make(view, "FILME JÁ FOI REMOVIDO" + sucesso, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


    public void preencherView() {
        binding.toolbar12.setTitle(f.getTitle());
        cadastrarViewModel.preencherView(f);
    }
}
