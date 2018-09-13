package com.uberlandia.financas.filipe.exemploomdb.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.uberlandia.financas.filipe.exemploomdb.model.FilmeSelecionado;

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

    
    public void preencherView(FilmeSelecionado f){
        tv_descricao.set(f.getPlot());
        tv_director.set(f.getDirector());
        tv_actors.set(f.getActors());
        tv_rated.set(f.getRated());
        tv_released.set(f.getReleased());
        tv_writer.set(f.getWriter());
        tv_language.set(f.getLanguage());
        tv_country.set(f.getCountry());
        imdb_rating.set(f.getImdbRating());
        metascore.set(f.getMetascore());
        tv_year.set(f.getYear());
        tv_duracao.set(f.getRuntime());
    }
}
