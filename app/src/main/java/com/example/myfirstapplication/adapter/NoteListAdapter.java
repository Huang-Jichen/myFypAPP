package com.example.myfirstapplication.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfirstapplication.R;
import com.example.myfirstapplication.pojo.NoteInfo;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyHolder> {

    private List<NoteInfo> myNotesInfos = new ArrayList<>();

    public NoteListAdapter(List<NoteInfo> list){
        this.myNotesInfos = list;
    }

    public void setNoteListAdapter(List<NoteInfo> list){
        this.myNotesInfos = list;
        //这句话不能少
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //绑定数据
        NoteInfo mynoteInfo = myNotesInfos.get(position);
        //设置数据

        holder.Title.setText(mynoteInfo.getTitle());
        holder.UserName.setText(mynoteInfo.getUserName());
        holder.LikesNumber.setText("❤" + mynoteInfo.getLikesNumber());

        String noteImg_path = mynoteInfo.getImg();
        Bitmap bitmap = BitmapFactory.decodeFile(noteImg_path);
        holder.IMG.setImageBitmap(bitmap);

        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != myOnItemClickListener){
                    //如果非空，回调当前数据
                    myOnItemClickListener.onClickItem(mynoteInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myNotesInfos.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        TextView Title;
        TextView UserName;
        TextView LikesNumber;
        ImageView IMG;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //初始化控件
            Title = itemView.findViewById(R.id.TextView_title);
            UserName = itemView.findViewById(R.id.textView_UserName);
            LikesNumber = itemView.findViewById(R.id.textView_likesNumber);
            IMG = itemView.findViewById(R.id.imageView9);
        }
    }

    private OnItemClickListener myOnItemClickListener;

    public void setMyOnItemClickListener(NoteListAdapter.OnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    //创建接口实现回调
    public interface OnItemClickListener {
        void onClickItem(NoteInfo NoteInfo);
    }

}
