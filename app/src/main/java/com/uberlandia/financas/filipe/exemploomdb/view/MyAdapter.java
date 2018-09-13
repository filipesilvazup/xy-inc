package com.uberlandia.financas.filipe.exemploomdb.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.databinding.ItemFilmeBinding;
import com.uberlandia.financas.filipe.exemploomdb.model.Filme;
import com.uberlandia.financas.filipe.exemploomdb.service.OnLoadMoreListener;
import com.uberlandia.financas.filipe.exemploomdb.utils.Utils;
import com.uberlandia.financas.filipe.exemploomdb.viewmodel.ItemFilmeViewModel;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {
    public static ArrayList<Filme> mDataset;
    private OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;


    public static class MyAdapterViewHolder extends RecyclerView.ViewHolder {

        private final ItemFilmeBinding binding;
        private final ItemFilmeViewModel itemFilmeViewModel;


        public MyAdapterViewHolder(final ItemFilmeBinding itemFilmeBinding, final ItemFilmeViewModel itemFilmeViewModel) {
            super(itemFilmeBinding.getRoot());
            this.binding = itemFilmeBinding;
            this.itemFilmeViewModel = itemFilmeViewModel;
            this.binding.setItemFilmeViewModel(this.itemFilmeViewModel);
        }

        public void bind(Filme filme) {
            Utils.setImagePicasso(filme.getPoster(), this.binding.imgFilme);
            itemFilmeViewModel.tvTitulo.set(filme.getTitle());
            itemFilmeViewModel.tvYear.set(filme.getYear());
            itemFilmeViewModel.tvImdbId.set(filme.getImdbID());
        }
    }

    public MyAdapter(ArrayList<Filme> myDataset) {
        this.mDataset = myDataset;
    }

    public MyAdapter(ArrayList<Filme> filmes, RecyclerView recyclerView) {
        this.mDataset = filmes;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public MyAdapter.MyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyAdapterViewHolder vh = null;


        if (viewType == VIEW_ITEM) {

            ItemFilmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_filme, parent, false);
            vh = new MyAdapter.MyAdapterViewHolder(binding, new ItemFilmeViewModel());
        }
        return vh;
    }

    public void atualizaLista(ArrayList<Filme> filmes) {

        if (filmes == null) {
            this.mDataset.clear();
        } else {
            for (int i = 0; i < filmes.size(); i++)
                this.mDataset.add(filmes.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        Filme filme = mDataset.get(position);
        holder.bind(filme);
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}


