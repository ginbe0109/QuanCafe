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
import com.example.user.quancafe.activity.adapter.LoaiMonAnAdapter;
import com.example.user.quancafe.activity.model.LoaiMonAn;
import com.example.user.quancafe.activity.ultil.CheckConnect;
import com.example.user.quancafe.activity.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 27/06/2018.
 */

public class HomeFragment extends Fragment {

    private View view;
    private ArrayList<LoaiMonAn> arrayLoaiMon;
    private LoaiMonAnAdapter adapterLoaiMon;

    private GridView gridViewMonAn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        view = inflater.inflate(R.layout.fragment_home, null);
        AnhXa();
        GetData();
        CatchItemClickLoai();

        return view;
    }

    private void GetData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest arrRequest = new JsonArrayRequest(Server.Duongdanloaimon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // nếu mảng không rỗng
                if(response != null){
                    int id = 0;
                    String tenloai;
                    String hinhloai;
                    // lấy từ phần tử object của mảng gán vào adapter
                    for(int i = 0; i < response.length(); i++){
                        try{
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("idloai");
                            tenloai = jsonObject.getString("tenloai");
                            hinhloai = jsonObject.getString("hinhloai");
                            arrayLoaiMon.add(new LoaiMonAn(id,tenloai,hinhloai));
                            adapterLoaiMon.notifyDataSetChanged();
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnect.ShowToast(getActivity(),"Vui lòng kiểm tra kết nối");
            }
        });
        requestQueue.add(arrRequest);
    }

    // bắt sự kiện GridView Loại món ăn
    private void CatchItemClickLoai() {
        // bắt sự kiện
        gridViewMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotificationFragment noti = new NotificationFragment();
                Bundle idloai = new Bundle();
                idloai.putString("idloai", String.valueOf(arrayLoaiMon.get(position).getIdloai()));
                noti.setArguments(idloai);

                //Inflate the fragment
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, noti).commit();

//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.contentMain, new DanhMucFragment());
//                fragmentTransaction.commit();

            }
        });

    }

    private void AnhXa() {
        gridViewMonAn = view.findViewById(R.id.gridView_fragmentHome_LoaiMonAn);
        // khai báo cho mảng loại
        arrayLoaiMon = new ArrayList<>();
        adapterLoaiMon = new LoaiMonAnAdapter(getActivity(),arrayLoaiMon);
        // GView
        gridViewMonAn.setAdapter(adapterLoaiMon);

    }
}
