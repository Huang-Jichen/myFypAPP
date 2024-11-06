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

    public void addLatestNote(String note) {
        notes.add(note);
    }

    public List<String> getNotes() {
        return notes;
    }
}
