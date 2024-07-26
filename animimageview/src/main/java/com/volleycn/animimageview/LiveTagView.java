package com.volleycn.animimageview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class LiveTagView extends View {

    private Paint mColumnPaint;
    private RectF[] mColumnsRectArray;
    private ValueAnimator[] mAnimators;
    private final int COLUMN_COUNT = 4;

    public LiveTagView(Context context) {
        super(context);
        init(context, null);
    }

    public LiveTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LiveTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int mColumnColor = Color.BLACK;
    private int mColumnCount = COLUMN_COUNT;
    private int mColumnSpace = 0;
    private int mColumnWidth = 0;

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LiveTagView);
            mColumnCount = typedArray.getInteger(R.styleable.LiveTagView_ltv_column_count, mColumnCount);
            mColumnWidth = (int) typedArray.getDimension(R.styleable.LiveTagView_ltv_column_width, mColumnWidth);
            mColumnSpace = (int) typedArray.getDimension(R.styleable.LiveTagView_ltv_column_space, mColumnSpace);
            mColumnColor = typedArray.getColor(R.styleable.LiveTagView_ltv_column_color, mColumnColor);
            typedArray.recycle();
        }
        mColumnPaint = new Paint();
        mColumnPaint.setColor(mColumnColor);
        mColumnPaint.setAntiAlias(true);
        mColumnPaint.setStyle(Paint.Style.FILL);
    }

    public static final float SCALE = 1.0f;

    private float[] scaleYFloats;

    private synchronized void createAnimations() {
        if (mAnimators != null) {
            return;
        }
        mColumnsRectArray = new RectF[mColumnCount];
        mAnimators = new ValueAnimator[mColumnCount];
        scaleYFloats = new float[mColumnCount];
        for (int i = 0; i < mColumnCount; i++) {
            mAnimators[i] = ValueAnimator.ofFloat(0.8f, 0.2f, 0.8f);
            mAnimators[i].setDuration(1000);
            mAnimators[i].setRepeatCount(ValueAnimator.INFINITE);
            mAnimators[i].setRepeatMode(ValueAnimator.REVERSE);
            mAnimators[i].setStartDelay((100 + 100L * i));
            mAnimators[i].addUpdateListener(new AnimationListener(i));
            scaleYFloats[i] = SCALE;
        }
        if (enabledPlay) {
            startAnimations();
        }
    }


    private class AnimationListener implements ValueAnimator.AnimatorUpdateListener {
        private final int index;

        AnimationListener(int index) {
            this.index = index;
        }

        @Override
        public void onAnimationUpdate(@NonNull ValueAnimator animation) {
            scaleYFloats[index] = (float) animation.getAnimatedValue();
            postInvalidate();
        }
    }

    private boolean running = false;

    private void startAnimations() {
        if (mAnimators == null) {
            return;
        }
        if (running) {
            return;
        }
        running = true;
        for (ValueAnimator mAnimator : mAnimators) {
            mAnimator.start();
        }
    }

    private void stopAnimations() {
        if (mAnimators == null) {
            return;
        }
        if (!running) {
            return;
        }
        running = false;
        for (ValueAnimator mAnimator : mAnimators) {
            mAnimator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        createAnimations();
        draw(canvas, mColumnPaint);
    }

    public synchronized void draw(Canvas canvas, Paint paint) {
        int w = getMeasuredWidth();
        if (mColumnWidth == 0) {
            mColumnWidth = (int) (w / (mColumnCount * 2f + 1));
        }
        if (mColumnSpace == 0) {
            mColumnSpace = mColumnWidth / 2;
        }
        float translateX = (w - (mColumnCount * mColumnWidth + (mColumnCount - 1) * mColumnSpace)) / 2f;
        float translateY = getHeight() / 2f;
        for (int i = 0; i < mColumnCount; i++) {
            canvas.save();
            canvas.translate(translateX + i * (mColumnWidth + mColumnSpace) + mColumnWidth / 2f, translateY);
            canvas.scale(SCALE, scaleYFloats[i]);
            RectF rectF = mColumnsRectArray[i];
            if (rectF == null) {
                rectF = mColumnsRectArray[i] = new RectF();
                rectF.left = -mColumnWidth / 2f;
                rectF.right = mColumnWidth / 2f;
            }
            rectF.top = -getHeight() / 2.5f;
            rectF.bottom = getHeight() / 2.5f;
            canvas.drawRoundRect(rectF, 5, 5, paint);
            canvas.restore();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimations();
    }

    private boolean enabledPlay = false;

    public void setEnabledPlay(boolean online) {
        this.enabledPlay = online;
        if (online) {
            startAnimations();
        } else {
            stopAnimations();
        }
    }
}
