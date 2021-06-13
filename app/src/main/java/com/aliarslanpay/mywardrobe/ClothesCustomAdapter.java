package com.aliarslanpay.mywardrobe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ClothesCustomAdapter extends BaseAdapter {
    ArrayList<byte[]> typeImageList;
    private Context context;
    private LayoutInflater layoutInflater;

    public ClothesCustomAdapter(ArrayList<byte[]> typeImageList, Context context) {
        this.typeImageList = typeImageList;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return typeImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.clothes_row_items,parent, false);
        }
        ImageView imageClothes = convertView.findViewById(R.id.clothesImageView);
        byte[] bytes = typeImageList.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        imageClothes.setImageBitmap(bitmap);

        return convertView;
    }
}
