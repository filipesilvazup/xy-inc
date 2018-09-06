package com.uberlandia.financas.filipe.exemploomdb.service;

import com.uberlandia.financas.filipe.exemploomdb.model.FilmeSelecionado;
import com.uberlandia.financas.filipe.exemploomdb.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Filipe on 20/08/2018.
 */

public interface FilmeService {
    @GET("?plot=full")
    Call<Result> buscarFilmes(@Query("s") String filme, @Query("apikey") String apikey, @Query("page") String cont);

    @GET("?plot=full")
    Call<FilmeSelecionado> buscarFilme(@Query("i") String imdbId, @Query("apikey") String apikey);

}
