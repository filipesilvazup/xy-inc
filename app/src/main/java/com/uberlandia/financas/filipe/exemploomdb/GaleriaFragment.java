package com.uberlandia.financas.filipe.exemploomdb;

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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Filipe on 23/08/2018.
 */

public class GaleriaFragment extends Fragment {
    public GaleriaFragment() {
        // Required empty public constructor
    }

    private TextView listMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context context;
    private int smillingUnicode = 0x1F60A;
    private String smilling;
    private EditText filme;
    FilmeDatabase movieDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_galeria, container, false);
        movieDatabase = FilmeDatabase.getInstance(getActivity().getApplicationContext());


        smilling = new String(Character.toChars(smillingUnicode));
        getActivity().setTitle("Busque por um filme " + smilling);

        movieDatabase = FilmeDatabase.getInstance(view.getContext());
        //listMovies = (RecyclerView) view.findViewById(R.id.list_movies);
       // listMovies.setHasFixedSize(true);
        listMovies = view.findViewById(R.id.list_movies);

        filme = view.findViewById(R.id.edt_nome);
        filme.setText("Batman");

        movieDatabase = FilmeDatabase.getInstance(view.getContext());
       // listMovies = (RecyclerView) view.findViewById(R.id.list_movies);


        filme.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {


                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            List<FilmeSelecionado> f = movieDatabase.daoAccess().findFilmeByName("%" + filme.getText().toString() + "%");
                            System.out.println(f);
                            if (f.size()>0) {
                               // getActivity().setTitle("Resultados " + new String(Character.toChars(0x1F603)));

                                //adapter = new MyAdapterGaleria(f);
                                for(int i=0; i<f.size();i++){
                                    TextView a = getView().findViewById(R.id.list_movies);
                                    a.setText("\n" + " " + f.get(i) + "\n"); //listMovies.setText("\n" + " " + f.get(i) + "\n");
                                }

                            } else {
                               // getActivity().setTitle("Filme não encontrado " + new String(Character.toChars(0x1F61E)));
                                //ArrayList<Filme> arrayNaoEncontrado = new ArrayList<Filme>();
                               // arrayNaoEncontrado.add(new Filme(filme.getText().toString() + " Não encontrado", " ", "", " ", ""));
                               // adapter = new MyAdapter(arrayNaoEncontrado);
                                listMovies.setText("NAO FOI ENCONTRADO");
                            }

                        }
                    }).start();

                }

                return true;
            }
        });


        return view;
    }
}
