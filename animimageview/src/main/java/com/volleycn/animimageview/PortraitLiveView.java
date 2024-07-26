package com.volleycn.animimageview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class PortraitLiveView extends FrameLayout {
    private boolean running;
    private int mImageH;
    private int mImageW;
    private int mAnimViewSrc;
    private int mBorderViewSrc;
    private int mImageViewSrc;
    private float mScaleOffset = 0.1f;
    private boolean mImageScale;


    public PortraitLiveView(Context context) {
        super(context);
        init(context, null);
    }

    public PortraitLiveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public PortraitLiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PortraitLiveView);
            mAnimViewSrc = typedArray.getResourceId(R.styleable.PortraitLiveView_plv_anim_src, R.drawable.shape_bg_ded9fb_circle);
            mBorderViewSrc = typedArray.getResourceId(R.styleable.PortraitLiveView_plv_border_src, R.drawable.shape_bg_bdb3f8_circle);
            mImageViewSrc = typedArray.getResourceId(R.styleable.PortraitLiveView_plv_image_src, R.drawable.avatar_default);
            mScaleOffset = typedArray.getFloat(R.styleable.PortraitLiveView_plv_scale_offset, mScaleOffset);
            mImageScale = typedArray.getBoolean(R.styleable.PortraitLiveView_plv_image_scale, false);
            mImageW = (int) typedArray.getDimension(R.styleable.PortraitLiveView_plv_image_width, mImageW);
            mImageH = (int) typedArray.getDimension(R.styleable.PortraitLiveView_plv_image_height, mImageH);
            typedArray.recycle();
        }
        initView();
    }

    private ImageView mImageView;
    private View mBorderView;
    private View mAnimatorView;
    private boolean border;

    public ImageView getImageView() {
        return mImageView;
    }

    public View getBorderView() {
        return mBorderView;
    }

    public View getAnimatorView() {
        return mAnimatorView;
    }

    private void initView() {
        if (mImageView == null) {
            addView(mImageView = new ImageView(getContext()));
            mImageView.setImageResource(mImageViewSrc);
        }
        checkBorderAnimView();
    }

    private void checkBorderAnimView() {
        if (enablePlay || border) {
            if (mBorderView == null) {
                addView(mBorderView = new View(getContext()));
                mBorderView.setBackgroundResource(mBorderViewSrc);
            }
        }
        if (enablePlay) {
            if (mAnimatorView == null) {
                addView(mAnimatorView = new View(getContext()));
                mAnimatorView.setBackgroundResource(mAnimViewSrc);
            }
        } else {
            removeBorderAnimView();
        }
    }

    private void removeBorderAnimView() {
        if (mBorderView != null && !border) {
            removeView(mBorderView);
            mBorderView = null;
        }
        if (mAnimatorView != null) {
            removeView(mAnimatorView);
            mAnimatorView = null;
        }

    }

    private LayoutParams mImageViewParams, mBorderViewParams, mAnimateViewParams;
    private int preBounderW, preBounderH;

    private void measureView() {
        int bounderW = enablePlay ? (int) (mImageW * mScaleOffset) : 0;
        int bounderH = enablePlay ? (int) (mImageH * mScaleOffset) : 0;
        boolean isSame = preBounderW == bounderW && preBounderH == bounderH;
        checkBorderAnimView();
        if (mImageView != null) {
            if (mImageViewParams == null) {
                mImageViewParams = new LayoutParams(mImageW - bounderW, mImageH - bounderH);
                mImageViewParams.gravity = Gravity.CENTER;
            } else if (!isSame) {
                mImageViewParams.width = mImageW - bounderW;
                mImageViewParams.height = mImageH - bounderH;
            }
            mImageView.setLayoutParams(mImageViewParams);
        }
        if ((enablePlay || border) && mBorderView != null) {
            if (mBorderViewParams == null) {
                mBorderViewParams = new LayoutParams(mImageW - bounderW, mImageH - bounderH);
                mBorderViewParams.gravity = Gravity.CENTER;
                mBorderView.setLayoutParams(mBorderViewParams);
            }
            if (!isSame) {
                mBorderViewParams.width = mImageW - bounderW;
                mBorderViewParams.height = mImageH - bounderH;
                mBorderView.setLayoutParams(mBorderViewParams);
            }
        }
        if (enablePlay && mAnimatorView != null) {
            if (mAnimateViewParams == null) {
                mAnimateViewParams = new LayoutParams(mImageW - bounderW, mImageH - bounderH);
                mAnimateViewParams.gravity = Gravity.CENTER;
                mAnimatorView.setLayoutParams(mAnimateViewParams);
            }
            if (!isSame) {
                mAnimateViewParams.width = mImageW - bounderW;
                mAnimateViewParams.height = mImageH - bounderH;
                mAnimatorView.setLayoutParams(mAnimateViewParams);
            }
        }
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup && ((ViewGroup) parent).getClipChildren()) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.setClipChildren(false);
        }
        preBounderW = bounderW;
        preBounderH = bounderH;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mImageW == 0 || mImageH == 0) {
            mImageH = getMeasuredHeight();
            mImageW = getMeasuredWidth();
        }
        measureView();
    }

    private void stopPlay() {
        if (!running) {
            return;
        }
        if (mImageViewScaleAnimator != null) {
            mImageViewScaleAnimator.cancel();
        }
        if (mAminViewScaleAnimator != null) {
            mAminViewScaleAnimator.cancel();
        }
        if (mAminViewAlphaAnimator != null) {
            mAminViewAlphaAnimator.cancel();
        }
        running = false;
        measureView();
    }

    private boolean enablePlay;

    public void setEnablePlay(boolean enablePlay) {
        this.enablePlay = enablePlay;
        if (enablePlay) {
            startPlay();
        } else {
            stopPlay();
        }
    }

    private ValueAnimator mImageViewScaleAnimator, mAminViewScaleAnimator, mAminViewAlphaAnimator;

    private void startPlay() {
        if (!enablePlay) {
            stopPlay();
            return;
        }
        if (running) {
            return;
        }
        running = true;
        measureView();
        if (mImageViewScaleAnimator == null && mImageScale) {
            mImageViewScaleAnimator = getValueAnimator(1.0f, 0.9f, 1.0f);
            mImageViewScaleAnimator.addUpdateListener(animator -> {
                if (mImageView != null && running) {
                    float value = (float) animator.getAnimatedValue();
                    mImageView.setScaleX(value);
                    mImageView.setScaleY(value);
                }
            });
        }
        if (mAminViewScaleAnimator == null) {
            mAminViewScaleAnimator = getValueAnimator(1.2f, 1f, 1.2f);
            mAminViewScaleAnimator.addUpdateListener(animator -> {
                if (mAnimatorView != null && running) {
                    float value = (float) animator.getAnimatedValue();
                    mAnimatorView.setScaleX(value);
                    mAnimatorView.setScaleY(value);
                }
            });
        }
        if (mAminViewAlphaAnimator == null) {
            mAminViewAlphaAnimator = getValueAnimator(0.5f, 0.0f, 0.1f, 0.0f, 1f, 0.0f, 0.5f);
            mAminViewAlphaAnimator.addUpdateListener(animator -> {
                if (mAnimatorView != null && running) {
                    mAnimatorView.setAlpha((float) animator.getAnimatedValue());
                }
            });
        }
        if (mImageScale && mImageViewScaleAnimator != null) {
            mImageViewScaleAnimator.start();
        }
        mAminViewScaleAnimator.start();
        mAminViewAlphaAnimator.start();
    }


    private ValueAnimator getValueAnimator(float... values) {
        ValueAnimator animator = ValueAnimator.ofFloat(values);
        long duration = 1500;
        animator.setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        return animator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopPlay();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startPlay();
        } else if (visibility == INVISIBLE || visibility == GONE) {
            stopPlay();
        }
    }

    public boolean isEnablePlay() {
        return enablePlay;
    }

    public void setBorder(boolean defaultBorder) {
        this.border = defaultBorder;
    }
}
