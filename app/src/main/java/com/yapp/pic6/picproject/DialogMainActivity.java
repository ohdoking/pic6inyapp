package com.yapp.pic6.picproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yapp.pic6.picproject.adapter.GridPagerAdapter;
import com.yapp.pic6.picproject.adapter.ImageAdapter;



public class DialogMainActivity extends Activity {


    //DisplayMetrics mMetrics;
    private final long FINSH_INTERVAL_TIME = 3000;
    private long backPressedTime = 0;

    // public PagerIndicator mIndicator;
    // public PagerIndicator mIndicator;
    private ViewPager viewPager;
    private ViewPager gridPager;


    private ImageView settingBtn;
    private ImageView closeBtn;



    //ArrayList<Gallery> listData= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        gridPager = (ViewPager) findViewById(R.id.grid_pager);

        viewPager.setAdapter(new ImageAdapter(this)); // 위
        gridPager.setAdapter(new GridPagerAdapter(this)); // 아래

        settingBtn = (ImageView)findViewById(R.id.setting_btn);
        closeBtn = (ImageView)findViewById(R.id.x_btn);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(DialogMainActivity.this,PicSettingsActivity.class);
                startActivity(i);*/
               /* new BottomSheet.Builder(this, R.style.BottomSheet_StyleDialog)
                        .title("New")
                        .grid() // <-- important part
                        .sheet(R.menu.menu_dialog_main)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO
                            }
                        }).show();*/
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                savePreferences(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            String str = getResources().getString(R.string.close_order);
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    }

    // 값 저장하기
    private void savePreferences(Integer value){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("where", value);
        editor.commit();
    }

}
