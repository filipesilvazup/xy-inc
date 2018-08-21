package com.uberlandia.financas.filipe.exemploomdb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uberlandia.financas.filipe.exemploomdb.service.RecyclerViewClickListener;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {


    private RecyclerView listMovies;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Context context;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMovies = (RecyclerView) findViewById(R.id.list_movies);
        listMovies.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        listMovies.setLayoutManager(mLayoutManager);
        listMovies.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        TextView titulo = v.findViewById(R.id.tv_titulo);
                        TextView imdbId = v.findViewById(R.id.tv_imdbID);
                        ImageView img = v.findViewById(R.id.img_filme);

                        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] array = stream.toByteArray();

                        System.out.println(titulo.getText().toString());
                        Intent it = new Intent();
                        Intent intent = new Intent(MainActivity.this, CadastrarFilmeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("imdbid", imdbId.getText().toString());
                        bundle.putByteArray("img", stream.toByteArray());
                        intent.putExtras(bundle);
                        startActivity(intent);

/*

                        Bitmap bitmapImage = BitmapFactory.decodeByteArray(array, 0, array.length);
                        nova.setImageBitmap(bitmapImage);
                        System.out.println(stream.toByteArray());
*/


                    }
                })
        );


        final EditText filme = findViewById(R.id.edt_nome);
        filme.setText("Batman");

        Button btnBuscar = findViewById(R.id.btn_buscar_filme);
        context = this;
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<Result> call = new RetrofitConfig().getFilmeService().buscarFilme(filme.getText().toString(), "45023bb7");
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Result f = response.body();

                        adapter = new MyAdapter(f.getSearch());
                        listMovies.setAdapter(adapter);

                    }
                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e("FilmeService   ", "Erro ao buscar o filme:" + t.getMessage());
                    }
                });
            }
        });
    }



}
