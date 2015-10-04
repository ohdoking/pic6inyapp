package com.yapp.pic6.picproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class FinishTestActivity extends Activity {

    private CheckBox finishCheck;
    private Button clouseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_test);

        finishCheck = (CheckBox)findViewById(R.id.finishChk);
        clouseButton = (Button)findViewById(R.id.clouseBtn);


        clouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(finishCheck.isChecked()){

                }*/


            }
        });


    }

}
