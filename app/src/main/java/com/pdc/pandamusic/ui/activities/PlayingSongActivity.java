package com.pdc.pandamusic.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chibde.visualizer.BarVisualizer;
import com.pdc.pandamusic.R;
import com.pdc.pandamusic.constaint.Key;

import java.io.File;

public class PlayingSongActivity extends AppCompatActivity {

    private static final String TAG = "PlayingSongActivity";

    private boolean isStart = true;
    private boolean isPlaying = false;

    private ImageView btnBack;
    private ImageView btnSearch;

    private ImageView imgSongImg;
    private TextView txtSongName;
    private TextView txtSingerName;

    private TextView txtSongDurationMinute;
    private TextView txtSongDurationSecond;

    private TextView txtCurrentDurationMinute;
    private TextView txtCurrentDurationSecond;

    private ImageView btnPlay;

    private ImageView btnNext;
    private ImageView btnPre;

    private ImageView btnShuffle;
    private ImageView btnRepeat;

    private ImageView btnVolume;
    private ImageView btnMuted;

    private SeekBar durationSeekBar;
    private SeekBar volumeSeekBar;
    private BarVisualizer visualizer;

    private AudioManager audioManager;
    private int duration;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initView();
        playVideo();
        configVisualizer();

        duration = duration();

        processSeekBar();
        seekBarListener();
        volumeControls();
    }

    private void volumeControls() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volumeSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void seekBarListener() {
        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int process;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.process = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int stopPosition = (process * duration) / seekBar.getMax();
                mediaPlayer.seekTo(stopPosition);
            }
        });
    }

    private int duration() {
        String path = getIntent().getStringExtra(Key.TXT_SONG_PATH);
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        String duration = media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return Integer.valueOf(duration);
    }

    private void processSeekBar() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isStart) {

                    int currentDuration = mediaPlayer.getCurrentPosition();
                    long currentSecond = currentDuration / 1000;
                    final long currentMinute = currentSecond / 60;
                    currentSecond = currentSecond % 60;

                    final long finalCurrentSecond = currentSecond;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            int progress = (mediaPlayer.getCurrentPosition() * 1000) / duration;

                            durationSeekBar.setProgress(progress);

                            // TODO: 13/04/2019  getCurrentPosition để xử lý curentTime

//                            Log.d(TAG, "runrunrunnnnn: " + mediaPlayer.getCurrentPosition() + " : " + mediaPlayer.getDuration());

//                            Log.d(TAG, "runrunnnnnnnn: " + second + " : " + songDuration);

                            if (finalCurrentSecond < 10) {
                                txtCurrentDurationSecond.setText("0" + finalCurrentSecond);
                            } else {
                                txtCurrentDurationSecond.setText(String.valueOf(finalCurrentSecond));
                            }

                            if (currentMinute < 10) {
                                txtCurrentDurationMinute.setText("0" + currentMinute);
                            } else {
                                txtCurrentDurationMinute.setText(String.valueOf(currentMinute));
                            }

                        }
                    });

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    private void playVideo() {
        String path = getIntent().getStringExtra(Key.TXT_SONG_PATH);

        Uri uri = Uri.fromFile(new File(path));

        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Glide.with(PlayingSongActivity.this).load(R.drawable.btn_play_brown).into(btnPlay);

                // TODO: 13/04/2019 seekBar và currentTime khi kéo lại bị lỗi

                // TODO: 13/04/2019  chuyển bài hát khác

                isStart = false;
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                com.github.florent37.viewanimator.ViewAnimator.animate(btnPlay).scale(1f, 0.9f, 1f).duration(100).start();
                int stopPosition = mediaPlayer.getCurrentPosition();
                if (isPlaying) {
                    mediaPlayer.pause();
                    Glide.with(PlayingSongActivity.this).load(R.drawable.btn_play_brown).into(btnPlay);
                } else {
                    mediaPlayer.seekTo(stopPosition);
                    mediaPlayer.start();
                    Glide.with(PlayingSongActivity.this).load(R.drawable.btn_pause_brown).into(btnPlay);
                }
            }
        });
    }

    private void initView() {
        imgSongImg = findViewById(R.id.img_song_img);
        txtSongName = findViewById(R.id.txt_song_name);
        txtSingerName = findViewById(R.id.txt_singer_name);
        txtSongDurationMinute = findViewById(R.id.txt_song_duration_minute);
        txtSongDurationSecond = findViewById(R.id.txt_song_duration_second);
        txtCurrentDurationMinute = findViewById(R.id.txt_current_duration_minute);
        txtCurrentDurationSecond = findViewById(R.id.txt_current_duration_second);

        btnPlay = findViewById(R.id.btn_play_pause);
        btnNext = findViewById(R.id.btn_next);
        btnPre = findViewById(R.id.btn_pre);
        btnShuffle = findViewById(R.id.btn_shuffle);
        btnVolume = findViewById(R.id.img_volume);
        btnBack = findViewById(R.id.btn_back);
        btnSearch = findViewById(R.id.btn_search);
        btnRepeat = findViewById(R.id.btn_repeat);
        btnMuted = findViewById(R.id.btn_muted);

        durationSeekBar = findViewById(R.id.seek_bar);
        volumeSeekBar = findViewById(R.id.seek_volume);
        visualizer = findViewById(R.id.visualizer);

        Glide.with(this).load(R.drawable.img_mgk).into(imgSongImg);
        Glide.with(this).load(R.drawable.img_volume).into(btnVolume);

        Glide.with(this).load(R.drawable.btn_pause_brown).into(btnPlay);
        Glide.with(this).load(R.drawable.btn_next).into(btnNext);
        Glide.with(this).load(R.drawable.btn_pre).into(btnPre);
        Glide.with(this).load(R.drawable.btn_shuffle).into(btnShuffle);
        Glide.with(this).load(R.drawable.img_muted).into(btnMuted);
        Glide.with(this).load(R.drawable.ic_back).into(btnBack);
        Glide.with(this).load(R.drawable.ic_search).into(btnSearch);
        Glide.with(this).load(R.drawable.btn_repeat).into(btnRepeat);

        btnNext.setColorFilter(Color.parseColor("#754123"));
        btnPre.setColorFilter(Color.parseColor("#754123"));
        btnShuffle.setColorFilter(Color.parseColor("#754123"));
        btnVolume.setColorFilter(Color.parseColor("#754123"));
        btnBack.setColorFilter(Color.parseColor("#754123"));
        btnSearch.setColorFilter(Color.parseColor("#754123"));
        btnRepeat.setColorFilter(Color.parseColor("#754123"));
        btnMuted.setColorFilter(Color.parseColor("#754123"));

        txtSongName.setText(getIntent().getStringExtra(Key.TXT_SONG_NAME));
        txtSingerName.setText(getIntent().getStringExtra(Key.TXT_SINGER_NAME));

        convertDuration();
//        currentDuration();
    }

    private void configVisualizer() {
        visualizer.setColor(Color.parseColor("#754123"));
        visualizer.setDensity(110);
        visualizer.setPlayer(mediaPlayer.getAudioSessionId());
        Log.d(TAG, "configVisualizer: " + mediaPlayer.getAudioSessionId());
    }

    @SuppressLint("SetTextI18n")
    private void convertDuration() {
        int songDuration = getIntent().getIntExtra(Key.TXT_SONG_DURATION, 0);
        long second = songDuration / 1000;
        long minute = second / 60;
        second = second % 60;

        if (second < 10) {
            txtSongDurationSecond.setText("0" + second);
        } else {
            txtSongDurationSecond.setText(String.valueOf(second));
        }

        if (minute < 10) {
            txtSongDurationMinute.setText("0" + minute);
        } else {
            txtSongDurationMinute.setText(String.valueOf(minute));
        }
    }

}
