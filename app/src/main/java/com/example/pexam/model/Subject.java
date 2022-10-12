package com.example.pexam.model;

public class Subject {
    private String nameSub;
    private int numPart;

    public String getNameSub() {
        return nameSub;
    }

    public void setNameSub(String nameSub) {
        this.nameSub = nameSub;
    }

    public int getNumPart() {
        return numPart;
    }

    public void setNumPart(int numPart) {
        this.numPart = numPart;
    }

    public Subject(String nameSub, int numPart) {
        this.nameSub = nameSub;
        this.numPart = numPart;
    }
}
