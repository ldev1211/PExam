package ldev.ptithcm.pexam.model;

public class Subject {
    private String codeSubj;

    public Subject(String codeSubj, String nameSub, int numPart) {
        this.codeSubj = codeSubj;
        this.nameSub = nameSub;
        this.numPart = numPart;
    }

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

    public String getCodeSubj() {
        return codeSubj;
    }

    public void setCodeSubj(String codeSubj) {
        this.codeSubj = codeSubj;
    }
}
