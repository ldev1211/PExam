package com.example.pexam.model;

import java.util.ArrayList;
import java.util.List;

public class KindAndQuestionNote {
    private String nameKind;
    private List<Docs> listQuestion;

    public String getNameKind() {
        return nameKind;
    }

    public void setNameKind(String nameKind) {
        this.nameKind = nameKind;
    }

    public List<Docs> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion(List<Docs> listQuestion) {
        this.listQuestion = listQuestion;
    }

    public KindAndQuestionNote(String nameKind, List<Docs> listQuestion) {
        this.nameKind = nameKind;
        this.listQuestion = listQuestion;
    }
}
