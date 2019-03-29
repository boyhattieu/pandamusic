package com.pdc.pandamusic.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.pdc.pandamusic.R;
import com.pdc.pandamusic.adapter.RecyclerAdapter;
import com.pdc.pandamusic.constaint.Key;
import com.pdc.pandamusic.entity.SongItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<SongItem> songItems;
    private RecyclerView rcvSongList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvSongList = findViewById(R.id.rcv_main);
        permissionRequest();
        initView();
//        onListeners();
    }

    private void onListeners() {

    }

    private void permissionRequest() {
        if (!isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            loadMusic(this);
        }
    }

    private void initView() {
        RecyclerAdapter adapter = new RecyclerAdapter(songItems);
        rcvSongList.setAdapter(adapter);
        rcvSongList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void permission(String... strings) {
        ActivityCompat.requestPermissions(
                this,
                strings,
                Key.REQUEST_CODE_PERMISSION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Key.REQUEST_CODE_PERMISSION:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED && permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        loadMusic(this);
                    }
                }
                break;

            default:
                break;
        }
    }

    private boolean isGranted(String permission) {
        if (isMarshMallow()) {
            return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private boolean isMarshMallow() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    private List<SongItem> loadMusic(final Context context) {
        songItems = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                SongItem songItem = new SongItem(cursor.getString(1), cursor.getString(3));
                songItems.add(songItem);
            }
            cursor.close();
        }

        return songItems;
    }
}
