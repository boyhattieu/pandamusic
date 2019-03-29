package com.pdc.pandamusic.entity;

public class SongItem {
    private String txtSongName;
    private String txtSingerName;

    public SongItem(String txtSongName, String txtSingerName) {
        this.txtSongName = txtSongName;
        this.txtSingerName = txtSingerName;
    }

    public String getTxtSongName() {
        return txtSongName;
    }

    public String getTxtSingerName() {
        return txtSingerName;
    }

}
