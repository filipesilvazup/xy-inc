package com.uberlandia.financas.filipe.exemploomdb.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uberlandia.financas.filipe.exemploomdb.model.FilmeSelecionado;
import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.service.RecyclerItemClickListener;
import com.uberlandia.financas.filipe.exemploomdb.dao.FilmeDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filipe on 23/08/2018.
 */

public class GaleriaFragment extends Fragment {
    public GaleriaFragment() {
        // Required empty public constructor
    }

    private RecyclerView listMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context context;
    private FilmeDatabase movieDatabase;
    private List<FilmeSelecionado> todosFilmes;
    private RelativeLayout view_empyt_list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(), "OnDestroy", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getContext(), "OnDestroy", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        todosFilmes = movieDatabase.daoAccess().findAll();
        if(todosFilmes.isEmpty()){
            view_empyt_list.setVisibility(View.VISIBLE);
            adapter = new MyAdapterGaleria(new ArrayList<FilmeSelecionado>());
            listMovies.setAdapter(adapter);
        }else{
            view_empyt_list.setVisibility(View.GONE);
            adapter = new MyAdapterGaleria(todosFilmes);
            listMovies.setAdapter(adapter);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_galeria, container, false);
        movieDatabase = FilmeDatabase.getInstance(getActivity().getApplicationContext());
        listMovies = (RecyclerView) view.findViewById(R.id.list_filmes_cadastrados);
        view_empyt_list = view.findViewById(R.id.view_empyt_list);

        listMovies.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(view.getContext(), 3);
        listMovies.setLayoutManager(mLayoutManager);

        listMovies.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        TextView imdbId = v.findViewById(R.id.imdbID);
                        Intent intent = new Intent(view.getContext(), CadastrarFilmeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("imdbid", imdbId.getText().toString());
                        bundle.putString("fragment", "galeria");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
        );
        return view;
    }
}
