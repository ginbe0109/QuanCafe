package com.example.user.quancafe.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.model.LoaiMonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 22/01/2018.
 */
public class LoaiMonAnAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiMonAn> arrLoaiMon;
    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public LoaiMonAnAdapter(Context context, ArrayList<LoaiMonAn> arrLoaiMon) {
        this.context = context;
        this.arrLoaiMon = arrLoaiMon;
    }

    @Override
    public int getCount() {
        return arrLoaiMon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrLoaiMon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class ViewHolder{
        TextView txtloaimon;
        ImageView imgloaimon;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        // Nếu chưa có dữ liệu
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            // get service là layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_loaimonan,null);
            // ánh xạ tới từng dòng
            viewHolder.txtloaimon = (TextView) convertView.findViewById(R.id.textviewloaimonan);
            viewHolder.imgloaimon = (ImageView) convertView.findViewById(R.id.imageloaimonan);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        } // nếu đã có dữ liệu thì getTag
        // lấy dữ liệu từ trong mảng
        // gọi lại khuôn là menu
        LoaiMonAn loaiMonAn = (LoaiMonAn) getItem(position);
        viewHolder.txtloaimon.setText(loaiMonAn.getTenloai());
        Picasso.with(context).load(loaiMonAn.getHinhloai())
                .placeholder(R.drawable.no_image_available)
                .into(viewHolder.imgloaimon);
        return convertView;
    }


}

