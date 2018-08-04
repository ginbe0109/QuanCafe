package com.example.user.quancafe.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.adapter.DanhSachMonAnAdapter;
import com.example.user.quancafe.activity.model.MonAn;
import com.example.user.quancafe.activity.ultil.CheckConnect;
import com.example.user.quancafe.activity.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 03/08/2018.
 */

public class SearchFragment extends Fragment {
    private View view;
    private ArrayList<MonAn> arrayMonAn;
    private ListView listViewMonAn;
    private DanhSachMonAnAdapter danhSachMonAnAdapter;
    private Bundle bundle;
    private String keyword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification,null);
        AnhXa();
        bundle = this.getArguments();
        // nếu bàn thêm món ăn mới thì click listview sẽ thêm vào danh sách món của bàn đó
        // ngược lại cho sự kiện click chuyển qua chi tiết món
        if (bundle != null) {

            // Danh sách món ăn theo từng loại
            // lấy id loại
            keyword = bundle.getString("keyword");
            //CheckConnect.ShowToast(getActivity(),keyword);
            GetData(keyword);
            ActionListThem();


        }else{

        }
        return view;
    }
    private void ActionListThem() {
        listViewMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailFoodFragment noti = new DetailFoodFragment();
                Bundle thongtinMon = new Bundle();
                thongtinMon.putSerializable("thongtinmon", arrayMonAn.get(position));
                noti.setArguments(thongtinMon);
                //Inflate the fragment
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, noti).commit();

            }
        });

    }


    private void GetData(final String keyword) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanTimKiem, new Response.Listener<String>() {
            // dùng phương thức này để lấy dữ liệu về
            @Override
            public void onResponse(String response) {
                // tạo biến hứng giá trị
                int idloai = 0;
                int maMon = 0;
                String tenMon = "";
                String hinhMon = "";
                String motaMon = "";
                float dongiaMon = 0;
                if (response != null) {
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        // lấy từng object của Array
                        for (int i = 0; i < jsonArr.length(); i++) {
                            // lấy từng values của từng object
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            maMon = jsonObject.getInt("mamon");
                            idloai = jsonObject.getInt("maloai");
                            tenMon = jsonObject.getString("ten");
                            hinhMon = jsonObject.getString("hinh");
                            motaMon = jsonObject.getString("mota");
                            dongiaMon = (float) jsonObject.getDouble("dongia");
                            // thêm dữ liệu vào mảng
                            arrayMonAn.add(new MonAn(maMon, idloai, tenMon, hinhMon, motaMon,dongiaMon));
                            // cập nhật Adapter
                            danhSachMonAnAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    // bắt sự kiện hết dữ liệu
                    CheckConnect.ShowToast(getActivity(),"Không có món theo từ khóa");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("KEYWORD",keyword);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        listViewMonAn = (ListView) view.findViewById(R.id.listView_fragmentNoti_MonAn);
        arrayMonAn = new ArrayList<>();
        danhSachMonAnAdapter = new DanhSachMonAnAdapter(arrayMonAn,getActivity());
        listViewMonAn.setAdapter(danhSachMonAnAdapter);
    }
}
