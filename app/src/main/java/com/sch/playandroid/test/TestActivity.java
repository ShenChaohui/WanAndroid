package com.sch.playandroid.test;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sch.playandroid.R;

/**
 * Created by Sch.
 * Date: 2020/4/27
 * description:
 */
public class TestActivity extends AppCompatActivity {
    private TextView tvNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvNum = findViewById(R.id.tvNum);
        tvNum.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = new ValueAnimator();
                valueAnimator.setIntValues(0, 100);
                valueAnimator.setDuration(5000);
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        tvNum.setText("" + valueAnimator.getAnimatedValue());
                    }
                });
                valueAnimator.start();
            }
        });

    }
}
