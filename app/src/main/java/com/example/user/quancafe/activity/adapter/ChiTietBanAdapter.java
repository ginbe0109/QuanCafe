package com.example.user.quancafe.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.fragment.DetailTableFragment;
import com.example.user.quancafe.activity.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.user.quancafe.R.id.btnvalues;

/**
 * Created by User on 20/07/2018.
 */

public class ChiTietBanAdapter extends BaseAdapter {
    ArrayList<Giohang> arrayGioHangCTBAN;
    Context context;
    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListnerPlus(int position, Giohang value);
        public void onButtonClickListnerMinus(int position, Giohang value);
    }
    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    public ChiTietBanAdapter(ArrayList<Giohang> arrayGioHangCTBAN, Context context) {
        this.arrayGioHangCTBAN = arrayGioHangCTBAN;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayGioHangCTBAN.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGioHangCTBAN.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder{
        public ImageView imageViewGioHangCTBAN;
        public TextView textViewTenGoiHangCTBAN, textViewGiaGioHangCTBAN;
        public Button btnminusCTBAN, btnvaluesCTBAN, btnplusCTBAN;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.imageViewGioHangCTBAN = (ImageView) convertView.findViewById(R.id.imageGioHang);
            viewHolder.textViewTenGoiHangCTBAN = (TextView) convertView.findViewById(R.id.textTenSPgiohang);
            viewHolder.textViewGiaGioHangCTBAN = (TextView) convertView.findViewById(R.id.textGiaSPgiohang);
            viewHolder.btnminusCTBAN = (Button) convertView.findViewById(R.id.btnminus);
            viewHolder.btnvaluesCTBAN = (Button) convertView.findViewById(btnvalues);
            viewHolder.btnplusCTBAN = (Button) convertView.findViewById(R.id.btnplus);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Giohang giohang = (Giohang) getItem(position);
        viewHolder.textViewTenGoiHangCTBAN.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewGiaGioHangCTBAN.setText(decimalFormat.format(giohang.getGiasp())+ " Đ");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.err)
                .into(viewHolder.imageViewGioHangCTBAN);
        viewHolder.btnvaluesCTBAN.setText(String.valueOf(giohang.getSoluongsp()));
        // lấy số lượng
        int sl = Integer.parseInt(viewHolder.btnvaluesCTBAN.getText().toString());
        // bắt sự kiện nhấn plus mà số lượng sảm phẩm vượt quá 10 thì cho ẩn plus , hiện minus
        if(sl >= 10){
            viewHolder.btnminusCTBAN.setVisibility(View.VISIBLE);
            viewHolder.btnplusCTBAN.setVisibility(View.INVISIBLE);
        }else if(sl <= 1){
            viewHolder.btnminusCTBAN.setVisibility(View.INVISIBLE);
            viewHolder.btnplusCTBAN.setVisibility(View.VISIBLE);
        }else if(sl >= 1){
            viewHolder.btnminusCTBAN.setVisibility(View.VISIBLE);
            viewHolder.btnplusCTBAN.setVisibility(View.VISIBLE);
        }

         //xét sự kiện cho actuon plus
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplusCTBAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvaluesCTBAN.getText().toString())+1;
               // finalViewHolder.btnvaluesCTBAN.setText(slmoinhat+"");
                //số lượng hiện tại
                int slht = arrayGioHangCTBAN.get(position).getSoluongsp();
                // giá hiện tại
                long giaht = arrayGioHangCTBAN.get(position).getGiasp();
                long giamoinhat = (giaht * slmoinhat) / slht;
                // set lại giá và số lượng sản phẩm của giỏ hàng
                arrayGioHangCTBAN.get(position).setGiasp(giamoinhat);
                arrayGioHangCTBAN.get(position).setSoluongsp(slmoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.textViewGiaGioHangCTBAN.setText(decimalFormat.format(giamoinhat)+ " Đ");
                // update lại tổng tiền của giỏ hàng
                EvenUtilChiTietBan();
                // bắt sự kiện số lượng là 10 (chọn 10 lần cái sản phẩm)
                // slmoinhat > 9 vì mỗi lần click đã cộng thêm 1 nên lần thứ 9 số lượng là
                if(slmoinhat > 9){
                    finalViewHolder.btnplusCTBAN.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminusCTBAN.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvaluesCTBAN.setText(String.valueOf(slmoinhat));

                }else{
                    finalViewHolder.btnplusCTBAN.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminusCTBAN.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvaluesCTBAN.setText(String.valueOf(slmoinhat));
                }
                // set on click detailtable
                if (customListner != null) {
                    customListner.onButtonClickListnerPlus(position,giohang);
                }

            }
        });
        viewHolder.btnminusCTBAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvaluesCTBAN.getText().toString())-1;
                // finalViewHolder.btnvaluesCTBAN.setText(slmoinhat+"");
                //số lượng hiện tại
                int slht = arrayGioHangCTBAN.get(position).getSoluongsp();
                // giá hiện tại
                long giaht = arrayGioHangCTBAN.get(position).getGiasp();
                long giamoinhat = (giaht * slmoinhat) / slht;
                // set lại giá và số lượng sản phẩm của giỏ hàng
                arrayGioHangCTBAN.get(position).setGiasp(giamoinhat);
                arrayGioHangCTBAN.get(position).setSoluongsp(slmoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.textViewGiaGioHangCTBAN.setText(decimalFormat.format(giamoinhat)+ " Đ");
                // update lại tổng tiền của giỏ hàng
                EvenUtilChiTietBan();
                // bắt sự kiện số lượng là 10 (chọn 10 lần cái sản phẩm)
                // slmoinhat > 9 vì mỗi lần click đã cộng thêm 1 nên lần thứ 9 số lượng là
                if(slmoinhat < 2){
                    finalViewHolder.btnplusCTBAN.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminusCTBAN.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnvaluesCTBAN.setText(String.valueOf(slmoinhat));

                }else{
                    finalViewHolder.btnplusCTBAN.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminusCTBAN.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvaluesCTBAN.setText(String.valueOf(slmoinhat));
                }
                // set on click detailtable
                if (customListner != null) {
                    customListner.onButtonClickListnerMinus(position,giohang);
                }
            }
        });

        return convertView;
    }
    //tính tổng tiền
    private void EvenUtilChiTietBan() {
        long tongtien = 0;
        for(int i = 0; i< arrayGioHangCTBAN.size(); i++){
            tongtien += (arrayGioHangCTBAN.get(i).getGiasp());
        }
        // định dạng lại tổng tiền
        DecimalFormat decimal = new DecimalFormat("###,###,###");
        DetailTableFragment.tongtienCTBAN.setText(decimal.format(tongtien)+" Đ");

    }

}
