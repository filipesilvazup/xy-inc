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
import com.uberlandia.financas.filipe.exemploomdb.utils.Utils;
import com.uberlandia.financas.filipe.exemploomdb.viewmodel.ItemFilmeCadastradoViewModel;

import java.util.List;

/**
 * Created by Filipe on 23/08/2018.
 */

public class MyAdapterGaleria extends RecyclerView.Adapter<MyAdapterGaleria.ViewHolder> {
    public static List<FilmeSelecionado> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemFilmeCadastradoBinding binding;
        private final ItemFilmeCadastradoViewModel itemFilmeCadastradoViewModel;

        public ViewHolder(ItemFilmeCadastradoBinding itemFilmeCadastradoBinding, ItemFilmeCadastradoViewModel itemFilmeCadastradoViewModel) {
            super(itemFilmeCadastradoBinding.getRoot());
            this.binding = itemFilmeCadastradoBinding;
            this.itemFilmeCadastradoViewModel = itemFilmeCadastradoViewModel;
            this.binding.setItemFilmeCadastradoViewModel(this.itemFilmeCadastradoViewModel);
        }

        public void bind(FilmeSelecionado filmeSelecionado) {

            itemFilmeCadastradoViewModel.imdbID.set(filmeSelecionado.getImdbID());
            if (!filmeSelecionado.getPoster().equals("N/A")) {
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(filmeSelecionado.getImagem(), 0, filmeSelecionado.getImagem().length);
                binding.imgFilme.setImageBitmap(bitmapImage);
            } else
                binding.imgFilme.setImageResource(R.drawable.ic_wallpaper);

        }
    }

    public MyAdapterGaleria(List<FilmeSelecionado> myDataset) {
        this.mDataset = myDataset;
    }

    @Override
    public MyAdapterGaleria.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemFilmeCadastradoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_filme_cadastrado, parent, false);
        return new MyAdapterGaleria.ViewHolder(binding, new ItemFilmeCadastradoViewModel());
    }

    @Override
    public void onBindViewHolder(MyAdapterGaleria.ViewHolder holder, int position) {

        FilmeSelecionado filmeSelecionado = mDataset.get(position);
        holder.bind(filmeSelecionado);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
