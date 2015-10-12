package com.yapp.pic6.picproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yapp.pic6.picproject.db.DBHelper;
import com.yapp.pic6.picproject.service.FifthService;
import com.yapp.pic6.picproject.service.GalleryHelper;


public class DialogTestActivity extends Activity {

    private static final int SELECT_IMAGE = 1;

    private DBHelper mydb;
    private GalleryHelper gh;



    public TextView dbNameCount;
    public TextView GalleryCount;
    public Button moveBtn;
    public Button galleryBtn;
    public Button addPopup;
    public Button startService;
    public ImageView imageView;

    public ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);

        mydb = new DBHelper(this);
        gh = new GalleryHelper(this);

        dbNameCount = (TextView) findViewById(R.id.galleryCount);
        GalleryCount = (TextView) findViewById(R.id.realGalleryCount);
        moveBtn = (Button) findViewById(R.id.moveBtn);
        galleryBtn = (Button) findViewById(R.id.openGalery);
        addPopup = (Button) findViewById(R.id.popupDialog);
        startService = (Button) findViewById(R.id.start_service);
        imageView = (ImageView) findViewById(R.id.image);


        Integer size = mydb.getAllGallerys().size();
        dbNameCount.setText(String.valueOf(size));

        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(DialogTestActivity.this, AddActivity.class);
                startActivity(i);*/

                if(getPreferences().equals("ON") || getPreferences().isEmpty()){

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "YeS", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else{

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "NO", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                Intent i = new Intent(DialogTestActivity.this, DialogMainActivity.class);
                startActivity(i);
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*Intent gallery = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, SELECT_IMAGE);*/

                Intent i = new Intent(DialogTestActivity.this, PicSettingsActivity.class);
                startActivity(i);
               /* Intent i = new Intent(DialogTestActivity.this, GridAcitivy.class);
                startActivity(i);*/
            }
        });

        addPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DialogTestActivity.this);
                alert.setTitle(R.string.new_album);
//                alert.setMessage("Pls input Album's Name");

                // Create EditText for entry
                final EditText input = new EditText(DialogTestActivity.this);
                input.setHint(R.string.new_album_hint);
                LinearLayout.LayoutParams editLayoutParams = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                editLayoutParams.setMargins(50, 60, 50, 25);
                input.setLayoutParams(editLayoutParams);
                input.setTextSize(13);
                LinearLayout layout = new LinearLayout(DialogTestActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(input);
                alert.setView(layout);

                // Make an "OK" button to save the name
                alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        // Grab the EditText's input
                        String inputName = input.getText().toString();

                        // Put it into memory (don't forget to commit!)
                       /* SharedPreferences.Editor e = mSharedPreferences.edit();
                        e.putString(PREF_NAME, inputName);
                        e.commit();*/

                        // Welcome the new user
                        gh.makeDirectory(inputName);

                    }
                });

                // Make a "Cancel" button
                // that simply dismisses the alert
                alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {}
                });

                alert.show();
            }
        });

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(DialogTestActivity.this, FifthService.class));
            }
        });

        Log.i("gallery", String.valueOf(gh.getGallery().size()));




    }

    // 값 불러오기
    private String getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("popup", "");
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
            Log.i("path",selectedImage.toString());
            String path = gh.getPath(selectedImage);
            Log.i("path",path);

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
