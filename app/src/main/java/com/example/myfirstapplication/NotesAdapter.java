package com.example.myfirstapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<String> notes;

    //构造函数
    public NotesAdapter(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        //绑定数据到ViewHolder
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        //返回数据集大小
        return notes.size();
    }

    public void updateNotes(List<String> newNotes) {
        //notes.clear();
        notes.addAll(newNotes);
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        // 定义我的ViewHolder内的控件
        public NoteViewHolder(View itemView) {
            super(itemView);
            // 初始化控件，例如：
            // textView = itemView.findViewById(R.id.textView);
        }

        public void bind(String note) {
            // 将数据绑定到ViewHolder的控件上
            TextView tvNote = itemView.findViewById(R.id.tvNote);
            tvNote.setText(note);
        }
    }
}

