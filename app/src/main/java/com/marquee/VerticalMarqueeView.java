package com.marquee;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class VerticalMarqueeView extends LinearLayout implements VerticalMarqueeAdapter.OnDataChangeListener {

    private int mAnimDuration=500;
    private int mMarqueeDuration=3000;
    private VerticalMarqueeAdapter mAdapter;
    private int mPosition;
    private View mFirstView;
    private View mSecondView;
    private boolean mIsStart;
    private int mBannerHeight;

    public VerticalMarqueeView(Context context) {
        super(context);
        init(context, null);
    }

    public VerticalMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public VerticalMarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
    }

    public void setAdapter(VerticalMarqueeAdapter adapter) {
        if (adapter == null) return;
        this.mAdapter = adapter;
        mAdapter.setOnDataChangeListener(this);
        setUpAdapter();
    }

    private void setUpAdapter() {
        removeAllViews();

        mFirstView = mAdapter.getView(this);
        mAdapter.setItemView(mFirstView, 0);
        addView(mFirstView);

        if (mAdapter.getCount() > 1) {
            mSecondView = mAdapter.getView(this);
            mAdapter.setItemView(mSecondView, 1);
            addView(mSecondView);

            mPosition = 1;
            mIsStart = false;
        }
    }


    public void start() {
        if (mAdapter == null || mAdapter.getCount() <= 1 || mIsStart) return;
        mIsStart = true;
        postDelayed(mRunnable, mMarqueeDuration);
    }

    public void stop() {
        removeCallbacks(mRunnable);
        mIsStart = false;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            performSwitch();
            postDelayed(this, mMarqueeDuration);
        }
    };

    private void performSwitch() {
        if (mAdapter == null || mAdapter.getCount() == 0) return;
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFirstView, "translationY", mFirstView.getTranslationY() - mBannerHeight);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSecondView, "translationY", mSecondView.getTranslationY() - mBannerHeight);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFirstView.setTranslationY(0);
                mSecondView.setTranslationY(0);
                View removeView = getChildAt(0);
                mPosition++;
                mAdapter.setItemView(removeView, mPosition % mAdapter.getCount());
                removeView(removeView);
                addView(removeView, 1);
            }
        });
        set.setDuration(mAnimDuration);
        set.start();
    }

    private void debug(Canvas canvas) {
        if (canvas != null) {
            Paint debugPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            debugPaint.setTextSize(16);
            debugPaint.setStyle(Paint.Style.STROKE);
            debugPaint.setColor(Color.WHITE);
            canvas.drawText("marquee banner is here", 50, getHeight() * 2 / 3, debugPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode()) {
            debug(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (LayoutParams.WRAP_CONTENT == getLayoutParams().height) {
            getLayoutParams().height = mBannerHeight;
        } else {
            mBannerHeight = getHeight();
        }
        if (isInEditMode()) {
            setBackgroundColor(Color.GRAY);
            return;
        }
        if (mFirstView != null) {
            mFirstView.getLayoutParams().height = mBannerHeight;
        }
        if (mSecondView != null) {
            mSecondView.getLayoutParams().height = mBannerHeight;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    public void onDataChange() {
        setUpAdapter();
    }
}
