package com.yapp.pic6.picproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapp.pic6.picproject.R;

public class GridMenuAdapter extends BaseAdapter {
    Context mycontext = null;

    public static int[] IMAGE_IDS = { R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher

    };

    public static String[] item_name = { "Item 01", "Item 02", "Item 03",
            "Item 04", "Item 05", "Item 06" };

    public GridMenuAdapter(Context c) {
        mycontext = c;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return IMAGE_IDS.length;
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
        View itemview = convertView;
        if (convertView == null) {
            LayoutInflater sl = LayoutInflater.from(mycontext);
            itemview = sl.inflate(R.layout.gallery, null);

            TextView stv = (TextView) itemview
                    .findViewById(R.id.grid_item_label);
            stv.setText(item_name[position]);

            ImageView simg = (ImageView) itemview
                    .findViewById(R.id.grid_item_icon);
            simg.setImageResource(IMAGE_IDS[position]);
        }
//        Toast.makeText(convertView.getContext(),String.valueOf(position),Toast.LENGTH_SHORT);


        return itemview;
    }

}