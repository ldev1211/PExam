package com.example.pexam.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pexam.R;
import com.example.pexam.model.Question;
import com.example.pexam.model.QuestionSearch;

public class SearchedFragment extends Fragment {

    QuestionSearch question;
    int colorSelected,colorNormal,colorWhite,colorChoose;
    Drawable drawableChoose,drawableSelected;
    public SearchedFragment(QuestionSearch question){
        this.question = question;
    }
    TextView contentQuestion,ansA,ansB,ansC,ansD,tvAnsA,tvAnsB,tvAnsC,tvAnsD;
    ImageView mark;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_searched, container, false);
        colorNormal = view.getResources().getColor(R.color.black);
        colorSelected = view.getResources().getColor(R.color.primaryColor);
        colorWhite = view.getResources().getColor(R.color.white);
        colorChoose = view.getResources().getColor(R.color.colorHide);
        drawableChoose = view.getResources().getDrawable(R.drawable.shape_ans_choose);
        drawableSelected = view.getResources().getDrawable(R.drawable.shape_ans_selected);
        contentQuestion = view.findViewById(R.id.tv_content_quest_searched);
        ansA = view.findViewById(R.id.ansa_searched);
        ansB = view.findViewById(R.id.ansa_searched);
        ansC = view.findViewById(R.id.ansc_searched);
        ansD = view.findViewById(R.id.ansd_searched);
        tvAnsA = view.findViewById(R.id.tv_content_ansa_searched);
        tvAnsB = view.findViewById(R.id.tv_content_ansb_searched);
        tvAnsC = view.findViewById(R.id.tv_content_ansc_searched);
        tvAnsD = view.findViewById(R.id.tv_content_ansd_searched);
        mark = view.findViewById(R.id.img_mark_state_searched);
        contentQuestion.setText(question.getContentQuestion());
        tvAnsA.setText(question.getAns1());
        tvAnsB.setText(question.getAns2());
        tvAnsC.setText(question.getAns3());
        tvAnsD.setText(question.getAns4());
        if(question.getAns1().equals(question.getAnsRight())){
            tvAnsA.setTextColor(colorSelected);
            ansA.setBackground(drawableSelected);
            ansA.setTextColor(colorWhite);
        } else if(question.getAns2().equals(question.getAnsRight())){
            tvAnsB.setTextColor(colorSelected);
            ansB.setBackground(drawableSelected);
            ansB.setTextColor(colorWhite);
        } else if(question.getAns3().equals(question.getAnsRight())){
            tvAnsC.setTextColor(colorSelected);
            ansC.setBackground(drawableSelected);
            ansC.setTextColor(colorWhite);
        } else {
            tvAnsD.setTextColor(colorSelected);
            ansD.setBackground(drawableSelected);
            ansD.setTextColor(colorWhite);
        }
//        mark.setImageResource((question.isNote())?R.drawable.ic_mark:R.drawable.ic_mark_yet);
        return view;
    }
}