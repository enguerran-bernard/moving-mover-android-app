package com.movingmover.oem.movingmover.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.movingmover.oem.movingmover.R;


public class ArrowView extends android.support.v7.widget.AppCompatImageView {
    private Paint mPaintOne;
    private Paint mPaintTwo;
    private Paint mPaintThree;
    private int mBorderSize;
    private ORIENTATION mOrientation = ORIENTATION.UP;

    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";

    public static enum ORIENTATION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private static final int DEFAULT_COLOR_ONE = Color.parseColor("#FFFFFF");
    private static final int DEFAULT_COLOR_TWO = Color.parseColor("#7777CC");
    private static final int DEFAULT_COLOR_THREE = Color.parseColor("#555500");
    private static final String TAG = ArrowView.class.getSimpleName();
    private static final float DEFAULT_SIZE = (float)1/(float)15;

    private static final float RECT_HEIGHT_BEGIN = (float)3/(float)8;
    private static final float RECT_HEIGHT_END = (float)5/(float)8;
    private static final float RECT_WIDTH_BEGIN = (float)1/(float)6;
    private static final float RECT_WIDTH_END = RECT_WIDTH_BEGIN + (float)4 / (float)9;

    private static final float ARROW_WIDTH_BEGIN = RECT_WIDTH_END;
    private static final float ARROW_WIDTH_END = (float)5/(float)6;
    private static final float ARROW_HEIGHT_BEGIN = (float)1/(float)4;
    private static final float ARROW_HEIGHT_END = (float)3/(float)4;

    private static final float OVAL_HEIGHT_BEGIN = (float)1/(float)3;
    private static final float OVAL_HEIGHT_END = (float)1/(float)3;
    private static final float OVAL_WIDTH_BEGIN = (float)3/(float)8;
    private static final float OVAL_WIDTH_END = (float)3/(float)8;

    public ArrowView(Context context) {
        super(context);
        init(DEFAULT_COLOR_ONE, DEFAULT_COLOR_TWO, DEFAULT_COLOR_THREE, null);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.Arrow);
        int colorOne = attr.getColor(R.styleable.Arrow_arrowColorOne, DEFAULT_COLOR_ONE);
        int colorTwo = attr.getColor(R.styleable.Arrow_arrowColorTwo, DEFAULT_COLOR_TWO);
        int colorThree = attr.getColor(R.styleable.Arrow_arrowColorThree, DEFAULT_COLOR_THREE);
        String orientation = attr.getString(R.styleable.Arrow_arrowOrientation);
        init(colorOne, colorTwo, colorThree, orientation);
    }

    private void init(int colorOne, int colorTwo, int colorThree, String orientation) {
        mBorderSize = (int) getResources().getDimension(R.dimen.arrow_default_border_size);
        mPaintOne = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTwo = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintThree = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOne.setColor(colorOne);
        mPaintTwo.setColor(colorTwo);
        mPaintThree.setColor(colorThree);

        if(UP.equals(orientation)) {
            mOrientation = ORIENTATION.UP;
        } else if(DOWN.equals(orientation)) {
            mOrientation = ORIENTATION.DOWN;
        } else if(LEFT.equals(orientation)) {
            mOrientation = ORIENTATION.LEFT;
        } else if(RIGHT.equals(orientation)) {
            mOrientation = ORIENTATION.RIGHT;
        } else {
            mOrientation = ORIENTATION.UP;
        }
    }

    public void setBorderSize(int size) {
        mBorderSize = size;
        requestLayout();
        invalidate();
    }

    public void setColorOne(int color) {
        mPaintOne = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintOne.setColor(color);
        requestLayout();
        invalidate();
    }

    public void setColorTwo(int color) {
        mPaintTwo = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTwo.setColor(color);
        requestLayout();
        invalidate();
    }

    public void setColorThree(int color) {
        mPaintThree = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintThree.setColor(color);
        requestLayout();
        invalidate();
    }

    public void setOrientation(ORIENTATION orientation) {
        mOrientation = orientation;
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

    private void drawArrow(Canvas canvas, float width, float height, Paint paint) {
        float paddingSizeX = getWidth() - width;
        float paddingSizeY = getHeight() - height;

        canvas.drawRect(paddingSizeX / 2 + width * RECT_WIDTH_BEGIN, paddingSizeY / 2 + height * RECT_HEIGHT_BEGIN,
                paddingSizeX / 2 + width * RECT_WIDTH_END, paddingSizeY / 2 + height * RECT_HEIGHT_END, paint);
        Path path = new Path();
        path.moveTo(paddingSizeX / 2 + width * ARROW_WIDTH_BEGIN, paddingSizeY / 2 + height * ARROW_HEIGHT_BEGIN);
        path.lineTo(paddingSizeX / 2 + width * ARROW_WIDTH_BEGIN, paddingSizeY / 2 + height * ARROW_HEIGHT_END);
        path.lineTo(paddingSizeX / 2 + width * ARROW_WIDTH_END, paddingSizeY / 2 + height / 2);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight() * 2 / 3;
        int width = getWidth() * 2 / 3;
        canvas.drawOval(getWidth() / 2 - width * OVAL_WIDTH_BEGIN, getHeight() / 2 - height * OVAL_HEIGHT_BEGIN,
                getWidth() / 2 + width * OVAL_WIDTH_END, getHeight() / 2 + height * OVAL_HEIGHT_END, mPaintOne);
        drawArrow(canvas, width, height, mPaintThree);
        drawArrow(canvas, width - mBorderSize, height - mBorderSize, mPaintTwo);
        switch (mOrientation) {
            case DOWN:
                setRotation(90);
                break;
            case LEFT:
                setRotation(180);
                break;
            case UP:
                setRotation(270);
                break;
            case RIGHT:
            default:
                //nothing to do
        }

    }
}
