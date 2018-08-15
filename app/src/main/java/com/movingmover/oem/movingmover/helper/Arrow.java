package com.movingmover.oem.movingmover.helper;

import android.widget.ImageView;

public class Arrow {
    private String mState;
    private Coord mCoord;
    private ImageView mImageView;

    public Arrow(String state, Coord coord, ImageView imageView) {
        mState = state;
        mCoord = coord;
        mImageView = imageView;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public Coord getCoord() {
        return mCoord;
    }

    public ImageView getmImageView() {
        return mImageView;
    }
}
