package com.uberlandia.financas.filipe.exemploomdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uberlandia.financas.filipe.exemploomdb.service.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filipe on 23/08/2018.
 */

public class MyAdapterGaleria extends RecyclerView.Adapter<MyAdapterGaleria.ViewHolder> {
    public static List<FilmeSelecionado> mDataset;



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagemFilme;
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

        if (!mDataset.get(position).getPoster().equals("N/A")) {
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(mDataset.get(position).getImagem(), 0, mDataset.get(position).getImagem().length);
            holder.imagemFilme.setImageBitmap(bitmapImage);
        }


        holder.tvImdbId.setText(mDataset.get(position).getImdbID());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
