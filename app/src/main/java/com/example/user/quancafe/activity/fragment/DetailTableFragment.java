package com.example.user.quancafe.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.adapter.ChiTietBanAdapter;
import com.example.user.quancafe.activity.model.Ban;
import com.example.user.quancafe.activity.model.Giohang;
import com.example.user.quancafe.activity.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 13/07/2018.
 */

public class DetailTableFragment extends Fragment {
    private View view;
    private Bundle bundle;
    private Ban ban;
    ListView listViewGioHangCTBAN;
    TextView txtthongbaoCTBAN;
    //static TextView txttongtiengioihang;
    Button btntieptucmua, btnthanhtoan;
    ChiTietBanAdapter chiTietBanAdapter;
    ArrayList<Giohang> mangGiohangChiTietBan;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_table,null);
        AnhXa();
        bundle = this.getArguments();
        if (bundle != null) {

            // Danh sách món ăn theo từng loại
            // lấy id loại
            ban = (Ban) bundle.getSerializable("thongtinban");
            // lấy ds món được đạt chưa được thanh toán theo từng bàn
           // CheckConnect.ShowToast(getActivity(),ban.getStt()+"");
            GetDanhSachMonTheoBan(ban.getStt());





        }
        return view;
    }

    private void GetDanhSachMonTheoBan(final int sttban) {

        RequestQueue requsetQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdanggetdsmonchuathanhtoantheoban, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null){
                    int mamon = 0;
                    String ten = "";
                    long dongia = 0;
                    String hinh = "";
                    int soluong = 0;
                    // lấy từ phần tử object của mảng gán vào adapter
                    JSONArray jsonArr = null;
                    try {
                        jsonArr = new JSONArray(response);
                        for(int i = 0; i < response.length(); i++){
                            try{
                                JSONObject jsonObject = jsonArr.getJSONObject(i);
                                mamon = jsonObject.getInt("mamon");
                                ten = jsonObject.getString("ten");
                                dongia = jsonObject.getLong("dongia");
                                hinh = jsonObject.getString("hinh");
                                soluong = jsonObject.getInt("soluong");
                              //  CheckConnect.ShowToast(getActivity(),mamon+" "+ten+" "+dongia+" "+hinh+" "+soluong);
                                mangGiohangChiTietBan.add(new Giohang(mamon,ten,dongia,hinh,soluong));
                                CheckData();
                                chiTietBanAdapter.notifyDataSetChanged();
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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
                params.put("STTBAN",String.valueOf(sttban));
                return params;
            }
        };
        requsetQueue.add(stringRequest);

    }
    private void CheckData() {
        // nếu mangGiohang.size() không chứa sản phẩm nào thì cho thông báo hiện lên, ẩn listview
        if(mangGiohangChiTietBan.size() <= 0){
            chiTietBanAdapter.notifyDataSetChanged(); // cập nhật adapter
            txtthongbaoCTBAN.setVisibility(View.VISIBLE); // hiện thông báo
            listViewGioHangCTBAN.setVisibility(View.INVISIBLE); // ẩn listview

        }else{
            chiTietBanAdapter.notifyDataSetChanged(); // cập nhật adapter
            txtthongbaoCTBAN.setVisibility(View.INVISIBLE); // ẩn thông báo
            listViewGioHangCTBAN.setVisibility(View.VISIBLE); // hiện listview
        }

    }

    private void AnhXa() {
        listViewGioHangCTBAN = view.findViewById(R.id.listviewGioHang_detailtable);
        mangGiohangChiTietBan = new ArrayList<>();
        chiTietBanAdapter = new ChiTietBanAdapter(mangGiohangChiTietBan,getActivity());
        listViewGioHangCTBAN.setAdapter(chiTietBanAdapter);
        btnthanhtoan = view.findViewById(R.id.btnThanhtoangiohang_detailtable);
        btntieptucmua = view.findViewById(R.id.btnTieptucgiohang_detailtable);
        txtthongbaoCTBAN = view.findViewById(R.id.textThongbao_detailtable);
        //cấp phát vùng bộ nhớ cho mảng
        if(mangGiohangChiTietBan != null){
            // nếu mảng Giỏ Hàng đã có dữ liệu thì không cấp phát lại
            // để không bị mất dữ liệu
        }else{
            // ngược lại mảng không có dữ liệu thì cấp phát vùng bộ nhớ
            mangGiohangChiTietBan = new ArrayList<>();
        }


    }







}
