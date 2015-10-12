package com.yapp.pic6.picproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yapp.pic6.picproject.adapter.CardAdapter;
import com.yapp.pic6.picproject.dao.Gallery;
import com.yapp.pic6.picproject.service.GalleryHelper;

import java.io.File;
import java.util.ArrayList;

public class FinishTestActivity extends Activity {

    MyHorizontalLayout myHorizontalLayout;
    CardAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_test);

            myHorizontalLayout = (MyHorizontalLayout)findViewById(R.id.mygallery);
            mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

            // The number of Columns
            final GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);



            mAdapter = new CardAdapter(getApplicationContext(),new ArrayList<Gallery>());
            mRecyclerView.setAdapter(mAdapter);

            String ExternalStorageDirectoryPath = Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath();

            String targetPath = ExternalStorageDirectoryPath + "/trash/";

            Toast.makeText(getApplicationContext(), GalleryHelper.TRASHPATH, Toast.LENGTH_LONG).show();
            File targetDirector = new File(GalleryHelper.TRASHPATH);

            File[] files = targetDirector.listFiles();
            for (File file : files){
                myHorizontalLayout.add(file.getAbsolutePath());
            }





    }

}
