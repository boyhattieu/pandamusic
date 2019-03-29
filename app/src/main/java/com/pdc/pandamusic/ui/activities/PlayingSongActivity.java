package com.pdc.pandamusic.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pdc.pandamusic.R;

public class PlayingSongActivity extends AppCompatActivity {

    private ImageView imgSongImg;
    private TextView txtSongName;
    private TextView txtSingerName;
    private TextView txtSongDuration;
    private TextView txtCurrentDuration;
    private ImageView btnPlay;
    private ImageView btnNext;
    private ImageView btnPre;
    private ImageView btnShuffle;
    private ImageView btnVolume;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        initView();
    }

    private void initView() {
        imgSongImg = findViewById(R.id.img_song_img);
        txtSongName = findViewById(R.id.txt_song_name);
        txtSingerName = findViewById(R.id.txt_singer_name);
        txtSongDuration = findViewById(R.id.txt_song_duration);
        txtCurrentDuration = findViewById(R.id.txt_current_duration);
        btnPlay = findViewById(R.id.btn_play_pause);
        btnNext = findViewById(R.id.btn_next);
        btnPre = findViewById(R.id.btn_pre);
        btnShuffle = findViewById(R.id.btn_shuffle);
        btnVolume = findViewById(R.id.img_volume);

        Glide.with(this).load(R.drawable.img_mgk).into(imgSongImg);
        Glide.with(this).load(R.drawable.btn_play).into(btnPlay);
        Glide.with(this).load(R.drawable.btn_next).into(btnNext);
        Glide.with(this).load(R.drawable.btn_pre).into(btnPre);
        Glide.with(this).load(R.drawable.btn_shuffle).into(btnShuffle);
        Glide.with(this).load(R.drawable.img_volume).into(btnVolume);

        txtSongName.setText("Let You Go");
        txtSingerName.setText("Machine Gun Kelly");
        txtSongDuration.setText("3:50");
        txtCurrentDuration.setText("0:00");
    }
}
