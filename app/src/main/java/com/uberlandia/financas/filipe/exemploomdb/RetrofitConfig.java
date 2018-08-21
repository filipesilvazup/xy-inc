package com.uberlandia.financas.filipe.exemploomdb;

import com.google.gson.Gson;
import com.uberlandia.financas.filipe.exemploomdb.service.FilmeService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Filipe on 20/08/2018.
 */

public class RetrofitConfig {

    private final Retrofit retrofit;


    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public FilmeService getFilmeService() {
        return this.retrofit.create(FilmeService.class);
    }
}
