package com.uberlandia.financas.filipe.exemploomdb.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.databinding.FragmentBuscaBinding;
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

public class BuscaFragment extends Fragment {

    public BuscaFragment() {
    }

   /* private RecyclerView listMovies;
    private EditText filme;
    private RelativeLayout progress_spinner;
    private RelativeLayout viewEmpytList;*/

    private MyAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Result f;
    ArrayList<Filme> listFilmes;
    private int smillingUnicode = 0x1F60A;
    private String smilling;
    protected Handler handler;
    private int cont;

    BuscaViewModel buscaViewModel;
    FragmentBuscaBinding binding;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.fragment_busca, container, false);
        smilling = new String(Character.toChars(smillingUnicode));
        buscaViewModel = new BuscaViewModel();
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_busca);
        binding.setBuscaViewModel(buscaViewModel);
        binding.executePendingBindings();

        getActivity().setTitle("Busque por um filme " + smilling);


       /* listMovies = (RecyclerView) view.findViewById(R.id.list_movies);
        viewEmpytList = view.findViewById(R.id.view_empyt_list);
        progress_spinner = view.findViewById(R.id.viewProgress);
        filme = view.findViewById(R.id.edt_nome);*/

        binding.listMovies.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.listMovies.setLayoutManager(mLayoutManager);

        handler = new Handler();

        binding.listMovies.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        TextView imdbId = v.findViewById(R.id.tv_imdbID);
                        Intent intent = new Intent(getContext(), CadastrarFilmeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("imdbid", imdbId.getText().toString());
                        bundle.putString("fragment", "busca");
                        intent.putExtras(bundle);
                        getActivity().startActivityForResult(intent, 1);

                    }
                })
        );
        adapter = new MyAdapter(new ArrayList<Filme>(), binding.listMovies);
        binding.edtNome.setText("Batman");

        listFilmes = new ArrayList<Filme>();
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {

                if (binding.viewProgress.getVisibility() == View.GONE)
                    binding.viewProgress.setVisibility(View.VISIBLE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cont++;
                        Call<Result> call = new RetrofitConfig().getFilmeService().buscarFilmes(binding.edtNome.getText().toString(), "45023bb7", "" + cont);
                        call.enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                binding.viewEmpytList.setVisibility(View.GONE);
                                f = response.body();

                                if (f.getResponse()) {
                                    getActivity().setTitle("Resultados " + new String(Character.toChars(0x1F603)));
                                    binding.viewProgress.setVisibility(View.GONE);
                                    adapter.atualizaLista(f.getSearch());
                                    adapter.setLoaded();
                                }
                            }

                            @Override
                            public void onFailure(Call<Result> call, Throwable t) {
                                Snackbar.make(getView(), "FALHA NA COMUNICAÇÃO " + new String(Character.toChars(0x1F61E)), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });

                    }
                }, 2000);

            }
        });


        binding.edtNome.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cont = 1;
                    adapter.atualizaLista(null);
                    Call<Result> call = new RetrofitConfig().getFilmeService().buscarFilmes(binding.edtNome.getText().toString(), "45023bb7", "" + cont);
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            f = response.body();
                            if (f.getResponse()) {
                                binding.viewEmpytList.setVisibility(View.GONE);
                                getActivity().setTitle("Resultados " + new String(Character.toChars(0x1F603)));
                                adapter.atualizaLista(f.getSearch());
                                binding.listMovies.setAdapter(adapter);
                            } else {
                                getActivity().setTitle("Filme não encontrado " + new String(Character.toChars(0x1F61E)));
                                binding.viewEmpytList.setVisibility(View.VISIBLE);
                                binding.listMovies.setAdapter(adapter);
                                binding.viewProgress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Snackbar.make(getView(), "FALHA NA COMUNICAÇÃO " + new String(Character.toChars(0x1F61E)), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                }

                return true;
            }
        });


        return getView();
    }
}