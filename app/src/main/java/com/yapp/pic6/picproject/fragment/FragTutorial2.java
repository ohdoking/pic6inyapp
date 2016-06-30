package com.yapp.pic6.picproject.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yapp.pic6.picproject.R;

/**
 * Created by Administrator on 2016-06-28.
 */
public class FragTutorial2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.tu2, container, false);
        Button btn_x = (Button)myFragmentView.findViewById(R.id.btn_x);
        btn_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return  myFragmentView;
    }
}
