package com.baidu.wdyy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossoverone.statuslib.StatusUtil;

public class LeadActivity extends AppCompatActivity {
    private SharedPreferences sp;
    @BindView(R.id.lead_vp)
    ViewPager leadVp;
    @BindView(R.id.lead_radiogroup)
    LinearLayout leadRadiogroup;
    @BindView(R.id.lead_button)
    Button leadButton;
    @OnClick(R.id.lead_button)
    public void onViewClicked() {
        startActivity(new Intent(LeadActivity.this, HomeActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        ButterKnife.bind(this);
        final List<View> list = new ArrayList<>();
        View v1 = View.inflate(this, R.layout.item_lead_one, null);
        View v2 = View.inflate(this, R.layout.item_lead_two, null);
        View v3 = View.inflate(this, R.layout.item_lead_three, null);
        View v4 = View.inflate(this, R.layout.item_lead_four, null);
        list.add(v1);
        list.add(v2);
        list.add(v3);
        list.add(v4);

        leadVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        getCurrentView(list);

        leadVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == list.size() - 1) {
                    leadButton.setVisibility(View.VISIBLE);
                } else {
                    leadButton.setVisibility(View.GONE);
                }
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        leadRadiogroup.getChildAt(i).setBackgroundResource(R.drawable.lead_radio_select);
                    } else {
                        leadRadiogroup.getChildAt(i).setBackgroundResource(R.drawable.lead_radio_default);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setStatusColor();
        setSystemInvadeBlack();
        sp = getSharedPreferences("WelCome", MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst", true);
        if(isFirst==true){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }else{
            Intent intent = new Intent(LeadActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    protected void setStatusColor() {
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#00000000"));
    }

    protected void setSystemInvadeBlack() {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(this, true, true);
    }
    private void getCurrentView(List<View> list) {
        for (int i = 0; i < list.size(); i++) {
            View view = new View(LeadActivity.this);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.lead_radio_select);
            } else {
                view.setBackgroundResource(R.drawable.lead_radio_default);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(34, 34);
            params.setMargins(12, 10, 12, 10);
            view.setLayoutParams(params);
            leadRadiogroup.addView(view);
        }
    }
}
