package ldev.ptithcm.pexam.model;

import java.io.Serializable;

public class AnsState implements Serializable {
    private String contentAns;
    private boolean isHide = false;
    private boolean isChoose = false;

    public AnsState(String contentAns, boolean isHide, boolean isChoose) {
        this.contentAns = contentAns;
        this.isHide = isHide;
        this.isChoose = isChoose;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getContentAns() {
        return contentAns;
    }

    public void setContentAns(String contentAns) {
        this.contentAns = contentAns;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }
}
