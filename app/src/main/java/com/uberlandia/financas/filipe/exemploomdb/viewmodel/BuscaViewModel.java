package com.uberlandia.financas.filipe.exemploomdb.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.uberlandia.financas.filipe.exemploomdb.model.Result;
import com.uberlandia.financas.filipe.exemploomdb.service.CallbackViewModel;
import com.uberlandia.financas.filipe.exemploomdb.service.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaViewModel {

    public ObservableField<String> busca = new ObservableField<>("");
    public ObservableBoolean viewProgress = new ObservableBoolean();
    public ObservableBoolean emptyList = new ObservableBoolean();
    public ObservableField<String> filme = new ObservableField<>("");
    public ObservableField<String> imdbID = new ObservableField<>("");

    public void moreItemList(final int cont, final String busca, final CallbackViewModel callbackViewModel) {

        Call<Result> call = new RetrofitConfig().getFilmeService().buscarFilmes(busca, "45023bb7", "" + cont);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                callbackViewModel.callbackServiceFilme((Result)response.body());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                callbackViewModel.callbackServiceFilme(null);

            }
        });
    }
}

