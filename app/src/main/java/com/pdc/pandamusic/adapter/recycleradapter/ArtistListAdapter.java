package com.pdc.pandamusic.adapter.recycleradapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ItemHolder> {



    @NonNull
    @Override
    public ArtistListAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistListAdapter.ItemHolder itemHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
