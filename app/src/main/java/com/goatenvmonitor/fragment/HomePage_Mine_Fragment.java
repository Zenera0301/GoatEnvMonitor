package com.goatenvmonitor.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.goatenvmonitor.ControlActivity;
import com.goatenvmonitor.GetPostUtil;
import com.goatenvmonitor.R;
import com.goatenvmonitor.fragmentmine.APPGuideActivity;
import com.goatenvmonitor.fragmentmine.AboutProjectActivity;
import com.goatenvmonitor.fragmentmine.ContactUSActivity;
import com.goatenvmonitor.fragmentmine.VersionActivity;

public class HomePage_Mine_Fragment extends Fragment implements View.OnClickListener{

    private RelativeLayout rl_1,rl_2,rl_3,rl_4;
    private Button btn_d1,btn_d2,btn_d3,btn_d4;

    public HomePage_Mine_Fragment() {
    }

    public static HomePage_Mine_Fragment newInstance(String title) {
        HomePage_Mine_Fragment fragment = new HomePage_Mine_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        rl_1 = (RelativeLayout)view.findViewById(R.id.rl_d1);
        rl_2 = (RelativeLayout)view.findViewById(R.id.rl_d2);
        rl_3 = (RelativeLayout)view.findViewById(R.id.rl_d3);
        rl_4 = (RelativeLayout)view.findViewById(R.id.rl_d4);
        btn_d1 = (Button)view.findViewById(R.id.btn_d1);
        btn_d2 = (Button)view.findViewById(R.id.btn_d2);
        btn_d3 = (Button)view.findViewById(R.id.btn_d3);
        btn_d4 = (Button)view.findViewById(R.id.btn_d4);
        btn_d1.setOnClickListener(this);
        btn_d2.setOnClickListener(this);
        btn_d3.setOnClickListener(this);
        btn_d4.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_d1:
                Intent intent1 = new Intent(getContext(), APPGuideActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_d2:
                Intent intent2 = new Intent(getContext(), VersionActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_d3:
                Intent intent3 = new Intent(getContext(),AboutProjectActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_d4:
                Intent intent4 = new Intent(getContext(),ContactUSActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
