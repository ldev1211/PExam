package com.example.pexam.model;

public class Exam {
    private String nameSubj;
    private String part;
    private int maxNumQuest;
    private int time;
    private int numAnsRight;

    public String getNameSubj() {
        return nameSubj;
    }

    public void setNameSubj(String nameSubj) {
        this.nameSubj = nameSubj;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public int getMaxNumQuest() {
        return maxNumQuest;
    }

    public void setMaxNumQuest(int maxNumQuest) {
        this.maxNumQuest = maxNumQuest;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNumAnsRight() {
        return numAnsRight;
    }

    public void setNumAnsRight(int numAnsRight) {
        this.numAnsRight = numAnsRight;
    }

    public Exam(String nameSubj, String part, int maxNumQuest, int time, int numAnsRight) {
        this.nameSubj = nameSubj;
        this.part = part;
        this.maxNumQuest = maxNumQuest;
        this.time = time;
        this.numAnsRight = numAnsRight;
    }
}
