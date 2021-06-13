package com.aliarslanpay.mywardrobe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class eskiWardrobeRecyclerAdapter extends RecyclerView.Adapter<eskiWardrobeRecyclerAdapter.PostHolder> {

    ArrayList<String> drawerNameList;
    ArrayList<String> typeNameList;
    ArrayList<byte[]> typeImageList;

    public eskiWardrobeRecyclerAdapter(ArrayList<String> typeNameList, ArrayList<String> drawerNameList, ArrayList<byte[]> typeImageList) {
        this.typeNameList = typeNameList;
        this.drawerNameList = drawerNameList;
        this.typeImageList = typeImageList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        holder.textView.setText(drawerNameList.get(position));

        byte[] bytes = typeImageList.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        holder.imageView.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {

        return drawerNameList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;


        public PostHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.drawerType);
            imageView = itemView.findViewById(R.id.drawerImage);

        }
    }
}
