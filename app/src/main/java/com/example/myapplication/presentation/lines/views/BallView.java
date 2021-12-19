package com.example.myapplication.presentation.lines.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.domain.lines.entities.BallColors;
import com.example.myapplication.domain.lines.entities.BallStates;

public class BallView extends androidx.appcompat.widget.AppCompatImageView {

    public  BallView(Context context) {
        super(context);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ObjectAnimator bouncing = null;
    private BallStates state = null;

    public void moveToNewPosition(@NonNull Point[] pathCheckpoints) {
        stopBouncing();
        float startX = this.getX();
        float startY = this.getY();

        Path path = new Path();
        path.moveTo(startX, startY);

        for (Point point : pathCheckpoints) {
            path.lineTo(point.x, point.y);
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(
                this,
                View.X,
                View.Y,
                path
        );
        animator.setDuration(500);
        animator.start();
    }

    public void startBouncing() {
        stopBouncing();
        bouncing = ObjectAnimator.ofFloat(this, "translationY", getY(), getY() - 10f);
        bouncing.setRepeatCount(ValueAnimator.INFINITE);
        bouncing.setDuration(500);
        bouncing.setAutoCancel(true);
        bouncing.start();
    }

    public void stopBouncing() {
        if (bouncing != null) {
            bouncing.cancel();
        }
    }

    public boolean isBouncing() {
        boolean bRunning = bouncing != null && bouncing.isStarted();
        return bRunning;
    }

    public void setColor(Context context, BallColors color) {
        GradientDrawable ballDrawable = new GradientDrawable();
        int[] colors = {
                ContextCompat.getColor(context, R.color.start_hot_color),
                ContextCompat.getColor(context, color.getColorId())
        };
        ballDrawable.setShape(GradientDrawable.OVAL);
        ballDrawable.setColors(colors);
        ballDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        ballDrawable.setGradientRadius(50f);
        ballDrawable.setGradientCenter(0.25f, 0.25f);

        setImageDrawable(ballDrawable);
    }

    public void setState(BallStates state) {
        if (this.state == BallStates.READY_TO_APPEAR && state == BallStates.ACTIVE) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 2.5f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 2.5f);
            scaleX.setDuration(500);
            scaleY.setDuration(500);

            AnimatorSet scaleDown = new AnimatorSet();
            scaleDown.play(scaleX).with(scaleY);

            scaleDown.start();
        }
        this.state = state;
    }

    public BallStates getState() {
        return state;
    }

    public static BallView getInstance(Context context, BallColors color, BallStates state) {
        BallView ballView = new BallView(context);
        ballView.setColor(context, color);
        ballView.setState(state);
        return ballView;
    }
}
