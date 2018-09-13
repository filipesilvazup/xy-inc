package com.uberlandia.financas.filipe.exemploomdb.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BuscaViewModel {

    public ObservableField<String> busca = new ObservableField<>("");
    public ObservableBoolean viewProgress = new ObservableBoolean();
    public ObservableBoolean emptyList = new ObservableBoolean();
    public ObservableField<String> filme = new ObservableField<>("");;
    public ObservableField<String> imdbID = new ObservableField<>("");

    

}
