package com.yapp.pic6.picproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class PhotoViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);


        RelativeLayout rl = (RelativeLayout)findViewById(R.id.bigPhoto);
        Bitmap bitmap = (Bitmap)getIntent().getParcelableExtra("event_name");
        Drawable d = new BitmapDrawable(getResources(), bitmap);

        rl.setBackground(d);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
