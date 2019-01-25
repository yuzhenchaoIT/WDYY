package com.baidu.wdyy.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bw.movie.R;



public class MySearchLayout extends RelativeLayout {

    private ObjectAnimator translationX;

    public MySearchLayout(Context context) {
        super(context);
        init();
    }

    public MySearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        View view = View.inflate(getContext(), R.layout.one_search_layout,this);
        ImageView imageView= view.findViewById(R.id.one_image_sou);
        translationX = ObjectAnimator.ofFloat(this, "translationX", 0,-280);
        translationX.setDuration(1000);
        translationX.setInterpolator(new LinearInterpolator());
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onAnimationEnd(Animator animator) {
                translationX.pause();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                    translationX.start();
              //  Toast.makeText(getContext(), "1111", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
