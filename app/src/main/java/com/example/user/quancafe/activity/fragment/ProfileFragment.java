package com.example.user.quancafe.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.adapter.BanAdapter;
import com.example.user.quancafe.activity.model.Ban;

import java.util.ArrayList;

/**
 * Created by User on 27/06/2018.
 */

public class ProfileFragment extends Fragment {
    private View view;
    private GridView gridViewBan;
    private BanAdapter banAdapter;
    private ArrayList<Ban> arrayListBan;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,null);
        AnhXa();
        ActionClickGridView();
        return view;
    }

    private void AnhXa() {
        gridViewBan = view.findViewById(R.id.gridView_fragmentDSBan_Ban);
        arrayListBan = new ArrayList<>();
        banAdapter = new BanAdapter(getActivity(), arrayListBan);
        gridViewBan.setAdapter(banAdapter);

        arrayListBan.add(new Ban(1,0));
        arrayListBan.add(new Ban(2,1));
        arrayListBan.add(new Ban(3,1));
        arrayListBan.add(new Ban(4,0));
        arrayListBan.add(new Ban(5,0));
        arrayListBan.add(new Ban(6,0));
        arrayListBan.add(new Ban(7,0));
        arrayListBan.add(new Ban(8,0));

        banAdapter.notifyDataSetChanged();
    }
    private void ActionClickGridView() {
        gridViewBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Inflate the fragment
                // chuyển qua fragment chi tiết bàn
                DetailTableFragment fragment = new DetailTableFragment();
                Bundle thongtinBan = new Bundle();
                thongtinBan.putSerializable("thongtinban", arrayListBan.get(position));
                fragment.setArguments(thongtinBan);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, fragment).commit();
            }
        });
    }
}
