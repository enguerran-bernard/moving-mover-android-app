package com.movingmover.oem.movingmover.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.movingmover.oem.movingmover.R;
import com.movingmover.oem.movingmover.animation.AnimationQueueElement;
import com.movingmover.oem.movingmover.animation.BoardAnimationManager;
import com.movingmover.oem.movingmover.helper.AssociateMap;
import com.movingmover.oem.movingmover.helper.Coord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardView extends ViewGroup {

    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_COLOR = -1;
    private static final String TAG = BoardView.class.getSimpleName();
    private static float percentScreen = (float)2/(float)3;
    private int mSize;
    private int mColorOne;
    private int mColorTwo;
    private int mBorderSize;
    private ArrayList<ArrayList<SquareView>> mSquares;
    private BoardAnimationManager mBoardAnimationManager;

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
        initData();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        initData();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttributes(attrs);
        initData();
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.Board);
        mSize = attr.getInteger(R.styleable.Board_size, DEFAULT_SIZE);
        mColorOne = attr.getColor(R.styleable.Board_boardColorOne, DEFAULT_COLOR);
        mColorTwo = attr.getColor(R.styleable.Board_boardColorTwo, DEFAULT_COLOR);
        mBorderSize = (int)attr.getFloat(R.styleable.Board_borderSize, getResources().getDimension(R.dimen.square_border_size));
    }

    private void initData() {
        mSquares = new ArrayList<ArrayList<SquareView>>();
        for(int i = 0; i < mSize; i++) {
            ArrayList list = new ArrayList<SquareView>();
            for(int j = 0; j < mSize; j++) {
                SquareView square = new SquareView(getContext());
                square.setColorOne(mColorOne);
                square.setColorTwo(mColorTwo);
                square.setBorderSize(mBorderSize);
                square.setTag(new Coord(i, j));
                addView(square);
                list.add(square);
            }
            mSquares.add(list);
        }
        mBoardAnimationManager = new BoardAnimationManager(getContext(), this);
    }

    private int getMeasure(int spec, int screenSize) {
        return screenSize;
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int size = (int) (Math.min(getMeasure(widthMeasureSpec, screenWidth),
                getMeasure(heightMeasureSpec, screenHeight)) * percentScreen);
        setMeasuredDimension(size, size);

        for(int i = 0; i < mSize; i++) {
            for(int j = 0; j < mSize; j++) {
                SquareView square = mSquares.get(i).get(j);
                square.measure(MeasureSpec.makeMeasureSpec(size / mSize,
                        MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(size / mSize,
                                MeasureSpec.EXACTLY));
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int width = getMeasuredWidth() / mSize;

        for(int i = 0; i < mSize; i++) {
            for(int j = 0; j < mSize; j++) {
                SquareView square = mSquares.get(i).get(j);
                square.layout(i * width, j * width, (i + 1) * width, (j + 1) * width);
            }
        }
    }

    public void setSize(int size) {
        if(size <= 0) {
            Log.e(TAG, "bad size requested: " + size);
            return;
        }
        mSize = size;
        initData();
        requestLayout();
        invalidate();
    }

    public int getSize() {
        return mSize;
    }

    public void setImagePositions(ArrayList<ImageView> images, ArrayList<Coord> coords) {
        if(images == null || coords == null || images.size() != coords.size()) {
            Log.e(TAG, "setImagePositions bad sizes");
            return;
        }
        for(int i = 0; i < images.size(); i++) {
            ImageView image = images.get(i);
            Coord coord = coords.get(i);
            adaptImageViewSize(image);
            setSquareImageView(coord.x, coord.y, image);
        }
    }

    public void setImagePosition(ImageView image, Coord coord) {
        if(image == null || coord == null) {
            Log.e(TAG, "can't set image position");
            return;
        }
        adaptImageViewSize(image);
        setSquareImageView(coord.x, coord.y, image);
    }

    private void adaptImageViewSize(ImageView imageView) {
        int width = getWidth() / mSize;
        imageView.getLayoutParams().height = width;
        imageView.getLayoutParams().width = width;
    }

    private void setSquareImageView(int x, int y, ImageView imageView) {
        if(mSquares != null && x >= 0 && y >= 0 && x < mSquares.size() && y < mSquares.size()) {
            imageView.setX(mSquares.get(x).get(y).getX() + getX());
            imageView.setY(mSquares.get(x).get(y).getY() + getY());
            imageView.requestLayout();
        } else {
            float posX = 0;
            float posY = 0;
            if(x < 0) {
                posX = mSquares.get(0).get(0).getX() + getX() - mSquares.get(0).get(0).getWidth();
            } else if( x >= mSquares.size()) {
                posX = mSquares.get(mSize - 1).get(mSize - 1).getX() + getX() + mSquares.get(0).get(0).getWidth();
            } else {
                posX =  mSquares.get(x).get(0).getX() + getX();
            }
            if(y < 0) {
                posY = mSquares.get(0).get(0).getY() + getY() - mSquares.get(0).get(0).getWidth();
            } else if(y >= mSize) {
                posY = mSquares.get(mSize - 1).get(mSize - 1).getY() + getY() + mSquares.get(0).get(0).getWidth();
            } else {
                posY =  mSquares.get(0).get(y).getY() + getY();
            }
            imageView.setX(posX);
            imageView.setY(posY);
            imageView.requestLayout();
        }
    }

    public void putPlayer(ImageView imageView, Coord coord) {
        if(coord.x >= 0 && coord.y >= 0 && coord.x < mSize && coord.y < mSize) {
            mBoardAnimationManager.putPlayer(imageView, coord);
        }
    }

    public void removePlayer(ImageView imageView) {
        mBoardAnimationManager.removePlayer(imageView);
    }

    public void movePlayer(final ImageView imageView, Coord coordInit, Coord coordDest, boolean isChoosed, int duration) {
        mBoardAnimationManager.movePlayer(imageView, coordInit, coordDest, isChoosed, duration);
    }

    public void putArrow(ArrowView arrowView, Coord coordInit, Coord coordDest) {

        if(coordInit.x >= 0 && coordInit.y >= 0 && coordInit.x < mSize && coordInit.y < mSize
                || coordDest.x >= 0 && coordDest.y >= 0 && coordDest.x < mSize && coordDest.y < mSize) {
            mBoardAnimationManager.putArrow(arrowView, coordInit, coordDest);
        }
    }

    public void hitArrow(String state, ImageView imageView) {
        if(imageView != null && imageView instanceof ArrowView) {
            mBoardAnimationManager.hitArrow((ArrowView) imageView, state);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if(mBoardAnimationManager != null) {
            mBoardAnimationManager.stopAnimations();
        }
        super.onDetachedFromWindow();
    }
}
