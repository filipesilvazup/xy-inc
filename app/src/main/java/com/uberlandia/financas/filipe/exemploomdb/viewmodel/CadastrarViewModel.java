package com.uberlandia.financas.filipe.exemploomdb.viewmodel;

import android.databinding.ObservableField;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class CadastrarViewModel {

    public ObservableField<ImageView> iv_poster;
    public ObservableField<String> tv_info;
    public ObservableField<String> tv_descricao;
    public ObservableField<String> tv_director;
    public ObservableField<String> tv_actors;
    public ObservableField<String> tv_rated;
    public ObservableField<String> tv_released;
    public ObservableField<String> tv_writer;
    public ObservableField<String> tv_language;
    public ObservableField<String> tv_country;
    public ObservableField<String> imdb_rating;
    public ObservableField<String> metascore;
    public ObservableField<String> tv_duracao;
    public ObservableField<String> tv_year;
    public ObservableField<FloatingActionButton> salvar;
    public ObservableField<FloatingActionButton> remover;
    public ObservableField<Toolbar> toolbar;

}
