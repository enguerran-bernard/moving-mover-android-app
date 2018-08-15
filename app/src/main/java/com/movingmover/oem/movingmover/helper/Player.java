package com.movingmover.oem.movingmover.helper;


import android.widget.ImageView;

public class Player {
    private int mId;
    private int mColor;
    private Coord mCoord;
    private ImageView mImageView;

    public Player(int id, int color, Coord coord, ImageView imageView) {
        mId = id;
        mColor = color;
        mCoord = coord;
        mImageView = imageView;
    }

    public void setCoord(Coord coord) {
        mCoord = coord;
    }

    public int getId() {
        return mId;
    }

    public int getColor() {
        return mColor;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public Coord getCoord() {
        return mCoord;
    }
}
