package com.pdc.pandamusic.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.pdc.pandamusic.R;
import com.pdc.pandamusic.adapter.viewpager.PagerAdapter;
import com.pdc.pandamusic.adapter.viewpager.PandaViewPager;
import com.pdc.pandamusic.constaint.Key;
import com.pdc.pandamusic.layout.ItemTab;
import com.pdc.pandamusic.ui.frags.AlbumFrag;
import com.pdc.pandamusic.ui.frags.ArtistFrag;
import com.pdc.pandamusic.ui.frags.SongListFrag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<ItemTab> itemTabs;
    private List<Fragment> fragments;
    private LinearLayout lnTabMain;
    private PandaViewPager pandaViewPager;

    private String[] permissionAll = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pandaViewPager = findViewById(R.id.vpg_main);
        lnTabMain = findViewById(R.id.ln_tab);

        permissionRequest();
    }

    private void permissionRequest() {
        if (!isGranted(this, permissionAll)) {
            permission(permissionAll);
        } else {
            initTab();
            initFrag();
            initViewPager();
            registerListener();
        }
    }

    private void registerListener() {
        pandaViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                onSelectedTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initViewPager() {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pandaViewPager.setAdapter(adapter);
    }

    private void initFrag() {
        fragments = new ArrayList<>();
        fragments.add(new SongListFrag());
        fragments.add(new ArtistFrag());
        fragments.add(new AlbumFrag());
    }

    private void initTab() {
        int width = WidthScreen() / 3;
        itemTabs = new ArrayList<>();
        itemTabs.add(new ItemTab(this, R.drawable.ic_song, "Songs", width, 0));
        itemTabs.add(new ItemTab(this, R.drawable.ic_artist, "Artist", width, 1));
        itemTabs.add(new ItemTab(this, R.drawable.ic_album, "Album", width, 2));

        itemTabs.get(0).isSelected(true);
        lnTabMain.setWeightSum(itemTabs.size());
        for (int i = 0; i < itemTabs.size(); i++){
            final ItemTab itemTab = itemTabs.get(i);
            itemTab.setOnClickItem(new ItemTab.IOnClickItem() {
                @Override
                public void onClickInterface(int position) {
                    pandaViewPager.setCurrentItem(position);
                    onSelectedTab(position);
                }
            });
            lnTabMain.addView(itemTab);
        }
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

                Map<String, Integer> perms = new HashMap<>();

                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {

                    for (int i = 0; i < permissions.length; i++) {
                        perms.put(permissions[i], grantResults[i]);
                        if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                                && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

                            initTab();
                            initFrag();
                            initViewPager();
                            registerListener();

                        } else {
                            this.finish();
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    private boolean isGranted(Context context, String... permissions) {
        if (isMarshMallow() && permissions != null && context != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } else {
            return true;
        }
        return true;
    }

    private boolean isMarshMallow() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    private int WidthScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    private void onSelectedTab(int position) {
        for (int i = 0; i < itemTabs.size(); i++) {
            itemTabs.get(i).isSelected(false);
        }
        itemTabs.get(position).isSelected(true);
    }
}
