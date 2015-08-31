package com.yapp.pic6.picproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapp.pic6.picproject.adapter.ExtendedGallery;
import com.yapp.pic6.picproject.adapter.GridMenuAdapter;

public class GridAcitivy extends Activity {
    GridView icon_grid;
    TextView power_text, power_imagine;
    ImageButton infobtn;
    RelativeLayout info_layout;
    LinearLayout count_layout;
    int count = 0;
    static TextView page_text[];

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_acitivy);

        icon_grid = (GridView) findViewById(R.id.iconic_grid);
        ExtendedGallery mGallery = (ExtendedGallery) findViewById(R.id.mygallery01);
        mGallery.setAdapter(new AddImageAdapter(this));
        mGallery.setScrollingEnabled(true);

        infobtn = (ImageButton) findViewById(R.id.info_icon);
        info_layout = (RelativeLayout) findViewById(R.id.info_layout);
        power_text = (TextView) findViewById(R.id.power_text);
        power_imagine = (TextView) findViewById(R.id.power_ever_text);

        power_text.setText("Always");
        power_imagine.setText(" " + "Imagine");

        icon_grid.setAdapter(new GridMenuAdapter(GridAcitivy.this));

        count_layout = (LinearLayout) findViewById(R.id.image_count);
        count = mGallery.getAdapter().getCount();
        System.out.println("Gallery Image Count======>>>" + count);

        page_text = new TextView[count];
        for (int i = 0; i < count; i++) {
            page_text[i] = new TextView(this);
            page_text[i].setText(".");
            page_text[i].setTextSize(45);
            page_text[i].setTypeface(null, Typeface.BOLD);
            page_text[i].setTextColor(android.graphics.Color.GRAY);
            count_layout.addView(page_text[i]);
        }

        mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // TODO Auto-generated method stub
                System.out.println("Item Selected Position=======>>>" + pos);
                for (int i = 0; i < count; i++) {
                    GridAcitivy.page_text[i]
                            .setTextColor(android.graphics.Color.GRAY);
                }
                GridAcitivy.page_text[pos]
                        .setTextColor(android.graphics.Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }


//This is tha adapter class for loading the images into the Gallery.

    public class AddImageAdapter extends BaseAdapter {
        Context mycontext = null;
        int galitembg = 0;

        public AddImageAdapter(Context c) {
            mycontext = c;
           /* TypedArray typArray = mycontext
                    .obtainStyledAttributes(R.styleable.);
            galitembg = typArray.getResourceId(
                    R.styleable.GalleryTheme_android_galleryItemBackground, 0);
            typArray.recycle();*/
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return GridAcitivy.IMAGE_IDS.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ImageView imageview = new ImageView(mycontext);
            imageview.setImageResource(GridAcitivy.IMAGE_IDS[position]);
            imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageview;
        }
    }



//These are the Images Going to load to the gallery from the res/drawable folder.

    public static int[] IMAGE_IDS = { R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher };

}