package com.example.picturebrowser.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.picturebrowser.Application.MyApplication;
import com.example.picturebrowser.MyClass.Photo;
import com.example.picturebrowser.R;

import java.util.List;

/**
 * Created by 铖哥 on 2017/5/19.
 */

public class MainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private  List<Photo> resource ;
    private Context context;

    public MainListAdapter(Context context , List<Photo> resource) {
        this.resource = resource;
        this.context = context;
        Log.e("size",resource.size()+"");

    }

    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemLongClickListener{
         void onItemLongClick(View v , int postion);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener l){
        onItemLongClickListener = l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_recycler_item,parent,false);
        final MyViewHolder holder = new MyViewHolder(view);


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v,holder.getAdapterPosition());
                }
                return true;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Glide.with(context).
                load(resource.get(position).getPath()).thumbnail( 0.001f ).
                into(((MyViewHolder)holder).imageView);
        ((MyViewHolder) holder).checkBox.setChecked(resource.get(position).isChecked());
    }




    @Override
    public int getItemCount() {
        return resource.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        CheckBox checkBox;
        public MyViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image_photo);
            checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        }
    }

    public List<Photo> getResource() {
        return resource;
    }
}
