package ldev.ptithcm.pexam.model;

public class Exam {
    private String codeSubj;
    private String nameSubj;
    private String namePart;
    private String codePart;
    private int maxNumQuest;
    private int time;
    private int numAnsRight;

    @Override
    public String toString() {
        return "Exam{" +
                "codeSubj='" + codeSubj + '\'' +
                ", nameSubj='" + nameSubj + '\'' +
                ", namePart='" + namePart + '\'' +
                ", codePart='" + codePart + '\'' +
                ", maxNumQuest=" + maxNumQuest +
                ", time=" + time +
                ", numAnsRight=" + numAnsRight +
                '}';
    }

    public Exam(String codeSubj, String codePart, String namePart, int maxNumQuest, int time, int numAnsRight) {
        this.codeSubj = codeSubj;
        this.namePart = namePart;
        this.codePart = codePart;
        this.maxNumQuest = maxNumQuest;
        this.time = time;
        this.numAnsRight = numAnsRight;
    }

    public String getCodeSubj() {
        return codeSubj;
    }

    public void setCodeSubj(String codeSubj) {
        this.codeSubj = codeSubj;
    }

    public String getNameSubj() {
        return nameSubj;
    }

    public void setNameSubj(String nameSubj) {
        this.nameSubj = nameSubj;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public String getCodePart() {
        return codePart;
    }

    public void setCodePart(String codePart) {
        this.codePart = codePart;
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
}
