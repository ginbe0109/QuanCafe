package com.example.user.quancafe.activity.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.model.MonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 26/01/2018.
 */

public class DanhSachMonAnAdapter extends BaseAdapter {
    ArrayList<MonAn> monAnArrayList;
    Context context;

    public DanhSachMonAnAdapter(ArrayList<MonAn> monAnArrayList, Context context) {
        this.monAnArrayList = monAnArrayList;
        this.context = context;
    }

    public DanhSachMonAnAdapter() {
    }

    @Override
    public int getCount() {
        return monAnArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public ImageView imageMonAn;
        public TextView textViewTenMon, textViewMoTa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        // nếu chưa có dữ liệu thì khởi tạo
        // ngược lại get lại
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_dsmonan,null );
            viewHolder.textViewTenMon = (TextView) convertView.findViewById(R.id.texttenmon);
            viewHolder.textViewMoTa = (TextView) convertView.findViewById(R.id.textMoTamon);
            viewHolder.imageMonAn = (ImageView) convertView.findViewById(R.id.imagemonan);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MonAn monan = (MonAn) getItem(position);
        // gán dữ liệu lên layout
        viewHolder.textViewTenMon.setText(monan.getTenMon());
        // tạo mô tả gồm 2 dòng
        viewHolder.textViewMoTa.setMaxLines(2);
        // mô tả quá dài thì được thay bằng 3 chấm
        viewHolder.textViewTenMon.setEllipsize(TextUtils.TruncateAt.END);
        // gán dữ liệu cho mô tả
        viewHolder.textViewMoTa.setText(monan.getMotaMon());
        Picasso.with(context).load(monan.getHinhMon())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.err)
                .into(viewHolder.imageMonAn);
        return convertView;
    }
}
