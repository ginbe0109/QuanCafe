package com.example.user.quancafe.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.model.Ban;

import java.util.ArrayList;

/**
 * Created by User on 13/07/2018.
 */

public class BanAdapter extends BaseAdapter {
    Context context;
    ArrayList<Ban> arrayBan;

    public BanAdapter(Context context, ArrayList<Ban> arrayBan) {
        this.context = context;
        this.arrayBan = arrayBan;
    }

    @Override
    public int getCount() {
        return arrayBan.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayBan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txtBan;
        ImageView imgBan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_gridview_ds_ban,null);
            // ánh xạ tới từng dòng
            viewHolder.txtBan = (TextView) convertView.findViewById(R.id.dong_textview_girdview_ds_Ban);
            viewHolder.imgBan = (ImageView) convertView.findViewById(R.id.dong_imge_girdview_ds_ban);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Ban ban = (Ban) getItem(position);
        viewHolder.txtBan.setText("Bàn "+ban.getStt());
        if(ban.getTrangthai() !=0){
            // bàn đã đặt món
            viewHolder.imgBan.setBackgroundColor(Color.BLACK);
        }else{
            viewHolder.imgBan.setBackgroundColor(Color.WHITE);

        }
        //viewHolder.imgBan.setScaleType(ImageView.ScaleType.CENTER_CROP);
       // viewHolder.imgBan.setPadding(8, 8, 8, 8);
       // viewHolder.imgBan.setLayoutParams(new ViewGroup.LayoutParams(85, 85));

        return convertView;
    }
}
