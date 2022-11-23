package com.example.pexam.model;

public class Docs {
    private String contentQuestion;
    private String ans1,ans2,ans3,ans4,ansRight;
    private boolean isNote;

    @Override
    public String toString() {
        return "Docs{" +
                "contentQuestion='" + contentQuestion + '\'' +
                ", ans1='" + ans1 + '\'' +
                ", ans2='" + ans2 + '\'' +
                ", ans3='" + ans3 + '\'' +
                ", ans4='" + ans4 + '\'' +
                ", ansRight='" + ansRight + '\'' +
                ", isNote=" + isNote +
                '}';
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public void setContentQuestion(String contentQuestion) {
        this.contentQuestion = contentQuestion;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getAnsRight() {
        return ansRight;
    }

    public void setAnsRight(String ansRight) {
        this.ansRight = ansRight;
    }

    public boolean isNote() {
        return isNote;
    }

    public void setNote(boolean note) {
        isNote = note;
    }

    public Docs(String contentQuestion, String ans1, String ans2, String ans3, String ans4, String ansRight, boolean isNote) {
        this.contentQuestion = contentQuestion;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.ansRight = ansRight;
        this.isNote = isNote;
    }
}
