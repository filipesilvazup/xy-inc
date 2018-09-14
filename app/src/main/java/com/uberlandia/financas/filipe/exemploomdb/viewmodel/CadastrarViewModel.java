package com.uberlandia.financas.filipe.exemploomdb.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.uberlandia.financas.filipe.exemploomdb.model.FilmeSelecionado;
import com.uberlandia.financas.filipe.exemploomdb.service.CallbackViewModel;
import com.uberlandia.financas.filipe.exemploomdb.service.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarViewModel {

    public ObservableBoolean fabSalvar = new ObservableBoolean();
    public ObservableBoolean fabRemover = new ObservableBoolean();
    public ObservableField<String> tv_descricao = new ObservableField<>();
    public ObservableField<String> tv_director = new ObservableField<>();
    public ObservableField<String> tv_actors = new ObservableField<>();
    public ObservableField<String> tv_rated = new ObservableField<>();
    public ObservableField<String> tv_released = new ObservableField<>();
    public ObservableField<String> tv_writer = new ObservableField<>();
    public ObservableField<String> tv_language = new ObservableField<>();
    public ObservableField<String> tv_country = new ObservableField<>();
    public ObservableField<String> imdb_rating = new ObservableField<>();
    public ObservableField<String> metascore = new ObservableField<>();
    public ObservableField<String> tv_duracao = new ObservableField<>();
    public ObservableField<String> tv_year = new ObservableField<>();


    public void preencherView(FilmeSelecionado filmeSelecionado) {
        tv_descricao.set(filmeSelecionado.getPlot());
        tv_director.set(filmeSelecionado.getDirector());
        tv_actors.set(filmeSelecionado.getActors());
        tv_rated.set(filmeSelecionado.getRated());
        tv_released.set(filmeSelecionado.getReleased());
        tv_writer.set(filmeSelecionado.getWriter());
        tv_language.set(filmeSelecionado.getLanguage());
        tv_country.set(filmeSelecionado.getCountry());
        imdb_rating.set(filmeSelecionado.getImdbRating());
        metascore.set(filmeSelecionado.getMetascore());
        tv_year.set(filmeSelecionado.getYear());
        tv_duracao.set(filmeSelecionado.getRuntime());
    }

    public void detalhesFilme(String busca, final CallbackViewModel callbackViewModel) {
        Call<FilmeSelecionado> call = new RetrofitConfig().getFilmeService().buscarFilme(busca, "45023bb7");
        call.enqueue(new Callback<FilmeSelecionado>() {
            @Override
            public void onResponse(Call<FilmeSelecionado> call, Response<FilmeSelecionado> response) {
                callbackViewModel.callbackServiceFilme((FilmeSelecionado) response.body());
            }

            @Override
            public void onFailure(Call<FilmeSelecionado> call, Throwable t) {
                callbackViewModel.callbackServiceFilme(null);

            }
        });
    }
}


