package com.uberlandia.financas.filipe.exemploomdb;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    public static List<Filme> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitulo;
        public ImageView imagemFilme;
        public TextView tvImdbId;

        public ViewHolder(View v) {
            super(v);
            tvTitulo = v.findViewById(R.id.tv_titulo);
            imagemFilme = v.findViewById(R.id.img_filme);
            tvImdbId = v.findViewById(R.id.tv_imdbID);
        }
    }

    public MyAdapter(List<Filme> myDataset) {
        this.mDataset = myDataset;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate
                (R.layout.item_filme, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitulo.setText(mDataset.get(position).getTitle());
        Picasso.get()
                .load(mDataset.get(position).getPoster())
                .resize(50, 50)
                .centerCrop()
                .into(holder.imagemFilme);
        holder.tvImdbId.setText(mDataset.get(position).getImdbID());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
