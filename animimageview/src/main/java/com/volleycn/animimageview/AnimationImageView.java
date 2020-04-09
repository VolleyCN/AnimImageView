package com.volleycn.animimageview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

/**
 * @Describe
 * @Date : 2020/4/9
 * @Email : zhangmeng@newstylegroup.com
 * @Author : MENG
 */
public class AnimationImageView extends FrameLayout {
    private boolean isPlay = false;
    private int bounderWidth = 0;
    private ObjectAnimator mImageViewScaleAnimatorX, mImageViewScaleAnimatorY,
            mCircleScaleAnimatorX, mCircleScaleAnimatorY, mCircleAlphaAnimator;
    private boolean running;
    private int h;
    private int w;
    private int anim_circle_src;
    private int circle_src;
    private int image_src;
    private float scaleOffset = 0.1f;

    public AnimationImageView(Context context) {
        super(context);
        init(context, null);
    }

    public AnimationImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public AnimationImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimationImageView);
            anim_circle_src = typedArray.getResourceId(R.styleable.AnimationImageView_aiv_anim_circle_src, R.drawable.shape_bg_ded9fb_circle);
            circle_src = typedArray.getResourceId(R.styleable.AnimationImageView_aiv_circle_src, R.drawable.shape_bg_bdb3f8_circle);
            image_src = typedArray.getResourceId(R.styleable.AnimationImageView_aiv_image_src, R.drawable.avatar_default);
            scaleOffset = typedArray.getFloat(R.styleable.AnimationImageView_aiv_scale_offset, scaleOffset);
            typedArray.recycle();
        }
        initView();
    }

    private ImageView imageView;
    private ImageView circle;
    private ImageView animatorCircle;

    public ImageView getImageView() {
        return imageView;
    }

    public ImageView getCircleView() {
        return circle;
    }

    public ImageView getAnimatorCircleView() {
        return animatorCircle;
    }

    private void initView() {
        try {
            ViewParent parent = getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.setClipChildren(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (imageView == null) {
                addView(imageView = new ImageView(getContext()));
                imageView.setImageResource(image_src);
            }
            if (enablePlay) {
                if (circle == null) {
                    addView(circle = new ImageView(getContext()));
                    circle.setImageResource(circle_src);
                }
                if (animatorCircle == null) {
                    addView(animatorCircle = new ImageView(getContext()));
                    animatorCircle.setImageResource(anim_circle_src);
                }
            }
            measureView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LayoutParams imageViewParams, circleParams, animatorCircleParams;

    private void measureView() {
        try {
            if (!enablePlay) {
                bounderWidth = 0;
            } else {
                bounderWidth = (int) (h * scaleOffset);
            }
            if (imageView != null) {
                if (imageViewParams == null) {
                    imageViewParams = new LayoutParams(w - bounderWidth, h - bounderWidth);
                    imageViewParams.gravity = Gravity.CENTER;
                } else {
                    imageViewParams.width = h - bounderWidth;
                    imageViewParams.height = w - bounderWidth;
                }
                imageView.setLayoutParams(imageViewParams);
            }
            if (circle != null) {
                if (enablePlay) {
                    if (circleParams == null) {
                        circleParams = new LayoutParams(w - bounderWidth, h - bounderWidth);
                        circleParams.gravity = Gravity.CENTER;
                    } else {
                        circleParams.width = h - bounderWidth;
                        circleParams.height = w - bounderWidth;
                    }
                    circle.setLayoutParams(circleParams);
                } else {
                    removeView(circle);
                    circle = null;
                }

            }
            if (animatorCircle != null) {
                if (enablePlay) {
                    if (animatorCircleParams == null) {
                        animatorCircleParams = new LayoutParams(w - bounderWidth, h - bounderWidth);
                        animatorCircleParams.gravity = Gravity.CENTER;
                    } else {
                        animatorCircleParams.width = h - bounderWidth;
                        animatorCircleParams.height = w - bounderWidth;
                    }
                    animatorCircle.setLayoutParams(animatorCircleParams);
                } else {
                    removeView(animatorCircle);
                    animatorCircle = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            h = getMeasuredHeight();
            w = getMeasuredWidth();
            measureView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        try {
            if (isPlay || running) {
                running = false;
                isPlay = false;
            }
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            if (imageView != null) {
                imageView.clearAnimation();
            }
            if (circle != null) {
                circle.clearAnimation();
            }
            if (animatorCircle != null) {
                animatorCircle.clearAnimation();
            }
            animatorSet = null;
            measureView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AnimatorSet animatorSet;
    private boolean enablePlay;

    public void setEnablePlay(boolean enablePlay) {
        this.enablePlay = enablePlay;
        if (enablePlay) {
            startPlay();
        } else {
            stopPlay();
        }
    }

    public void startPlay() {
        try {
            if (!isEnablePlay()) {
                stopPlay();
                return;
            }
            isPlay = true;
            initView();
            if (running) {
                return;
            }
            checkAnimatorSet();
            animatorSet.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAnimatorSet() {
        if (animatorSet == null) {
            animatorSet = new AnimatorSet();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    running = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    running = false;
                    startPlay();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    running = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            ObjectAnimator imageViewAnimatorX = ImageViewScaleAnimatorX(imageView);
            ObjectAnimator imageViewAnimatorY = ImageViewScaleAnimatorY(imageView);
            ObjectAnimator circleAnimatorX = circleScaleAnimatorX(animatorCircle);
            ObjectAnimator circleAnimatorY = circleScaleAnimatorY(animatorCircle);
            ObjectAnimator circleAlphaAnimator = circleAlphaAnimator(animatorCircle);
            animatorSet.playTogether(imageViewAnimatorX, imageViewAnimatorY, circleAnimatorX, circleAnimatorY, circleAlphaAnimator);
            animatorSet.setDuration(isPlay ? 1000 : 0);
        }
    }

    private ObjectAnimator ImageViewScaleAnimatorX(View view) {
        if (mImageViewScaleAnimatorX == null) {
            mImageViewScaleAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.9f, 1.0f);
        }
        return mImageViewScaleAnimatorX;
    }

    private ObjectAnimator ImageViewScaleAnimatorY(View view) {
        if (mImageViewScaleAnimatorY == null) {
            mImageViewScaleAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.9f, 1.0f);
        }
        return mImageViewScaleAnimatorY;
    }

    private ObjectAnimator circleScaleAnimatorX(View view) {
        if (mCircleScaleAnimatorX == null) {
            mCircleScaleAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1f, 1.2f);
        }
        return mCircleScaleAnimatorX;
    }

    private ObjectAnimator circleScaleAnimatorY(View view) {
        if (mCircleScaleAnimatorY == null) {
            mCircleScaleAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1f, 1.2f);
        }
        return mCircleScaleAnimatorY;
    }

    private ObjectAnimator circleAlphaAnimator(View view) {
        if (mCircleAlphaAnimator == null) {
            mCircleAlphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.5f, 0.0f, 1f, 0.0f, 0.1f, 0.0f, 0.5f, 0.0f, 0.1f, 0.0f, 1f, 0.0f, 0.5f);
        }
        return mCircleAlphaAnimator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            stopPlay();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
