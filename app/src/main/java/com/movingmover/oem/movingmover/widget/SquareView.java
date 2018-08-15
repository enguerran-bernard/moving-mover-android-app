package com.movingmover.oem.movingmover.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.movingmover.oem.movingmover.R;

public class SquareView extends View {

    private Paint mPaintOne;
    private Paint mPaintTwo;
    private int mBorderSize;
    private static final int DEFAULT_COLOR = -1;
    private static final String TAG = SquareView.class.getSimpleName();
    private static final float DEFAULT_SIZE = (float)1/(float)15;

    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.Square);
        int colorOne = attr.getColor(R.styleable.Square_colorOne, DEFAULT_COLOR);
        int colorTwo = attr.getColor(R.styleable.Square_colorTwo, DEFAULT_COLOR);
        mPaintOne = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTwo = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderSize = (int) getResources().getDimension(R.dimen.square_border_size);

        if (colorOne == DEFAULT_COLOR) {
            mPaintOne.setColor(Color.BLUE);
        } else {
            mPaintOne.setColor(colorOne);
        }

        if (colorTwo == DEFAULT_COLOR) {
            mPaintTwo.setColor(Color.BLACK);
        } else {
            mPaintTwo.setColor(colorTwo);
        }
    }

    public void setBorderSize(int size) {
        mBorderSize = size;
        requestLayout();
        invalidate();
    }

    public void setColorOne(int color) {
        mPaintOne = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (color == DEFAULT_COLOR) {
            mPaintOne.setColor(Color.BLUE);
        } else {
            mPaintOne.setColor(color);
        }
        requestLayout();
        invalidate();
    }

    public void setColorTwo(int color) {
        mPaintTwo = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (color == DEFAULT_COLOR) {
            mPaintTwo.setColor(Color.BLACK);
        } else {
            mPaintTwo.setColor(color);
        }
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int mode;
        int size;
        int width;
        int height;

        mode = MeasureSpec.getMode(widthMeasureSpec);
        size = MeasureSpec.getSize(widthMeasureSpec);
        if(mode != MeasureSpec.EXACTLY) {
            width = (int) (screenWidth * DEFAULT_SIZE);
        } else {
            width = size;
        }

        mode = MeasureSpec.getMode(heightMeasureSpec);
        size = MeasureSpec.getSize(heightMeasureSpec);
        if(mode != MeasureSpec.EXACTLY) {
            height = (int) (screenHeight * DEFAULT_SIZE);
        } else {
            height = size;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        canvas.drawRect(0, 0, width, height, mPaintTwo);
        canvas.drawRect(mBorderSize, mBorderSize, width - mBorderSize, height - mBorderSize, mPaintOne);
    }
}
