package com.yapp.pic6.picproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yapp.pic6.picproject.R;

/**
 * Created by Administrator on 2016-06-28.
 */
public class FragTutorial5 extends Fragment{
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.tu5, container, false);
        Button btn_x = (Button) myFragmentView.findViewById(R.id.btn_x);
        btn_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        FrameLayout fl = (FrameLayout) myFragmentView.findViewById(R.id.last_tutorial);
        fl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    getActivity().finish();
                    return true;
                }

                return false;
            }
        });


        return myFragmentView;
    }
}
