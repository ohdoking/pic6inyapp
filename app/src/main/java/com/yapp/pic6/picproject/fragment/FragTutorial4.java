package com.yapp.pic6.picproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.yapp.pic6.picproject.R;

/**
 * Created by Administrator on 2016-06-28.
 */
public class FragTutorial4 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.tu4, container, false);

        ImageView purple = (ImageView)myFragmentView.findViewById(R.id.purple4);
        Animation animTrans = AnimationUtils.loadAnimation(getActivity(), R.anim.trans_tu4);
        System.out.println("1");
        purple.startAnimation(animTrans);
        System.out.println("2");
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
