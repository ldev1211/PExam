package ldev.ptithcm.pexam.model;

import java.io.Serializable;

public class Question implements Serializable {
    private String contentQuestion;
    private String linkImgQuestion;
    private AnsState ans1,ans2,ans3,ans4;
    private String ansRight;
    private boolean isChoose;
    private boolean isNote;

    public boolean isNote() {
        return isNote;
    }

    public void setNote(boolean note) {
        isNote = note;
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public void setContentQuestion(String contentQuestion) {
        this.contentQuestion = contentQuestion;
    }

    public String getLinkImgQuestion() {
        return linkImgQuestion;
    }

    public void setLinkImgQuestion(String linkImgQuestion) {
        this.linkImgQuestion = linkImgQuestion;
    }

    public AnsState getAns1() {
        return ans1;
    }

    public void setAns1(AnsState ans1) {
        this.ans1 = ans1;
    }

    public AnsState getAns2() {
        return ans2;
    }

    public void setAns2(AnsState ans2) {
        this.ans2 = ans2;
    }

    public AnsState getAns3() {
        return ans3;
    }

    public void setAns3(AnsState ans3) {
        this.ans3 = ans3;
    }

    public AnsState getAns4() {
        return ans4;
    }

    public void setAns4(AnsState ans4) {
        this.ans4 = ans4;
    }

    public String getAnsRight() {
        return ansRight;
    }

    public void setAnsRight(String ansRight) {
        this.ansRight = ansRight;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public Question(String contentQuestion, String linkImgQuestion, AnsState ans1, AnsState ans2, AnsState ans3, AnsState ans4, String ansRight, boolean isChoose, boolean isNote) {
        this.contentQuestion = contentQuestion;
        this.linkImgQuestion = linkImgQuestion;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.ansRight = ansRight;
        this.isChoose = isChoose;
        this.isNote = isNote;
    }
}
