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
    public class ViewHolder{
        public ImageView imageViewGioHangCTBAN;
        public TextView textViewTenGoiHangCTBAN, textViewGiaGioHangCTBAN;
        public Button btnminusCTBAN, btnvaluesCTBAN, btnplusCTBAN;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        Giohang giohang = (Giohang) getItem(position);
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

        return convertView;
    }
}
