package com.uberlandia.financas.filipe.exemploomdb.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.uberlandia.financas.filipe.exemploomdb.service.RecyclerItemClickListener;
import com.uberlandia.financas.filipe.exemploomdb.service.RetrofitConfig;
import com.uberlandia.financas.filipe.exemploomdb.model.Filme;
import com.uberlandia.financas.filipe.exemploomdb.model.Result;
import com.uberlandia.financas.filipe.exemploomdb.service.OnLoadMoreListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaFragment extends Fragment {

    public BuscaFragment() {
    }

    private RecyclerView listMovies;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context context;
    private Result f;
    ArrayList<Filme> listFilmes;
    private int smillingUnicode = 0x1F60A;
    private String smilling;
    private EditText filme;
    private RelativeLayout viewEmpytList;
    protected Handler handler;
    private int cont;
    private RelativeLayout progress_spinner;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busca, container, false);
        smilling = new String(Character.toChars(smillingUnicode));
        getActivity().setTitle("Busque por um filme " + smilling);
        listMovies = (RecyclerView) view.findViewById(R.id.list_movies);
        listMovies.setHasFixedSize(true);
        viewEmpytList = view.findViewById(R.id.view_empyt_list);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        listMovies.setLayoutManager(mLayoutManager);
        progress_spinner = view.findViewById(R.id.viewProgress);
        handler = new Handler();
        listMovies.addOnItemTouchListener(
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
        adapter = new MyAdapter(new ArrayList<Filme>(), listMovies);
        filme = view.findViewById(R.id.edt_nome);
        filme.setText("Batman");

        listFilmes = new ArrayList<Filme>();
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {

                if (progress_spinner.getVisibility() == View.GONE)
                    progress_spinner.setVisibility(View.VISIBLE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //listFilmes.remove(listFilmes.size() - 1);
                        // adapter.notifyItemRemoved(listFilmes.size());
                        cont++;
                        Call<Result> call = new RetrofitConfig().getFilmeService().buscarFilmes(filme.getText().toString(), "45023bb7", "" + cont);
                        call.enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                viewEmpytList.setVisibility(View.GONE);
                                f = response.body();

                                if (f.getResponse()) {
                                    getActivity().setTitle("Resultados " + new String(Character.toChars(0x1F603)));
                                    progress_spinner.setVisibility(View.GONE);
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


        filme.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    cont = 1;
                    adapter.atualizaLista(null);
                    Call<Result> call = new RetrofitConfig().getFilmeService().buscarFilmes(filme.getText().toString(), "45023bb7", "" + cont);
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            f = response.body();
                            if (f.getResponse()) {
                                viewEmpytList.setVisibility(View.GONE);
                                getActivity().setTitle("Resultados " + new String(Character.toChars(0x1F603)));
                                adapter.atualizaLista(f.getSearch());
                                listMovies.setAdapter(adapter);
                            } else {
                                getActivity().setTitle("Filme não encontrado " + new String(Character.toChars(0x1F61E)));
                                viewEmpytList.setVisibility(View.VISIBLE);
                                listMovies.setAdapter(adapter);
                                progress_spinner.setVisibility(View.GONE);
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


        return view;
    }
}
