package com.pdc.pandamusic.entity;

public class ArtistItem {
    private int imgArtistAva;
    private String txtArtistName;
    private String txtArtistSong;

    public ArtistItem(int imgArtistAva, String txtArtistName, String txtArtistSong) {
        this.imgArtistAva = imgArtistAva;
        this.txtArtistName = txtArtistName;
        this.txtArtistSong = txtArtistSong;
    }

    public int getImgArtistAva() {
        return imgArtistAva;
    }

    public String getTxtArtistName() {
        return txtArtistName;
    }

    public String getTxtArtistSong() {
        return txtArtistSong;
    }
}
