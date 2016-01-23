package com.yapp.pic6.picproject;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


public class TutorialActivity extends BaseActivity {
    ViewPager viewPager;
    PagerAdapter adapter;
    String[] tutorialText;
    int[] tutorialImage;
    int[] indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        // Binds the Adapter to the ViewPager

        String tutorial1 = getResources().getString(R.string.tutorial_no_1);
        String tutorial2 = getResources().getString(R.string.tutorial_no_2);
        String tutorial3 = getResources().getString(R.string.tutorial_no_3);
        String tutorial4 = getResources().getString(R.string.tutorial_no_4);
        String tutorial5 = getResources().getString(R.string.tutorial_no_5);
        tutorialText = new String[]{tutorial1, tutorial2,
                tutorial3, tutorial4, tutorial5};
/*        tutorialText = new String[]{"1.사진을 찍거나 \n다운받으면 \n팝업이 나타납니다.", "2.사진을 선택하고",
                "3.앨범을 클릭하면 \n선택된 사진이 이동됩니다.", "선택된 사진을 바로 \n공유하거나 확대할 수 \n있습니다.", "사진을 길게 누르면 \n확대됩니다."};*/
        tutorialImage = new int[]{R.drawable.image_tutorial_11, R.drawable.image_tutorial_13, R.drawable.image_tutorial_15,
                R.drawable.image_tutorial_17, R.drawable.image_tutorial_19};
        indicator = new int[]{R.drawable.tutorial_status_01, R.drawable.tutorial_status_02, R.drawable.tutorial_status_03,
                R.drawable.tutorial_status_04, R.drawable.tutorial_status_05};

        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(TutorialActivity.this, tutorialText, tutorialImage, indicator);
        viewPager.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
