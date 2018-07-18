package com.example.user.quancafe.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.quancafe.R;

/**
 * Created by User on 13/07/2018.
 */

public class DetailTableFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_table,null);
        AnhXa();

        return view;
    }

    private void AnhXa() {


    }



}
