package com.example.myfirstapplication.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NoteInfo implements Serializable {
    private int id;
    private String title;
    private String mainBody;
    private String userName;
    private int likesNumber;
    private int savesNumber;
    private int commentsNumber;
    private String img;
    private String createdAt; // 创建时间
    private String updatedAt; // 修改时间
    public static NoteInfo NoteInfos;

    public NoteInfo() {
    }

    public NoteInfo(int id, String title, String mainBody, String userName, int likesNumber, int savesNumber, int commentsNumber, String img, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.mainBody = mainBody;
        this.userName = userName;
        this.likesNumber = likesNumber;
        this.savesNumber = savesNumber;
        this.commentsNumber = commentsNumber;
        this.img = img;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static NoteInfo getNoteInfos() {
        return NoteInfos;
    }

    public static void setNoteInfos(NoteInfo noteInfos) {
        NoteInfos = noteInfos;
    }

    public int getSavesNumber() {
        return savesNumber;
    }

    public void setSavesNumber(int savesNumber) {
        this.savesNumber = savesNumber;
    }

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMainBody() {
        return mainBody;
    }

    public void setMainBody(String mainBody) {
        this.mainBody = mainBody;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    public boolean isEmpty(){
        return (userName == null || userName.isEmpty())
                ||(mainBody == null || mainBody.isEmpty())
                ||(title == null || title.isEmpty())
                || id == 0
                || likesNumber == 0
                || img == null;
    }
}
