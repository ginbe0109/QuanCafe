package com.example.user.quancafe.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.adapter.BanAdapter;
import com.example.user.quancafe.activity.model.Ban;
import com.example.user.quancafe.activity.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.user.quancafe.activity.ultil.CheckConnect.ShowToast;

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
        // get danh sách bàn
        GetDataBan();
        ActionClickGridView();
        return view;
    }

    private void AnhXa() {
        gridViewBan = view.findViewById(R.id.gridView_fragmentDSBan_Ban);
        arrayListBan = new ArrayList<>();
        banAdapter = new BanAdapter(getActivity(), arrayListBan);
        gridViewBan.setAdapter(banAdapter);
    }
    private void GetDataBan() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest arrRequest = new JsonArrayRequest(Server.DuongdanBan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // nếu mảng không rỗng
                if(response != null){
                    int sttban = 0;
                    int trangthai = 0;
                    // lấy từ phần tử object của mảng gán vào adapter
                    for(int i = 0; i < response.length(); i++){
                        try{
                            JSONObject jsonObject = response.getJSONObject(i);
                            sttban = jsonObject.getInt("sttban");
                            trangthai = jsonObject.getInt("trangthai");
                            arrayListBan.add(new Ban(sttban,trangthai));
                            banAdapter.notifyDataSetChanged();
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowToast(getActivity(),"Vui lòng kiểm tra kết nối");
            }
        });
        requestQueue.add(arrRequest);
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
