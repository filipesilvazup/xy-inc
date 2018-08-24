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

public class MyAdapterGaleria extends RecyclerView.Adapter<MyAdapterGaleria.ViewHolder> {
    public static List<FilmeSelecionado> mDataset;



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public SquareImageView imagemFilme;
        public TextView tvImdbId;

        public ViewHolder(View v) {
            super(v);
            imagemFilme = v.findViewById(R.id.img_filme);
            tvImdbId = v.findViewById(R.id.imdbID);
        }
    }

    public MyAdapterGaleria(List<FilmeSelecionado> myDataset) {
        this.mDataset = myDataset;
    }


    @Override
    public MyAdapterGaleria.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate
                (R.layout.item_filme_cadastrado, parent, false);
        MyAdapterGaleria.ViewHolder vh = new MyAdapterGaleria.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapterGaleria.ViewHolder holder, int position) {

        Picasso.get()
                .load(mDataset.get(position).getPoster())
                .resize(150, 230)
                .centerCrop()
                .into(holder.imagemFilme);

        holder.tvImdbId.setText(mDataset.get(position).getImdbID());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
