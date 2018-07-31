package com.example.user.quancafe.activity.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.activity.LogInActivity;
import com.example.user.quancafe.activity.adapter.DanhSachMonAnAdapter;
import com.example.user.quancafe.activity.model.Ban;
import com.example.user.quancafe.activity.model.MonAn;
import com.example.user.quancafe.activity.ultil.CheckConnect;
import com.example.user.quancafe.activity.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 21/07/2018.
 */

public class ListFoodFragment extends Fragment {
    private View view;
    private int idloai = 0;
    private int page = 1;

    private ArrayList<MonAn> arrayMonAn;
    private ListView listViewMonAn;
    private DanhSachMonAnAdapter danhSachMonAnAdapter;
    private View footerview;

    private boolean isLoading = false;
    private boolean limitData = false;
    private mHandler mHandler;
    private Bundle bundle;
    private int idhoadon = 0;
    private int sttban = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification,null);

        AnhXa();
        GetData(page);
        LoadMoreData();
        bundle = this.getArguments();
        // nếu bàn thêm món ăn mới thì click listview sẽ thêm vào danh sách món của bàn đó
        // ngược lại cho sự kiện click chuyển qua chi tiết món
        if (bundle != null) {

            // Danh sách món ăn theo từng loại
            // lấy id loại
            idhoadon = bundle.getInt("stthoadon");
            sttban = bundle.getInt("sttban");
            ActionListMonAnThem();

        }else{

         ActionListThem();
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

    private void ActionListMonAnThem() {
        listViewMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //xacNhanThemMon(arrayMonAn.get(position));
                xacNhanThemMon(arrayMonAn.get(position));
              //  CheckConnect.ShowToast(getActivity(),arrayMonAn.get(position).getMaloaiMon()+"");
            }
        });

    }
    public void xacNhanThemMon(final MonAn monAn){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("SỐ LƯỢNG");
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setMessage("Số lượng sảm phẩm?");
        final Spinner spinnerChiTiet = new Spinner(getActivity());
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterSpinner = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_dropdown_item_1line, soluong);
        spinnerChiTiet.setAdapter(adapterSpinner);
        alert.setView(spinnerChiTiet);
        alert.setPositiveButton("Đặt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // số lượng
                final int soluong = Integer.parseInt(spinnerChiTiet.getSelectedItem().toString());
                //CheckConnect.ShowToast(getActivity(),soluong+"");
                /// get current time
                Date time = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final String currenttime = df.format(time);
                final Ban ban = new Ban(sttban,1);
                final int sttban = ban.getStt();
                final int trangthai = ban.getTrangthai();
                //update trạng thái bàn
                RequestQueue requsetQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanCapNhatBan, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // nếu đẩy dữ liệu thành cônng thì hêm ds món trong giỏ hàng lên server
                        if (Integer.parseInt(response) > 0){
                            // đảy dự liệu lên server
                            // thêm ds món trong giỏ hàng lên server
                            RequestQueue Queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                            StringRequest request = new StringRequest(Request.Method.POST, Server.DuongdanThemMon, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("1")){

                                        //// Cập nhật hóa đơn sau thi thêm món ăn vào
                                        UpDateHoaDon(idhoadon);
                                        //Inflate the fragment
                                        // chuyển qua fragment chi tiết bàn
                                        DetailTableFragment fragment = new DetailTableFragment();
                                        Bundle thongtinBan = new Bundle();
                                        thongtinBan.putSerializable("thongtinban", ban);
                                        fragment.setArguments(thongtinBan);
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, fragment).commit();
                                    }else{
                                        CheckConnect.ShowToast(getActivity().getApplicationContext(),"Thêm món không thành công");
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    // chuyển jsonArray thành dạng chuỗi để đưa lên server
                                    HashMap<String, String> param = new HashMap<String, String>();
                                    param.put("mand", LogInActivity.mand+"");
                                    param.put("mamon",monAn.getMaMon()+"");
                                    param.put("sttban",ban.getStt()+"");
                                    param.put("sohd",idhoadon+"");
                                    param.put("thoigian",currenttime);
                                    param.put("trangthai",0+"");
                                    param.put("soluong",soluong+"");
                                    return param;
                                }
                            };

                                 Queue.add(request);
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
                        params.put("sttban",String.valueOf(sttban));
                        params.put("trangthai",String.valueOf(trangthai));
                        return params;
                    }
                };
                requsetQueue.add(stringRequest);


                //CheckConnect.ShowToast(getActivity(), currenttime+" "+"id hoa don "+idhoadon+" ma mon "+monAn.getMaMon()+" sttban"+sttban+" nguoi"+ LogInActivity.mand);


            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();

    }
    private void UpDateHoaDon(final int stthdon) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdangCapNhatHoaDon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                // bàn chưa thanh toán
                params.put("SOHD",String.valueOf(stthdon));
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }


    private void LoadMoreData() {
        // bắt sự kiện click vào từng Item

        listViewMonAn.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // vuốt listView trả vào trong function này
                // hàm if để bắt giá trị cuối trong
                //lần đầu tiên run lên thì totalItenCount = 0 nên đặt total khác không để không bị nhảy vào function if liền
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false) {
                    // nhảy vào function thì đang load dữ liệu
                    isLoading = true;
                    // Thực hiện cho Theard hoạt động
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }

            }
        });

    }
    private void AnhXa() {
//        listViewMonAn = view.findViewById(R.id.listView_fragmentNoti_MonAn);
//        arrayMonAn = new ArrayList<>();
//        danhSachMonAnAdapter = new DanhSachMonAnAdapter(arrayMonAn,getActivity());
//        listViewMonAn.setAdapter(danhSachMonAnAdapter);
        listViewMonAn = (ListView) view.findViewById(R.id.listView_fragmentNoti_MonAn);
        arrayMonAn = new ArrayList<>();
        danhSachMonAnAdapter = new DanhSachMonAnAdapter(arrayMonAn,getActivity());
        listViewMonAn.setAdapter(danhSachMonAnAdapter);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        footerview = layoutInflater.inflate(R.layout.progressbar, null);

        // khởi tạo handler
        mHandler = new mHandler();
    }
    // phương thức GetData(int page) dùng để đưa idloai lên server
    // sau đó lấy dữ liệu(là thông tin món ăn) về theo idloai
    // biến page giúp lấy đối tượng theo từng trang
    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String duongdan = Server.urlMon + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
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
                if (response != null && response.length() != 2) {
                    // có dữ liệu trả về thì thanh progressBar tắt đi
                    listViewMonAn.removeFooterView(footerview);
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
                            // CheckConnect.ShowToast(getActivity(),maMon+" "+idloai+" "+" "+tenMon+" "+" "+hinhMon+" "+motaMon+" "+dongiaMon);

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
                    limitData = true;
                    listViewMonAn.removeFooterView(footerview);
                    CheckConnect.ShowToast(getActivity(),"Hết dữ liệu");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }



    public class mHandler extends Handler {
        // function dùng để quản lý những Theard gửi lên
        @Override
        public void handleMessage(Message msg) {
            // những giá trị gửi lên thông qua msg
            switch (msg.what){
                case 0:
                    // Theard gửi lên biến = 0 thì add progessbar vào lisview
                    listViewMonAn.addFooterView(footerview);
                    break;
                case 1:
                    // Theard gửi lên = 1 thì cập nhật đổ dữ liệu lên
                    // page + 1 trước rồi mới thực hiên function
                    GetData(++page);
                    // trả về trạng thái chưa load dữ liệu vì đã load xong rồi
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }

    }
    // Các nhân viên là các dạng luồng (Threard)
    public  class ThreadData extends Thread{
        // để cho thực hiện các luồng gọi run
        @Override
        public void run() {
            // gửi tin nhắn = 0  trước
            mHandler.sendEmptyMessage(0);
            try {
                // cho nó ngủ 1s
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // sau 3s gửi tiếp tin nhắn
            // obtainMessage liên kết các Theard và Handler
            // muốn liên kết tiếp tục thì gọi obtainMessage
            // gửi tiếp cho Handler giá trị 1
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
