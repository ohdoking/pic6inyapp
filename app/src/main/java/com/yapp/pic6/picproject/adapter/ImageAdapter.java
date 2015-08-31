package com.yapp.pic6.picproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.yapp.pic6.picproject.service.GalleryHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-08-29.
 */
public class ImageAdapter extends PagerAdapter {
    Context context;
    GalleryHelper gh;
    private ArrayList<String> galImages;
    public ImageAdapter(Context context){
        this.context=context;
        galImages = new ArrayList<String>();
        gh = new GalleryHelper(context);

        galImages = gh.newPicture();


    }
    @Override
    public int getCount() {
        return galImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        //int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        //imageView.setPadding(padding, padding, padding, padding);
        imageView.setMaxHeight(200);
        imageView.setMaxWidth(200);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Bitmap bitmapImage = BitmapFactory.decodeFile(galImages.get(position));
        imageView.setImageBitmap(bitmapImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
               ;
            }
        });

        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

}