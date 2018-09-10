package com.uberlandia.financas.filipe.exemploomdb.viewmodel;

import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BuscaViewModel {

    public ObservableField<EditText> filme;
    public  ObservableField<RecyclerView> listMovies;
    public ObservableField<RelativeLayout> progressSpinner;
    public  ObservableField<RelativeLayout> viewEmpytList;
    public ObservableField<TextView> imdbID;


}
