package com.uberlandia.financas.filipe.exemploomdb.model;

import android.view.View;

import com.uberlandia.financas.filipe.exemploomdb.model.Filme;
import com.uberlandia.financas.filipe.exemploomdb.service.RecyclerViewClickListener;

import java.util.ArrayList;

/**
 * Created by Filipe on 21/08/2018.
 */

public class Result implements RecyclerViewClickListener {
    public ArrayList<Filme> Search;
    public String totalResults;
    public Boolean Response;

    public ArrayList<Filme> getSearch() {
        return Search;
    }

    public void setSearch(ArrayList<Filme> search) {
        Search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public Boolean getResponse() {
        return Response;
    }

    public void setResponse(Boolean response) {
        Response = response;
    }

    @Override
    public String toString() {
        return "Result{" +
                "Search=" + Search +
                ", totalResults='" + totalResults + '\'' +
                ", Response='" + Response + '\'' +
                '}';
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }
}
