package com.pdc.pandamusic.entity;

public class SongItem {
    private String txtSongName;
    private String txtSingerName;
    private String txtSongPath;
    private int txtSongDuration;
    private int imgSongAva;

    public SongItem(String txtSongName, String txtSingerName, String txtSongPath, int txtSongDuration, int imgSongAva) {
        this.txtSongName = txtSongName;
        this.txtSingerName = txtSingerName;
        this.txtSongPath = txtSongPath;
        this.txtSongDuration = txtSongDuration;
        this.imgSongAva = imgSongAva;
    }

    public String getTxtSongName() {
        return txtSongName;
    }

    public String getTxtSingerName() {
        return txtSingerName;
    }

    public String getTxtSongPath() {
        return txtSongPath;
    }

    public int getTxtSongDuration() {
        return txtSongDuration;
    }

    public int getImgSongAva() {
        return imgSongAva;
    }
}
