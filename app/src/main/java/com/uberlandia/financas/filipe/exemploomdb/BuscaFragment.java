package com.uberlandia.financas.filipe.exemploomdb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.uberlandia.financas.filipe.exemploomdb.dao.FilmeDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaFragment extends Fragment {

    public BuscaFragment() {
        // Required empty public constructor
    }

    private RecyclerView listMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context context;
    private int smillingUnicode = 0x1F60A;
    private String smilling;
    private EditText filme;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busca, container, false);


        smilling = new String(Character.toChars(smillingUnicode));
        getActivity().setTitle("Busque por um filme " + smilling);


        listMovies = (RecyclerView) view.findViewById(R.id.list_movies);
        listMovies.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(view.getContext());
        listMovies.setLayoutManager(mLayoutManager);
        listMovies.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        TextView titulo = v.findViewById(R.id.tv_titulo);
                        TextView imdbId = v.findViewById(R.id.tv_imdbID);
                        ImageView img = v.findViewById(R.id.img_filme);

                        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] array = stream.toByteArray();

                        Intent it = new Intent();
                        Intent intent = new Intent(getContext(), CadastrarFilmeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("imdbid", imdbId.getText().toString());
                        //bundle.putByteArray("img", stream.toByteArray());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
        );

        Button btn = view.findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            FilmeDatabase movieDatabase = FilmeDatabase.getInstance(getActivity().getApplicationContext());

            @Override
            public void onClick(final View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Snackbar.make(view, "" + movieDatabase.daoAccess().findFilmeByName("%" + filme.getText().toString() + "%"), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }).start();
            }
        });


        filme = view.findViewById(R.id.edt_nome);
        filme.setText("Batman");

        filme.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    // Perform action on key press
                    Call<Result> call = new RetrofitConfig().getFilmeService().buscarFilmes(filme.getText().toString(), "45023bb7");
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            Result f = response.body();
                            System.out.println(f.Response + "" + f.getSearch());
                            if (f.getResponse()) {
                                getActivity().setTitle("Resultados " + new String(Character.toChars(0x1F603)));
                                adapter = new MyAdapter(f.getSearch());
                                listMovies.setAdapter(adapter);
                            } else {
                                getActivity().setTitle("Filme não encontrado " + new String(Character.toChars(0x1F61E)));
                                ArrayList<Filme> arrayNaoEncontrado = new ArrayList<Filme>();
                                arrayNaoEncontrado.add(new Filme(filme.getText().toString() + " Não encontrado", " ", "", " ", ""));
                                adapter = new MyAdapter(arrayNaoEncontrado);
                                listMovies.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Log.e("FilmeService   ", "Erro ao buscar o filme:" + t.getMessage());
                        }
                    });
                }

                return true;
            }
        });


        return view;
    }
}