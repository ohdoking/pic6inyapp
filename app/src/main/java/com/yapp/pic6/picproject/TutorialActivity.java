package com.yapp.pic6.picproject;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.yapp.pic6.picproject.BaseFragmentActivity;
import com.yapp.pic6.picproject.R;
import com.yapp.pic6.picproject.adapter.MyViewPagerAdapter;


public class TutorialActivity extends BaseFragmentActivity {
    ViewPager viewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuto_item);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        MyViewPagerAdapter adapter  = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }

}
