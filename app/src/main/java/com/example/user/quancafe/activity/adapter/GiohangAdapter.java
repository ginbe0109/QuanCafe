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
import com.example.user.quancafe.activity.activity.MainActivity;
import com.example.user.quancafe.activity.fragment.DasboardFragment;
import com.example.user.quancafe.activity.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import static com.example.user.quancafe.R.id.btnvalues;


/**
 * Created by User on 07/08/2017.
 */

public class GiohangAdapter extends BaseAdapter {
    ArrayList<Giohang> arrayGioHang;
    Context context;

    public GiohangAdapter(ArrayList<Giohang> arrayGioHang, Context context) {
        this.arrayGioHang = arrayGioHang;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public ImageView imageGioHang;
        public TextView textTengiohang, textGiagiohang;
        public Button btnminus, btnvalues, btnplus;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.imageGioHang = (ImageView) convertView.findViewById(R.id.imageGioHang);
            viewHolder.textTengiohang = (TextView) convertView.findViewById(R.id.textTenSPgiohang);
            viewHolder.textGiagiohang = (TextView) convertView.findViewById(R.id.textGiaSPgiohang);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.btnminus);
            viewHolder.btnvalues = (Button) convertView.findViewById(btnvalues);
            viewHolder.btnplus = (Button) convertView.findViewById(R.id.btnplus);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.textTengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textGiagiohang.setText(decimalFormat.format(giohang.getGiasp())+ " Đ");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.err)
                .into(viewHolder.imageGioHang);
        viewHolder.btnvalues.setText(String.valueOf(giohang.getSoluongsp()));
        // lấy số lượng
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        // bắt sự kiện nhấn plus mà số lượng sảm phẩm vượt quá 10 thì cho ẩn plus , hiện minus
        if(sl >= 10){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
        }else if(sl <= 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }else if(sl >= 1){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        // xét sự kiện cho action plus
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt( finalViewHolder.btnvalues.getText().toString()) +1;
                // số lượng hiện tại
                int slht = MainActivity.mangGiohang.get(position).getSoluongsp();
                // giá hiện tại
                long giaht = MainActivity.mangGiohang.get(position).getGiasp();
                // số lượng ht: 3 --> có giá hiện tại: 1000
                // 4 --> ?
                long giamoinhat = (giaht * slmoinhat) / slht;
                // set lại giá và số lượng sản phẩm của giỏ hàng
                MainActivity.mangGiohang.get(position).setGiasp(giamoinhat);
                MainActivity.mangGiohang.get(position).setSoluongsp(slmoinhat);
                // gán lên cho giá của giỏ hàng
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.textGiagiohang.setText(decimalFormat.format(giamoinhat)+ " Đ");
                // update lại tổng tiền của giỏ hàng
                DasboardFragment.EvenUtil();
                // bắt sự kiện số lượng là 10 (chọn 10 lần cái sản phẩm)
                // slmoinhat > 9 vì mỗi lần click đã cộng thêm 1 nên lần thứ 9 số lượng là 10
                if(slmoinhat > 9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));

                }else{
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }

            }
        });

        // bắt sự kiên cho minus
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt( finalViewHolder.btnvalues.getText().toString()) -1;
                // số lượng hiện tại
                int slht = MainActivity.mangGiohang.get(position).getSoluongsp();
                // giá hiện tại
                long giaht = MainActivity.mangGiohang.get(position).getGiasp();
                // số lượng ht: 3 --> có giá hiện tại: 1000
                // 4 --> ?
                long giamoinhat = (giaht * slmoinhat) / slht;
                // set lại giá và số lượng sản phẩm của giỏ hàng
                MainActivity.mangGiohang.get(position).setGiasp(giamoinhat);
                MainActivity.mangGiohang.get(position).setSoluongsp(slmoinhat);
                // gán lên cho giá của giỏ hàng
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.textGiagiohang.setText(decimalFormat.format(giamoinhat)+ " Đ");
                // update lại tổng tiền của giỏ hàng
                DasboardFragment.EvenUtil();
                // bắt sự kiện số lượng là 10 (chọn 10 lần cái sản phẩm)
                if(slmoinhat < 2){
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));

                }else{
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });


        return convertView;
    }

    public ArrayList<Giohang> getArrayGioHang() {
        return arrayGioHang;
    }

    public void setArrayGioHang(ArrayList<Giohang> arrayGioHang) {
        this.arrayGioHang = arrayGioHang;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
