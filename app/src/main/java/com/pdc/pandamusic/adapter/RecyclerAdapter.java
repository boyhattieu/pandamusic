package com.pdc.pandamusic.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pdc.pandamusic.R;
import com.pdc.pandamusic.entity.SongItem;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemHolder> {

    private static final String TAG = "RecyclerAdapter";

    private List<SongItem> songItems;

    public RecyclerAdapter(List<SongItem> songItems) {
        this.songItems = songItems;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ItemHolder itemHolder, final int i) {
        String txtSongName = songItems.get(i).getTxtSongName();
        String txtSingerName = songItems.get(i).getTxtSingerName();
        itemHolder.setData(txtSongName, txtSingerName);
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                playSong(i);
            }
        });
    }

    private void playSong(int i) {
        // TODO: 29/03/2019
    }

    @Override
    public int getItemCount() {
        return songItems.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        private TextView txtSongName;
        private TextView txtSingerName;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            txtSongName = itemView.findViewById(R.id.txt_song_name);
            txtSingerName = itemView.findViewById(R.id.txt_singer_name);
        }

        public void setData(String txtSongName, String txtSingerName){
            this.txtSongName.setText(txtSongName);
            this.txtSingerName.setText(txtSingerName);
        }
    }
}
