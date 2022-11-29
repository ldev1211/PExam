package ldev.ptithcm.pexam.model;

public class GridKind {
    private String codeKind;
    private String name;
    private String imgLink;
    private int numSubj;

    public String getName() {
        return name;
    }

    public String getCodeKind() {
        return codeKind;
    }

    public void setCodeKind(String codeKind) {
        this.codeKind = codeKind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public int getNumSubj() {
        return numSubj;
    }

    public void setNumSubj(int numSubj) {
        this.numSubj = numSubj;
    }

    public GridKind(String codeKind, String name, String imgLink, int numSubj) {
        this.codeKind = codeKind;
        this.name = name;
        this.imgLink = imgLink;
        this.numSubj = numSubj;
    }
}
