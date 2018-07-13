package com.example.user.quancafe.activity.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.activity.MainActivity;
import com.example.user.quancafe.activity.adapter.BanAdapter;
import com.example.user.quancafe.activity.adapter.GiohangAdapter;
import com.example.user.quancafe.activity.model.Ban;
import com.example.user.quancafe.activity.ultil.CheckConnect;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard,null);

        Anhxa();
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
                    CheckConnect.ShowToast(getActivity(),"Giỏ hàng của bạn chưa có sản phẩm");
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


        arrayListBan.add(new Ban(1,0));
        arrayListBan.add(new Ban(2,0));
        arrayListBan.add(new Ban(3,0));
        arrayListBan.add(new Ban(4,1));
        arrayListBan.add(new Ban(5,0));
        arrayListBan.add(new Ban(6,0));
        arrayListBan.add(new Ban(7,1));
        arrayListBan.add(new Ban(8,1));
        arrayListBan.add(new Ban(9,0));
        arrayListBan.add(new Ban(10,0));
        arrayListBan.add(new Ban(11,1));
        banAdapter.notifyDataSetChanged();

        gridViewBan.setAdapter(banAdapter);

       ActionClickGridView(gridViewBan,dialog);

        dialog.show();

    }

    private void ActionClickGridView(final GridView gridViewBan, final Dialog dialog) {
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
                dialog.dismiss();
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

}
