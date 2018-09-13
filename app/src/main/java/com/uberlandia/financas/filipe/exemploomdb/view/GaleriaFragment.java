package com.uberlandia.financas.filipe.exemploomdb.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uberlandia.financas.filipe.exemploomdb.databinding.FragmentGaleriaBinding;
import com.uberlandia.financas.filipe.exemploomdb.model.FilmeSelecionado;
import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.service.RecyclerItemClickListener;
import com.uberlandia.financas.filipe.exemploomdb.dao.FilmeDatabase;
import com.uberlandia.financas.filipe.exemploomdb.utils.Utils;
import com.uberlandia.financas.filipe.exemploomdb.viewmodel.GaleriaViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filipe on 23/08/2018.
 */

public class GaleriaFragment extends Fragment {
    public GaleriaFragment() {
    }


    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FilmeDatabase movieDatabase;
    private List<FilmeSelecionado> todosFilmes;
    FragmentGaleriaBinding binding;
    GaleriaViewModel galeriaViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        todosFilmes = movieDatabase.daoAccess().findAll();
        if (todosFilmes.isEmpty()) {
            galeriaViewModel.emptyListCadastrados.set(true);
            adapter = new MyAdapterGaleria(new ArrayList<FilmeSelecionado>());
            binding.listFilmesCadastrados.setAdapter(adapter);
        } else {
            galeriaViewModel.emptyListCadastrados.set(false);
            adapter = new MyAdapterGaleria(todosFilmes);
            binding.listFilmesCadastrados.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        galeriaViewModel = new GaleriaViewModel();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_galeria, container, false);
        binding.setGaleriaViewModel(galeriaViewModel);
        binding.executePendingBindings();

        movieDatabase = Utils.getFilmeDatabaseInstance(getActivity().getApplicationContext());

        binding.listFilmesCadastrados.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        binding.listFilmesCadastrados.setLayoutManager(mLayoutManager);

        binding.listFilmesCadastrados.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(getActivity(), CadastrarFilmeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("imdbid", MyAdapterGaleria.mDataset.get(position).getImdbID());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
        );
        return binding.getRoot();
    }
}
