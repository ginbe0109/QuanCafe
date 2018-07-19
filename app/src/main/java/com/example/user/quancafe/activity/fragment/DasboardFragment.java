package com.example.user.quancafe.activity.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.activity.LogInActivity;
import com.example.user.quancafe.activity.activity.MainActivity;
import com.example.user.quancafe.activity.adapter.BanAdapter;
import com.example.user.quancafe.activity.adapter.GiohangAdapter;
import com.example.user.quancafe.activity.model.Ban;
import com.example.user.quancafe.activity.ultil.CheckConnect;
import com.example.user.quancafe.activity.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.user.quancafe.activity.ultil.CheckConnect.ShowToast;

/**
 * Created by User on 27/06/2018.
 */

public class DasboardFragment extends Fragment {
    private View view;
    ListView listViewGioHang;
    TextView txtthongbao;
    static TextView txttongtiengioihang;
    Button btntieptucmua, btnthanhtoan;
    GiohangAdapter giohangAdapter;

    GridView gridViewBan;
    BanAdapter banAdapter;
    ArrayList<Ban> arrayListBan;

//    Hoadon hoadon;'
    //static int stthdon;
    int stthdon;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard,null);

        Anhxa();
        // get danh sách bàn
        GetDataBan();
        CheckData();
        EvenUtil();
        CatchOnItemListView();
        EveenButton();

        return view;
    }

    private void EveenButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // trong giỏ hàng có sản phẩm thì mới thanh toán
                if(MainActivity.mangGiohang.size() > 0){
                    DialogDatMon();
                }else{
                    ShowToast(getActivity(),"Giỏ hàng của bạn chưa có sản phẩm");
                }
            }
        });
    }

    private void DialogDatMon() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu_ds_ban);
        dialog.setCanceledOnTouchOutside(false);
        gridViewBan = dialog.findViewById(R.id.gridView_dsBan);
        gridViewBan.setAdapter(banAdapter);
        ActionClickGridView(gridViewBan,dialog);
        dialog.show();

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

//    private int insertTriGiaHoadon(final float trigia){


//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanThemHoaDon, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String stthd) {
//                 stthdon = Integer.parseInt(stthd);
//                CheckConnect.ShowToast(getActivity(),stthdon+"");
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("trigia",String.valueOf(trigia));
//                return params;
//            }
//        };
//
//        requestQueue.add(stringRequest);

//    }

    private void ActionClickGridView(final GridView gridViewBan, final Dialog dialog) {
        gridViewBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //Inflate the fragment
                // chuyển qua fragment chi tiết bàn
//                DetailTableFragment fragment = new DetailTableFragment();
//                Bundle thongtinBan = new Bundle();
//                thongtinBan.putSerializable("thongtinban", arrayListBan.get(position));
//                fragment.setArguments(thongtinBan);
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, fragment).commit();
//                dialog.dismiss();


                // nếu bàn trống thì tạo hóa đơn mới
                // ngược lại không tạo
                if(arrayListBan.get(position).getTrangthai() == 0){
                    CheckConnect.ShowToast(getActivity(),"bàn trống");
                    // tạo hóa đơn
                    RequestQueue requestQueueHoaDon = Volley.newRequestQueue(getActivity().getApplicationContext());
                    StringRequest stringRequestHoaDon = new StringRequest(Request.Method.POST, Server.DuongdanThemHoaDon, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String stthd) {
                            // số thứ tự hóa đơn
                            stthdon = Integer.parseInt(stthd);
                            if (stthdon > 0){
                                // get current time
                                Date time = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                final String currenttime = df.format(time);
                                //CheckConnect.ShowToast(getActivity().getApplicationContext(), LogInActivity.mand +" "+currenttime);

                                final Ban  ban = new Ban(arrayListBan.get(position).getStt(),1);
                                final int sttban = ban.getStt();
                                final int trangthai = ban.getTrangthai();
                                // update trạng thái của bàn
                                RequestQueue requsetQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanCapNhatBan, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String madonhang) {
                                        Log.d("madonhang",madonhang);
                                        // nếu có dữ liệu sẽ được gửi lên server
                                        if(Integer.parseInt(madonhang) > 0){
                                            // đảy dự liệu lên server
                                            // thêm ds món trong giỏ hàng lên server
                                            RequestQueue Queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                            StringRequest request = new StringRequest(Request.Method.POST, Server.DuongdanChitietdonhang, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    // nếu dữ liệu đã lên thành công thì trả về 1
                                                    if(response.equals("1")){
                                                        // làm sạch mảng gio hang
                                                        MainActivity.mangGiohang.clear();
                                                        CheckConnect.ShowToast(getActivity().getApplicationContext(),"Tên đã thêm dư liệu giỏ hàng thành công");

                                                        // chuyển qua fragment chi tiết bàn
                                                        DetailTableFragment fragment = new DetailTableFragment();
                                                        Bundle thongtinBan = new Bundle();
                                                        thongtinBan.putSerializable("thongtinban", arrayListBan.get(position));
                                                        fragment.setArguments(thongtinBan);
                                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, fragment).commit();
                                                        dialog.dismiss();
                                                    }else{
                                                        CheckConnect.ShowToast(getActivity().getApplicationContext(),"Dữ liệu giỏ hàng của bạn bị lỗi");
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    // tạo dư liệu và đổ vào JSONArray
                                                    JSONArray jsonArray = new JSONArray();
                                                    // đối với mỗi sản phẩm trong vỏ hàng thì chứa trong Object con
                                                    for(int i=0; i<MainActivity.mangGiohang.size(); i++){
                                                        JSONObject jsonObject = new JSONObject();
                                                        try {
                                                            // truyền dữ liệu vào trong Object
                                                            jsonObject.put("mand", LogInActivity.mand);
                                                            jsonObject.put("mamon",MainActivity.mangGiohang.get(i).getIdsp());
                                                            jsonObject.put("sttban",ban.getStt());
                                                            jsonObject.put("sohd",stthdon);
                                                            jsonObject.put("thoigian",currenttime);
                                                            jsonObject.put("trangthai",0);
                                                            jsonObject.put("soluong",MainActivity.mangGiohang.get(i).getSoluongsp());

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        // put(đưa) các jsonobject vào các jsonArray
                                                        jsonArray.put(jsonObject);
                                                    }
                                                    // chuyển jsonArray thành dạng chuỗi để đưa lên server
                                                    HashMap<String, String> param = new HashMap<String, String>();
                                                    // gửi lên đoạn chuỗi jsonArray
                                                    param.put("jsonChitietdonhang",jsonArray.toString());

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
                            params.put("trigia",String.valueOf(TongTienGio()));
                            return params;
                        }
                    };

                    requestQueueHoaDon.add(stringRequestHoaDon);
                }else{
                    CheckConnect.ShowToast(getActivity(),"có người");
                }



            }
        });
    }

    // sự kiện nhấn lâu listView
    private void CatchOnItemListView() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc xóa sản phẩm đã chọn?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nếu mảng không có sp thì cho hiện thông báo
                        if(MainActivity.mangGiohang.size() <= 0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else{
                            // ngược lại cho
                            // xóa phần từ trong mảng
                            MainActivity.mangGiohang.remove(position);
                            // cập nhật lại adapter và tổng tiền trong giỏ
                            giohangAdapter.notifyDataSetChanged();
                            EvenUtil();
                            // nếu xóa hồi đến hết thì cho thông báo hiện lên
                            if(MainActivity.mangGiohang.size() <= 0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else{
                                txtthongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EvenUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EvenUtil();
                    }
                });
                // cho AlertDilog
                builder.show();
                return true;
            }
        });
    }

    private void CheckData() {
        // nếu mangGiohang.size() không chứa sản phẩm nào thì cho thông báo hiện lên, ẩn listview
        if(MainActivity.mangGiohang.size() <= 0){
            giohangAdapter.notifyDataSetChanged(); // cập nhật adapter
            txtthongbao.setVisibility(View.VISIBLE); // hiện thông báo
            listViewGioHang.setVisibility(View.INVISIBLE); // ẩn listview

        }else{
            giohangAdapter.notifyDataSetChanged(); // cập nhật adapter
            txtthongbao.setVisibility(View.INVISIBLE); // ẩn thông báo
            listViewGioHang.setVisibility(View.VISIBLE); // hiện listview
        }

    }

    private void Anhxa() {
        listViewGioHang = (ListView) view.findViewById(R.id.listviewGioHang);
        txtthongbao = (TextView) view.findViewById(R.id.textThongbao);
        txttongtiengioihang = (TextView) view.findViewById(R.id.textTongTienGioHang);
        btnthanhtoan = (Button) view.findViewById(R.id.btnThanhtoangiohang);
        btntieptucmua = (Button) view.findViewById(R.id.btnTieptucgiohang);
        giohangAdapter = new GiohangAdapter(MainActivity.mangGiohang, getActivity());
        listViewGioHang.setAdapter(giohangAdapter);

        // ds bàn
        arrayListBan = new ArrayList<>();
        banAdapter = new BanAdapter(getActivity(),arrayListBan);




    }

    //tính tổng tiền
    public static void EvenUtil() {
        long tongtien = 0;
        for(int i = 0; i< MainActivity.mangGiohang.size(); i++){
            tongtien += MainActivity.mangGiohang.get(i).getGiasp();
        }
        // định dạng lại tổng tiền
        DecimalFormat decimal = new DecimalFormat("###,###,###");
        txttongtiengioihang.setText(decimal.format(tongtien)+" Đ");
    }


    public static float TongTienGio(){
        long tongtien = 0;
        for(int i = 0; i< MainActivity.mangGiohang.size(); i++){
            tongtien += MainActivity.mangGiohang.get(i).getGiasp();
        }
        return tongtien;
    }

}
