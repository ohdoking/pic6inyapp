package com.yapp.pic6.picproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yapp.pic6.picproject.adapter.CardAdapter;
import com.yapp.pic6.picproject.dao.Gallery;
import com.yapp.pic6.picproject.service.GalleryHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class PicMainActivity extends BaseActivity {

    CardAdapter mAdapter;
    RecyclerView mRecyclerView;

    LinearLayout layout;
    HorizontalScrollView scrollView;

    Button closeBtn;
    Button settingBtn;
    Button trashBtn;
    Button shareBtn;
    Button addFolderBtn;

    TextView tvCount;
    ArrayList<String> arrayList;
    ArrayList<Integer> arraySelect;
    ImageView[] imageView;
    FrameLayout[] frameLayout;
    ImageView[] foreImg;
    TextView[] imageText;

    CheckBox allPhotoCb;

    ArrayList<String> newPictureList;

    GalleryHelper gh;

    ArrayList<Gallery> items;



    LinearLayout wrap;
    Timer timer;
    public int count = 0;
    public float wrapYup, wrapYdown;
    private float swipeYup, swipeYdown;
    static final int MIN_SWIPE_DISTANCE = 150; //swipe 액션으로 인식하는 최소값
    static final int FIRST_ACTIVITY_Y = 700; //처음 액티비티 Y값
    static final int PER_DISTANCE = 10; //1ms마다 이동시킬 Y값
    boolean swipeMode = false; //true면 swipe 실행 중

    //DisplayMetrics mMetrics;
    private final long FINSH_INTERVAL_TIME = 3000;
    private long backPressedTime = 0;

    boolean add_client_error;
    Handler handler;

    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_layoutup, R.anim.anim_none);
        setContentView(R.layout.activity_pic_main);
        handler = new Handler();
        num = 0;

        gh = new GalleryHelper(PicMainActivity.this);

        File file = new File(gh.TRASHPATH);
        if (!file.exists() && !file.isDirectory()) {
            gh.makeDirectory("Trash");
        }

        getId();
        clickevent();

        // The number of Columns
        final GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        items = gh.getGallery();
        mAdapter = new CardAdapter(getApplicationContext(), items);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setArray(gh.newPicture());

        ga();

        wrap.setY(FIRST_ACTIVITY_Y);



        /*ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(gh.STRSAVEPATH+"a/myApp.PNG");
        arrayList.add(gh.STRSAVEPATH + "f/myApp.PNG");
        mAdapter.setArray(gh.newPicture());*/

    }

    public void ga() {
        newPictureList = gh.newPicture();
        /*Integer[] mThumbIds = {
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
        };*/

        arrayList = new ArrayList<String>();
        arraySelect = mAdapter.getSelectList();

        frameLayout = new FrameLayout[newPictureList.size()];
        imageView = new ImageView[newPictureList.size()];
        foreImg = new ImageView[newPictureList.size()];
        imageText = new TextView[newPictureList.size()];



//        tvCount.setText(String.valueOf(newPictureList.size()) + "장의 사진 중 " + String.valueOf(newPictureList.size()) + "장 선택");
        tvCount.setText(newPictureList.size() + " " + getResources().getString(R.string.main_select_of, newPictureList.size()));
//        tvCount.setText(newPictureList.size() + " " + getResources().getString(R.string.main_select_of, mAdapter.getTempImagePathList().size()));

//        tvCount.setText(String.valueOf(newPictureList.size()) + " - " + String.valueOf(newPictureList.size()) + "!");
        for (int i = 0; i < newPictureList.size(); i++) {
            final int index;
            index = i;
            mAdapter.getSelectedItem().add(1);

            frameLayout[i] = new FrameLayout(this);
            FrameLayout.LayoutParams lpf = new FrameLayout.LayoutParams(300, 300);
//            lpf.setMargins(0, 0, 700, 0);


            frameLayout[i].setLayoutParams(lpf);
            frameLayout[i].setPadding(0,0,80,0);
//

            imageView[i] = new ImageView(this);


//            LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            imageView[0].setColorFilter(Color.BLUE);
//            imageView[i].setLayoutParams(tp);

            Bitmap resized = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            //		options.inJustDecodeBounds = true;
            final Bitmap bitmapImage = BitmapFactory.decodeFile(newPictureList.get(i), options);
            if (bitmapImage != null) {
                resized = Bitmap.createScaledBitmap(bitmapImage, 500, 500, true);
            }


            imageView[i].setImageBitmap(resized);
            frameLayout[i].addView(imageView[i]);

            foreImg[i] = new ImageView(this);
            frameLayout[i].addView(foreImg[i]);

            imageText[i] = new TextView(this);
            imageText[i].setGravity(Gravity.CENTER);
            imageText[i].setTextColor(Color.parseColor("#363636"));
            frameLayout[i].addView(imageText[i]);

            layout.addView(frameLayout[i]);

            //  imageView[i].setPadding(100,100,100,100);
///            layout.addView(textView[i]);

            arrayList.add(i, String.valueOf(index));
            arraySelect.add(i, 1);
            mAdapter.setTempImagePath(i);
            num = 0;
            for (Integer j : arraySelect) {
                if (j == 1) {
                    ++num;
                }
            }

            foreImg[index].setImageResource(R.mipmap.check_shadow);
            imageView[index].setColorFilter(Color.TRANSPARENT);

            imageView[index].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    allPhotoCb.setChecked(false);




                    Log.i("ohdokingLog",mAdapter.getSelectedItem().get(index).toString() + " : " + imageText[index].getText().toString());

                    if (arraySelect.get(index) == 0) {//&& !arrayList.contains(index)) {
//                        imageView[index].setColorFilter(Color.CYAN);
                        foreImg[index].setImageResource(R.mipmap.check_shadow);
                        imageView[index].setColorFilter(Color.TRANSPARENT);
                        arraySelect.set(index, 1);
                        mAdapter.setTempImagePath(index);
                        arrayList.add(String.valueOf(index));

                    } else {
                        foreImg[index].setImageResource(0);
                        imageView[index].setColorFilter(Color.TRANSPARENT);
                        // selected = 0;
                        arraySelect.set(index, 0);
                        mAdapter.removeTempImagePath(index);
                        arrayList.remove(String.valueOf(index));
//                        arraySelect.remove(index);
                    }
                    if(mAdapter.getSelectedItem().get(index) == 1 && !imageText[index].getText().equals("")){
                        Log.i("ohdokingLog", "change!!!!");
                        imageView[index].getDrawable().setAlpha(50);
                        mAdapter.getSelectedItem().set(index, 0);
                    }
                    else if(mAdapter.getSelectedItem().get(index) == 1){
                        Log.i("ohdokingLog","wait change!!!!");
                        mAdapter.getSelectedItem().set(index, 0);
                    }
                    else{
                        Log.i("ohdokingLog","no change!!!!");
                        mAdapter.getSelectedItem().set(index, 1);
                    }

                    if (arraySelect.indexOf(0) == -1) {
                        allPhotoCb.setChecked(true);
                    }


                    num = 0;
                    for (Integer j : arraySelect) {
                        if (j == 1) {
                            ++num;
                        }
                    }


//                    tvCount.setText(String.valueOf(newPictureList.size()) + "장의 사진 중 " + String.valueOf(num) + "장 선택 ");
                    tvCount.setText(newPictureList.size() + " " + getResources().getString(R.string.main_select_of, num));
//                    tvCount.setText(String.valueOf(newPictureList.size()) + " -  "+String.valueOf(num)+"! ");


                }
            });


            imageView[index].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent i = new Intent(PicMainActivity.this, PhotoViewActivity.class);
                    i.putExtra("event_name", bitmapImage);
                    startActivity(i);

                    return false;
                }
            });

        }
        if (arraySelect.indexOf(0) == -1) {
            allPhotoCb.setChecked(true);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.anim_layoutup, R.anim.anim_none);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_none, R.anim.anim_layoutdown);
    }

    public void getId() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        closeBtn = (Button) findViewById(R.id.btn_close);
        settingBtn = (Button) findViewById(R.id.btn_setting);
        trashBtn = (Button) findViewById(R.id.btn_trash);
        shareBtn = (Button) findViewById(R.id.btn_share);
        addFolderBtn = (Button) findViewById(R.id.btn_add_folder);

        tvCount = (TextView) findViewById(R.id.tv_photo_count);

        allPhotoCb = (CheckBox) findViewById(R.id.cb_all_photo);

        wrap = (LinearLayout) findViewById(R.id.wrap);

        layout = (LinearLayout) findViewById(R.id.layout);
        scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
    }

    public void clickevent() {

        final Animation anim_bigtosmall = AnimationUtils.loadAnimation(this, R.anim.anim_bigtosmall);
        final Animation anim_imgdelete = AnimationUtils.loadAnimation(this, R.anim.anim_imgdelete);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBtn.startAnimation(anim_bigtosmall);
//                finish();
                closeAndSave();


            }

        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            int i = 0;

            @Override
            public void onClick(View v) {
                settingBtn.startAnimation(anim_bigtosmall);
                Intent i = new Intent(PicMainActivity.this, PicSettingsActivity.class);
                startActivity(i);
                finish();

                /*if(i == 0){
                    mAdapter.setTempImagePath(0);
                    i = 1;
                }
                else{
                    mAdapter.removeTempImagePath(0);
                    i = 0;
                }*/

            }
        });
        trashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*gh.moveTrash(gh.STRSAVEPATH + "/e/myApp.PNG");
                refresh();*/
                tvCount.setText(newPictureList.size() + " " + getResources().getString(R.string.main_select_of, 0));

//                img99.getDrawable().setAlpha(50);


                trashBtn.startAnimation(anim_bigtosmall);
                allPhotoCb.setChecked(false);
                //비었을때
                if (mAdapter.getTempImagePathList().isEmpty()) {

                    Toast.makeText(getApplicationContext(), R.string.main_please_select, Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getApplicationContext(), mAdapter.getTempImagePathList().size() + " 장의 사진이 휴지통으로 이동되었습니다 ", Toast.LENGTH_SHORT).show();
                    Log.i("ohdoking", "ohdokingCheck");

                    String message = getResources().getString(R.string.main_trash);
                    Toast.makeText(getApplicationContext(), mAdapter.getTempImagePathList().size() + message, Toast.LENGTH_SHORT).show();
                    for (Integer imagePath : mAdapter.getTempImagePathList()) {
                        mAdapter.getRealImagePathList().set(imagePath, gh.TRASHPATH);
                        Log.i("integer", String.valueOf(imagePath));

                        imageView[imagePath].startAnimation(anim_imgdelete);

                        imageView[imagePath].setColorFilter(Color.parseColor("#99999999"));
                        foreImg[imagePath].setImageResource(0);
                        imageView[imagePath].setColorFilter(Color.TRANSPARENT);
                        imageText[imagePath].setText("Trash");
//                        imageText[imagePath].setText(R.string.trash);
                    }
                    for (String str : mAdapter.getOriginImagePathList()) {
                        Log.i("origin", str);
                    }
                    for (String str : mAdapter.getRealImagePathList()) {
                        Log.i("move", str);
                    }
                    for (int j = 0; j < arraySelect.size(); j++) {
                        arraySelect.set(j, 0);
                    }
                    mAdapter.getTempImagePathList().clear();
                }
                for(int i = 0 ; i < mAdapter.getSelectedItem().size();i++){

//                    if(mAdapter.getSelectedItem().get(i) == 1){
                    imageView[i].getDrawable().setAlpha(50);
                    mAdapter.getSelectedItem().set(i, 0);
//                    }
                }
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareBtn.startAnimation(anim_bigtosmall);
                //비었을때
                if (mAdapter.getTempImagePathList().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "사진을 선택해주세요 ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), R.string.main_please_select, Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> tempShare = new ArrayList<String>();
                    for (Integer imagePath : mAdapter.getTempImagePathList()) {
                        mAdapter.getRealImagePathList().set(imagePath, gh.TRASHPATH);
                        Log.i("integer", String.valueOf(imagePath));

                        tempShare.add(mAdapter.getOriginImagePathList().get(imagePath));

                    }
                    shareImage(tempShare);
                }


            }
        });
        addFolderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFolderBtn.startAnimation(anim_bigtosmall);
                addFolderDialog();
//                refresh();
            }
        });

        allPhotoCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allPhotoCb.isChecked() == true) {
                    int index = 0;
                    for (Integer select : arraySelect) {
                        if (select.equals(0)) {
                            int indexSelect = arraySelect.indexOf(0);
                            arraySelect.set(indexSelect, 1);
                            mAdapter.setTempImagePath(index);
                            arrayList.add(String.valueOf(indexSelect));
                            //imageView[indexSelect].setColorFilter(Color.CYAN);
                            foreImg[indexSelect].setImageResource(R.mipmap.check_shadow);
                            imageView[indexSelect].setColorFilter(Color.TRANSPARENT);
                        }
                        index = index + 1;
                    }
                } else {
                    int index = 0;
                    for (Integer select : arraySelect) {
                        if (select.equals(1)) {
                            int indexSelect = arraySelect.indexOf(1);
                            mAdapter.removeTempImagePath(index);
                            arraySelect.set(indexSelect, 0);
                            arrayList.clear();
                            foreImg[indexSelect].setImageResource(0);
                            imageView[indexSelect].setColorFilter(Color.TRANSPARENT);
                        }
                    }
                    index = index + 1;
                }
                num = 0;
                for (Integer j : arraySelect) {
                    if (j == 1) {
                        ++num;
                    }
                }

//                tvCount.setText(String.valueOf(newPictureList.size()) + " -  " + String.valueOf(num) + "! ");
//                tvCount.setText(String.valueOf(newPictureList.size()) + "장의 사진 중 " + String.valueOf(num) + "장 선택 ");
                tvCount.setText(newPictureList.size() + " " + getResources().getString(R.string.main_select_of, num));
            }
        });

    }

    public void addFolderDialog() {
        ContextThemeWrapper themedContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            themedContext = new ContextThemeWrapper(PicMainActivity.this, R.style.AppTheme);
        } else {
            themedContext = new ContextThemeWrapper(PicMainActivity.this, android.R.style.Theme_Light_NoTitleBar);
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(themedContext);
        alert.setTitle(R.string.new_album_title);
//                alert.setMessage("Pls input Album's Name");

        // Create EditText for entry
        final EditText input = new EditText(PicMainActivity.this);
        input.setHint(R.string.new_album_hint);
        input.setHintTextColor(Color.GRAY);
        LinearLayout.LayoutParams editLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editLayoutParams.setMargins(50, 60, 50, 25);
        input.setLayoutParams(editLayoutParams);
        input.setTextSize(13);
        LinearLayout layout = new LinearLayout(PicMainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(input);
        alert.setView(layout);

        // Make an "OK" button to save the name
        alert.setPositiveButton(R.string.new_album_confirm, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                // Put it into memory (don't forget to commit!)
                       /* SharedPreferences.Editor e = mSharedPreferences.edit();
                        e.putString(PREF_NAME, inputName);
                        e.commit();*/

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                }, 1000);

                String inputName = input.getText().toString();

                if (inputName.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.new_album_error_no_name, Toast.LENGTH_SHORT).show();
                    add_client_error = true;
                } else {
                    gh.makeDirectory(inputName);
                    add_client_error = false;
                }



            }
        });

        // Make a "Cancel" button
        // that simply dismisses the alert
        alert.setNegativeButton(R.string.new_album_cancel, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                add_client_error = false;
                dialog.cancel();
            }
        });

        final AlertDialog alertClient = alert.create();

        alertClient.show();


        alertClient.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                //If the error flag was set to true then show the dialog again
                if (add_client_error == true) {
                    alertClient.show();
                } else {
                    return;
                }






            }
        });
    }


    public void shareImage(ArrayList<String> imagePaths) {
        Intent share = new Intent(Intent.ACTION_SEND);

        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/*");


        ArrayList<Uri> arrayFile = new ArrayList<Uri>();

        for (String imagePath : imagePaths) {
            File imageFileToShare = new File(imagePath);
            Uri uri = Uri.fromFile(imageFileToShare);
            arrayFile.add(uri);
        }
        share.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayFile);

        String shareTitle = getResources().getString(R.string.share_title);
        startActivity(Intent.createChooser(share, shareTitle));
    }

    public void refresh() {
        items.clear();
        items.addAll(gh.getGallery());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                swipeYdown = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                swipeYup = event.getY();
                float deltaY = swipeYup - swipeYdown;
                if (Math.abs(deltaY) > MIN_SWIPE_DISTANCE) {

                    if (!swipeMode) {
                        swipeMode = true;
                        if (swipeYdown < swipeYup) { // 처음 터치한 Y값이 나중에 터치한 Y값보다 위이므로 swipe down
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    count++;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (wrap.getY() <= FIRST_ACTIVITY_Y) {
                                                wrapYdown = wrap.getY();
                                                wrap.setY(wrapYdown + PER_DISTANCE);
                                            } else {
                                                count = 0;
                                                timer.cancel();
                                                swipeMode = false;
                                            }
                                        }
                                    });
                                }
                            }, 0, 1);
                        } else { // swipe up
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    count++;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (wrap.getY() >= 0) {
                                                wrapYup = wrap.getY();
                                                wrap.setY(wrapYup - PER_DISTANCE);
                                            } else {
                                                count = 0;
                                                timer.cancel();
                                                swipeMode = false;
                                            }
                                        }
                                    });
                                }
                            }, 0, 1);
                        }
                    }

                }
                // 빈공간 클릭
                else {
                        /*long tempTime = System.currentTimeMillis();
                        long intervalTime = tempTime - backPressedTime;

                        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
                            closeAndSave();
                        } else {
                            backPressedTime = tempTime;
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        }
                        */
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
            closeAndSave();
        } else {
            backPressedTime = tempTime;
            String str = getResources().getString(R.string.main_close);
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    }


    void closeAndSave() {
        ArrayList<String> moveList = mAdapter.getRealImagePathList();
        ArrayList<String> originList = mAdapter.getOriginImagePathList();
        for (int i = 0; i < moveList.size(); i++) {
//                    String s = gh.STRSAVEPATH+"/a/myApp.PNG";
            String imagePath = originList.get(i).toString();
            String movePath = moveList.get(i).toString();
            File orginFile = new File(imagePath);
            File moveFile = new File(movePath);
            if (!imagePath.equals(movePath)) {

                Log.i("ohdoking", imagePath + " -> " + movePath + "/" + orginFile.getName());

                if (!imagePath.equals(movePath + "/" + orginFile.getName())) {
                    gh.fileUMove(imagePath, movePath + "/" + orginFile.getName());
                }

            } else {
                Log.i("ohdoking", "same");
            }

//                    gh.fileUMove(imagePath,imagePath+"/"+f.getName());
        }
        finish();
    }

}
