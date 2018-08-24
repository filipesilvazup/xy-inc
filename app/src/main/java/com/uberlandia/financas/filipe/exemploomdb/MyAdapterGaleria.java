package com.uberlandia.financas.filipe.exemploomdb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filipe on 23/08/2018.
 */

public class MyAdapterGaleria extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    public static List<FilmeSelecionado> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitulo;
        public ImageView imagemFilme;
        public TextView tvImdbId;
        public TextView tvYear;

        public ViewHolder(View v) {
            super(v);
            tvTitulo = v.findViewById(R.id.tv_titulo);
            imagemFilme = v.findViewById(R.id.img_filme);
            tvImdbId = v.findViewById(R.id.tv_imdbID);
            tvYear = v.findViewById(R.id.tv_year);
        }
    }

    public MyAdapterGaleria(List<FilmeSelecionado> myDataset) {
        this.mDataset = myDataset;
    }



    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate
                (R.layout.item_filme, parent, false);
        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.tvTitulo.setText(mDataset.get(position).getTitle());
        if (mDataset.get(position).getImdbID() != "") {


            Picasso.get()
                    .load(mDataset.get(position).getPoster())
                    .resize(150, 150)
                    .centerCrop()
                    .into(holder.imagemFilme);
        }
        holder.tvImdbId.setText(mDataset.get(position).getImdbID());
        holder.tvYear.setText(mDataset.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
