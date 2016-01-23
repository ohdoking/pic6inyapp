package com.yapp.pic6.picproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yapp.pic6.picproject.R;
import com.yapp.pic6.picproject.dao.Gallery;
import com.yapp.pic6.picproject.service.GalleryHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015-08-29.
 */
public class GridPagerAdapter extends PagerAdapter {
    Context context;
    public int gridPage;
    GalleryHelper gh;
    private ArrayList<String> mThumbTxtList;
    private ArrayList<Integer> mThumbIdsList;

    private ArrayList<String> mThumbDirList;
    String[] mThumbTxt = {"", "", "", "", "", "", "", ""};
    int[] mThumbIds = {0, 0, 0, 0, 0, 0, 0, 0};
    String[] mThumbDir = {"", "", "", "", "", "", "", ""};

    boolean firstInstantiate = true;



    public GridPagerAdapter(Context context){
        this.context=context;
        gh = new GalleryHelper(context);
        mThumbTxtList = new ArrayList<String>();
        mThumbIdsList = new ArrayList<Integer>();
        mThumbDirList = new ArrayList<String>();
        init();
    }
    public void init(){
        ArrayList<Gallery> gallerys = gh.getGallery();
        int i = 1;
        String trash = context.getResources().getString(R.string.trash);
        mThumbTxtList.add(trash);
        mThumbIdsList.add( R.drawable.blank_dir);
        mThumbDirList.add("0");
        for (Gallery gallery:gallerys){
            mThumbTxtList.add(gallery.getName());
            mThumbIdsList.add( R.drawable.blank_dir);
            mThumbDirList.add(gallery.getImagePath());
        }
        String newfolder = context.getResources().getString(R.string.new_album_title);
        mThumbTxtList.add(newfolder);
        mThumbIdsList.add( R.drawable.blank_dir);
        mThumbDirList.add("1");
    }

    @Override
    public int getCount() {
        // 한 화면에 8개씩 한 카운트 ex) 디렉토리가 9개일 때 (9+7)/8 = 2 카운트
        return (((mThumbTxtList.size())+7)/8);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((GridView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        int mPosition = position;
        for(int i = 0; i < 8 ; i++){
            if(position * 8 + i < mThumbTxtList.size()) {
                if(firstInstantiate && position == 1) {
//                    Log.i("그리드", "일");
                    // 처음 실행시에 position이 0과 1을 모두 받으므로 처음 position 1일 경우 position-1로 실행
                    mPosition = position - 1;
                }
//                Log.i("그리드", "for문 if 도는 중 / " + position + "/" + i);
                mThumbTxt[i] = mThumbTxtList.get(mPosition * 8 + i);
                mThumbIds[i] = mThumbIdsList.get(mPosition * 8 + i);
                mThumbDir[i] = mThumbDirList.get(mPosition * 8 + i);
            } else {
                firstInstantiate = false;
//                Log.i("그리드", "for문 else 도는 중 / " + position + "/" + i);
                mThumbTxt[i] = "";
                mThumbIds[i] = 0;
                mThumbDir[i] = "";
            }
        }


//        Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
        gridPage = position;

        final GridView gridView = new GridView(context);
        gridView.setNumColumns(4);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setAdapter(new GridAdapter(context, mThumbTxt, mThumbIds, mThumbDir));
        gridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }

        });
        ((ViewPager) container).addView(gridView, 0);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),gridPage+"*8+"+position,Toast.LENGTH_SHORT).show();
                String str = context.getResources().getString(R.string.trash);
                String str2 = context.getResources().getString(R.string.new_album_title);
                Log.i("where",String.valueOf(getPreferences()));
                int resultInt = gridPage * 8 + position + 1;
                Log.i("ss", String.valueOf(resultInt));
                if(mThumbTxtList.get(gridPage*8+position).equals(str)){
                    gh.moveTrash(mThumbDirList.get(resultInt));
//                    gh.moveTrash(mThumbDirList.get(resultInt));


                    notifyDataSetChanged();
//                    notifyAll();
                }
                else if(mThumbTxtList.get(gridPage*8+position).equals(str2)){

                    ContextThemeWrapper themedContext;
                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
                        themedContext = new ContextThemeWrapper( context ,R.style.AppTheme );
                    }
                    else {
                        themedContext = new ContextThemeWrapper( context, android.R.style.Theme_Light_NoTitleBar );
                    }
                    AlertDialog.Builder alert = new AlertDialog.Builder(themedContext);
                    alert.setTitle(R.string.new_album_title);
//                alert.setMessage("Pls input Album's Name");

                    // Create EditText for entry
                    final EditText input = new EditText(context);
                    input.setHint(R.string.new_album_hint);
                    LinearLayout.LayoutParams editLayoutParams = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    editLayoutParams.setMargins(50, 60, 50, 25);
                    input.setLayoutParams(editLayoutParams);
                    input.setTextSize(13);
                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.addView(input);
                    alert.setView(layout);

                    // Make an "OK" button to save the name
                    alert.setPositiveButton(R.string.new_album_confirm, new DialogInterface.OnClickListener() {

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
                    alert.setNegativeButton(R.string.new_album_cancel, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });

                    alert.show();
                }
                else{
                    File f = new File(mThumbDirList.get(resultInt));
                    String fileName = f.getName();
                    f.toString().replace(fileName, "");
                    gh.fileUMove(mThumbDirList.get(1), f.toString()+fileName);
                }
            }
        });
        return gridView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((GridView) object);
    }
    private int getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return pref.getInt("where", 0);
    }
}