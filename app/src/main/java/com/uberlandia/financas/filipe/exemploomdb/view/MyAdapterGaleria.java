package com.uberlandia.financas.filipe.exemploomdb.view;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.uberlandia.financas.filipe.exemploomdb.databinding.ItemFilmeCadastradoBinding;
import com.uberlandia.financas.filipe.exemploomdb.model.FilmeSelecionado;
import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.viewmodel.ItemFilmeCadastradoViewModel;

import java.util.List;

/**
 * Created by Filipe on 23/08/2018.
 */

public class MyAdapterGaleria extends RecyclerView.Adapter<MyAdapterGaleria.ViewHolder> {
    public static List<FilmeSelecionado> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemFilmeCadastradoBinding binding;

        public ViewHolder(ItemFilmeCadastradoBinding itemFilmeCadastradoBinding) {
            super(itemFilmeCadastradoBinding.getRoot());
            this.binding = itemFilmeCadastradoBinding;
        }
    }

    public MyAdapterGaleria(List<FilmeSelecionado> myDataset) {
        this.mDataset = myDataset;
    }


    @Override
    public MyAdapterGaleria.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemFilmeCadastradoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_filme_cadastrado, parent, false);
        return new MyAdapterGaleria.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyAdapterGaleria.ViewHolder holder, int position) {

        ((ViewHolder) holder).binding.setItemFilmeCadastradoViewModel(new ItemFilmeCadastradoViewModel());


        if (!mDataset.get(position).getPoster().equals("N/A")) {
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(mDataset.get(position).getImagem(), 0, mDataset.get(position).getImagem().length);
            holder.binding.imgFilme.setImageBitmap(bitmapImage);
        }


        holder.binding.imdbID.setText(mDataset.get(position).getImdbID());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
