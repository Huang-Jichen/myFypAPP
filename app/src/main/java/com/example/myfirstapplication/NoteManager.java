package com.example.myfirstapplication;

import java.util.ArrayList;
import java.util.List;

public class NoteManager {
    private static NoteManager instance;
    private List<String> notes;

    private NoteManager() {
        notes = new ArrayList<>();
    }

    public static synchronized NoteManager getInstance() {
        if (instance == null) {
            instance = new NoteManager();
        }
        return instance;
    }

    public void addLatestNote(String newNoteContent) {
        if (!notes.contains(newNoteContent)) { // 检查是否已存在相同的笔记
            notes.add(0, newNoteContent); // 将新笔记添加到列表的开头
        }
    }

    // 检查笔记列表中是否已经存在指定的内容
    public boolean containsNote(String noteContent) {
        for (String note : notes) {
            if (note != null && note.equals(noteContent)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getNotes() {
        return notes;
    }
}