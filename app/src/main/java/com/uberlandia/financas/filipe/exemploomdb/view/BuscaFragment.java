package com.uberlandia.financas.filipe.exemploomdb.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.databinding.FragmentBuscaBinding;
import com.uberlandia.financas.filipe.exemploomdb.model.FilmeSelecionado;
import com.uberlandia.financas.filipe.exemploomdb.service.CallbackViewModel;
import com.uberlandia.financas.filipe.exemploomdb.service.RecyclerItemClickListener;
import com.uberlandia.financas.filipe.exemploomdb.service.RetrofitConfig;
import com.uberlandia.financas.filipe.exemploomdb.model.Filme;
import com.uberlandia.financas.filipe.exemploomdb.model.Result;
import com.uberlandia.financas.filipe.exemploomdb.service.OnLoadMoreListener;
import com.uberlandia.financas.filipe.exemploomdb.viewmodel.BuscaViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaFragment extends Fragment implements CallbackViewModel {

    public BuscaFragment() {
    }

    private MyAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Result f;
    ArrayList<Filme> listFilmes;
    private int smillingUnicode = 0x1F60A;
    private String smilling;
    protected Handler handler;
    private int cont;
    private String busca;
    private BuscaViewModel buscaViewModel;
    private FragmentBuscaBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        smilling = new String(Character.toChars(smillingUnicode));
        buscaViewModel = new BuscaViewModel();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_busca, container, false);
        binding.setBuscaViewModel(buscaViewModel);
        binding.executePendingBindings();

        getActivity().setTitle("Busque por um filme " + smilling);
        busca = "";

        binding.listMovies.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.listMovies.setLayoutManager(mLayoutManager);

        handler = new Handler();
        binding.listMovies.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                       goDetalhesFilmes(position);
                    }
                })
        );
        adapter = new MyAdapter(new ArrayList<Filme>(), binding.listMovies);
        buscaViewModel.busca.set("Batman");

        listFilmes = new ArrayList<Filme>();
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                carregarFilmes();
            }
        });


        binding.edtNome.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    novaBuscaFilme();
                }
                return true;
            }
        });

        return binding.getRoot();
    }

    public void novaBuscaFilme(){
        cont = 0;
        busca = buscaViewModel.busca.get();
        adapter.atualizaLista(null);
        binding.listMovies.setAdapter(adapter);
        carregarFilmes();
    }

    public void carregarFilmes() {
        cont++;
        buscaViewModel.moreItemList(cont, busca, this);
    }


    @Override
    public void callbackServiceFilme(final Object object) {
        buscaViewModel.viewProgress.set(true);
        final Result result = (Result) object;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (result == null) {
                    Snackbar.make(getView(), "FALHA NA COMUNICAÇÃO " + new String(Character.toChars(0x1F61E)), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (result.getResponse()) {
                    getActivity().setTitle("Resultados " + new String(Character.toChars(0x1F603)));
                    if (buscaViewModel.emptyList.get()) {
                        buscaViewModel.emptyList.set(false);
                    }
                    buscaViewModel.viewProgress.set(false);
                    adapter.atualizaLista(result.getSearch());
                    adapter.setLoaded();
                } else if (cont == 1 && !result.getResponse()) {
                    getActivity().setTitle("Filme não encontrado " + new String(Character.toChars(0x1F61E)));
                    buscaViewModel.emptyList.set(true);
                    if (buscaViewModel.viewProgress.get()) {
                        buscaViewModel.viewProgress.set(false);
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "ÚLTIMOS FILMES DA PESQUISA" + new String(Character.toChars(0x1F61E)), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    buscaViewModel.viewProgress.set(false);
                }
            }
        }, 1000);

    }


    public void goDetalhesFilmes(int position){
        Intent intent = new Intent(getContext(), CadastrarFilmeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imdbid", MyAdapter.mDataset.get(position).getImdbID());
        intent.putExtras(bundle);
        getActivity().startActivityForResult(intent, 1);

    }

}

