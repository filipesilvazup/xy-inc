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

public class MyAdapter extends RecyclerView.Adapter {
    public static ArrayList<Filme> mDataset;
    private OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemFilmeBinding binding;

        public ViewHolder(final ItemFilmeBinding itemFilmeBinding) {
            super(itemFilmeBinding.getRoot());
            this.binding = itemFilmeBinding;
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
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder vh = null;
        if (viewType == VIEW_ITEM) {

            ItemFilmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_filme, parent, false);
            vh = new MyAdapter.ViewHolder(binding);
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

        ((ViewHolder) holder).binding.setItemFilmeViewModel(new ItemFilmeViewModel());

        if (holder instanceof MyAdapter.ViewHolder) {

            ((ViewHolder) holder).binding.tvTitulo.setText(mDataset.get(position).getTitle());
            if (mDataset.get(position).getImdbID() != "" && !mDataset.get(position).getPoster().equals("N/A")) {
                Utils.setImagePicasso(mDataset.get(position).getPoster(), ((ViewHolder) holder).binding.imgFilme);
            }
            ((ViewHolder) holder).binding.tvImdbID.setText(mDataset.get(position).getImdbID());
            ((ViewHolder) holder).binding.tvYear.setText(mDataset.get(position).getYear());
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
}


