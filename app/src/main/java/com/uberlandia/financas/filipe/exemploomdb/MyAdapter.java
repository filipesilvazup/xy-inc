package com.uberlandia.financas.filipe.exemploomdb;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uberlandia.financas.filipe.exemploomdb.service.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    public static ArrayList<Filme> mDataset;
    private OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

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
                                // End has been reached
                                // Do something
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
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = (View) LayoutInflater.from(parent.getContext()).inflate
                    (R.layout.item_filme, parent, false);
            vh = new MyAdapter.ViewHolder(v);
        } else {
            View v = (View) LayoutInflater.from(parent.getContext()).inflate
                    (R.layout.progressbar, parent, false);
            vh = new MyAdapter.ViewHolder(v);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyAdapter.ViewHolder) {

            ((MyAdapter.ViewHolder) holder).tvTitulo.setText(mDataset.get(position).getTitle());
            if (mDataset.get(position).getImdbID() != "" && !mDataset.get(position).getPoster().equals("N/A")) {
                Picasso.get()
                        .load(mDataset.get(position).getPoster())
                        .resize(200, 210)
                        .centerCrop()
                        .into(((MyAdapter.ViewHolder) holder).imagemFilme);
            }
            ((MyAdapter.ViewHolder) holder).tvImdbId.setText(mDataset.get(position).getImdbID());
            ((MyAdapter.ViewHolder) holder).tvYear.setText(mDataset.get(position).getYear());
        } else {

            ((MyAdapter.ProgressViewHolder) holder).progressBar.setIndeterminate(true);

        }
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


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(ViewHolder v) {
            super(v.itemView);
            progressBar = (ProgressBar) v.itemView.findViewById(R.id.progressBar1);
        }
    }

}


