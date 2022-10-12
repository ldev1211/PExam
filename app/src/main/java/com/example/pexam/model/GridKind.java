package com.example.pexam.model;

public class GridKind {
    private String name;
    private int imgItem;
    private int numSubj;

    public int getNumSubj() {
        return numSubj;
    }

    public void setNumSubj(int numQuestion) {
        this.numSubj = numQuestion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgItem() {
        return imgItem;
    }

    public void setImgItem(int imgItem) {
        this.imgItem = imgItem;
    }

    public GridKind(String name, int imgItem, int numSubj) {
        this.name = name;
        this.imgItem = imgItem;
        this.numSubj = numSubj;
    }
}
