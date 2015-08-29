package com.yapp.pic6.picproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yapp.pic6.picproject.db.DBHelper;
import com.yapp.pic6.picproject.service.GalleryHelper;


public class DialogMainActivity extends Activity {

    private static final int SELECT_IMAGE = 1;

    private DBHelper mydb;
    private GalleryHelper gh;



    public TextView dbNameCount;
    public TextView GalleryCount;
    public Button moveBtn;
    public Button galleryBtn;
    public ImageView imageView;

    public ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_main);
        /*imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });*/
        mydb = new DBHelper(this);
        gh = new GalleryHelper(this);

        dbNameCount = (TextView) findViewById(R.id.galleryCount);
        GalleryCount = (TextView) findViewById(R.id.realGalleryCount);
        moveBtn = (Button) findViewById(R.id.moveBtn);
        galleryBtn = (Button) findViewById(R.id.openGalery);
        imageView = (ImageView) findViewById(R.id.image);


        Integer size = mydb.getAllGallerys().size();
        dbNameCount.setText(String.valueOf(size));

        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(DialogMainActivity.this, AddActivity.class);
                startActivity(i);*/

                PackageManager pm = getApplicationContext().getPackageManager();

                if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "NO!!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, SELECT_IMAGE);
            }
        });

        gh.getGallery();




    }




    @Override
    protected void onResume() {
        super.onResume();
        Integer size = mydb.getAllGallerys().size();
        dbNameCount.setText(String.valueOf(size));
//        newPicture();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_IMAGE) {
            Uri selectedImage = data.getData();
            String path = gh.getPath(selectedImage);

            Bitmap bitmapImage = BitmapFactory.decodeFile(path);
            ImageView image = (ImageView) findViewById(R.id.image);
            image.setImageBitmap(bitmapImage);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dialog_main, menu);
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
