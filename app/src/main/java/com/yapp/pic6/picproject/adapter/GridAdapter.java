package com.yapp.pic6.picproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapp.pic6.picproject.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2015-08-29.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mThumbTxt;
    private int[] mThumbIds;
    private String[] mThumbDir;

    public GridAdapter(Context c,String[] mThumbTxt,int[] mThumbIds, String[] mThumbDir ) {
        mContext = c;
        this.mThumbTxt = mThumbTxt;
        this.mThumbIds = mThumbIds;
        this.mThumbDir = mThumbDir;
    }


    public GridAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 8;
        //return mThumbIds.length;
    }

    public Object getItem(int position) {return null;}


    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.custom, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_item_label);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_item_image);
            ImageView dirView = (ImageView) grid.findViewById(R.id.grid_item_dir);

            textView.setText(mThumbTxt[position]);
            imageView.setImageResource(mThumbIds[position]);


            if(mThumbDir[position].equals("0")){

                dirView.setImageResource(R.drawable.del_dir);
            } else if (mThumbDir[position].equals("1")) {

                dirView.setImageResource(R.drawable.plus_dir);
            } else {

//                Bitmap bitmapImage = BitmapFactory.decodeFile(mThumbDir[position]);

                dirView.setImageResource(R.drawable.folder2);
//                dirView.setImageBitmap(bitmapImage);
                // Best of quality is 80 and more, 3 is very low quality of image
//                Bitmap bJPGcompress = codec(bitmapImage, Bitmap.CompressFormat.JPEG, 3);
//
//
//                dirView.setImageBitmap(bJPGcompress);
            }


        } else {
            grid = (View) convertView;
        }

        return grid;
    }
    private static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
                                int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

}

