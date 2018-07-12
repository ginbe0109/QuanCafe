package com.example.user.quancafe.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.activity.MainActivity;
import com.example.user.quancafe.activity.model.Giohang;
import com.example.user.quancafe.activity.model.MonAn;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;


public class DetailFoodFragment extends Fragment {
    private View view;
    private Bundle bundle;
    private ImageView imageChiTiet;
    private TextView txtTenChiTiet, txtGiaChiTiet, txtMoTaChitiet;
    private Spinner spinnerChiTiet;
    private Button btnThemGioHang;
    String tenMon;
    String montaMon;
    String hinhMon;
    float giaMon = 0;
    MonAn mon;
    int idMon;
    int idLoaiMon;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_food, container, false);
        AnhXa();
        bundle = this.getArguments();
        if (bundle != null) {

            // Danh sách món ăn theo từng loại
            // lấy id loại

            mon = (MonAn) bundle.getSerializable("thongtinmon");;
            // get thông tin món ăn
            GetInformationChiTietSP();
            // Spinner
            CatchEvenSpinner();
            // sự kiện thêm món vào hóa đơn
            EvenButton();


        }

        return  view;
    }

    private void EvenButton() {
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nếu đã có dữ liệu trong giỏ hàng
                if(MainActivity.mangGiohang.size() > 0){
                    // lấy số lượng trong Spinner
                    int sl = Integer.parseInt(spinnerChiTiet.getSelectedItem().toString());
                    boolean exists = false;
                    // kiểm tra coi có thằng nào update số lại không
                    // duyệt mảng
                    for(int i = 0; i < MainActivity.mangGiohang.size(); i++ ){
                        // nếu ID == nhau thì cộng 2 số lượng trong mảng và số lượng mới được chọn
                        if(MainActivity.mangGiohang.get(i).getIdsp() == idMon){
                            MainActivity.mangGiohang.get(i).setSoluongsp(MainActivity.mangGiohang.get(i).soluongsp + sl);
                            // nếu sản phẩm lớn hơn 10 thì set lại số luongj = 10
                            if(MainActivity.mangGiohang.get(i).getSoluongsp() >= 10){
                                MainActivity.mangGiohang.get(i).setSoluongsp(10);
                            }
                            // set lại giá cho sản phẩm
                            MainActivity.mangGiohang.get(i).setGiasp((long) (giaMon * MainActivity.mangGiohang.get(i).getSoluongsp()));
                            exists = true;
                        }
                    }
                    // nếu như không tìm thấy id trùng nhau thì không cập nhật số lượng
                    // Tạo giỏ mới thêm vào mảng
                    if(exists == false){
                        // chưa có dữ liệu trong giỏ hàng
                        // lấy số lượng trong Spinner
                        int soluong = Integer.parseInt(spinnerChiTiet.getSelectedItem().toString());
                        long Giamoi = (long) (soluong * giaMon);
                        // thêm và giỏi hàng
                        MainActivity.mangGiohang.add(new Giohang(idMon,tenMon,Giamoi,hinhMon,soluong));
                    }
                }else{
                    // chưa có dữ liệu trong giỏ hàng
                    // lấy số lượng trong Spinner
                    int soluong = Integer.parseInt(spinnerChiTiet.getSelectedItem().toString());
                    long Giamoi = (long) (soluong * giaMon);
                    // thêm và giỏi hàng
                    MainActivity.mangGiohang.add(new Giohang(idMon,tenMon,Giamoi,hinhMon,soluong));
                }
                // chuyển qua fragment Giỏ hàng
                DasboardFragment gioHang = new DasboardFragment();
                getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, gioHang).commit();
            }
        });
    }

    private void CatchEvenSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterSpinner = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_dropdown_item_1line, soluong);
        spinnerChiTiet.setAdapter(adapterSpinner);
    }

    private void GetInformationChiTietSP() {
        idMon = mon.getMaMon();
        idLoaiMon = mon.getMaloaiMon();
        tenMon = mon.getTenMon();
        montaMon = mon.getMotaMon();
        hinhMon = mon.getHinhMon();
        giaMon = mon.getDongia();

        txtTenChiTiet.setText(tenMon);
        txtMoTaChitiet.setText(montaMon);
        Picasso.with(getActivity()).load(hinhMon)
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.err)
                .into(imageChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaChiTiet.setText("Giá: "+decimalFormat.format(giaMon)+" Đ");


    }

    private void AnhXa() {
        imageChiTiet = (ImageView) view.findViewById(R.id.imageChiTietSP);
        txtTenChiTiet = (TextView) view.findViewById(R.id.textTenChiTietSP);
        txtGiaChiTiet = (TextView) view.findViewById(R.id.textGiaChiTietSP);
        txtMoTaChitiet = (TextView) view.findViewById(R.id.textMoTaChiTietSP);
        spinnerChiTiet = (Spinner) view.findViewById(R.id.spinnerChiTietSP);
        btnThemGioHang = (Button) view.findViewById(R.id.buttonThemGioHang);


    }


}
