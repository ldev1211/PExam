package com.example.pexam.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pexam.R;
import com.example.pexam.database.Database;
import com.example.pexam.model.Question;

import java.util.List;

public class ListViewQuestionConsequenceAdapter extends BaseAdapter {

    private Context context;
    private List<Question> questions;
    private LayoutInflater inflater;
    Database database;
    int colorSelected,colorNormal,colorWhite,colorChoose;
    Drawable drawableChoose,drawableSelected;

    public ListViewQuestionConsequenceAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        database = new Database(context.getApplicationContext(),"PExamSQLite.sqlite",null,1);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    TextView contentQuestion,ansA,ansB,ansC,ansD,tvAnsA,tvAnsB,tvAnsC,tvAnsD;
    ImageView mark;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_note_ques,viewGroup,false);
        colorNormal = view.getResources().getColor(R.color.black);
        colorSelected = view.getResources().getColor(R.color.primaryColor);
        colorWhite = view.getResources().getColor(R.color.white);
        colorChoose = view.getResources().getColor(R.color.colorHide);
        drawableChoose = view.getResources().getDrawable(R.drawable.shape_ans_choose);
        drawableSelected = view.getResources().getDrawable(R.drawable.shape_ans_selected);
        contentQuestion = view.findViewById(R.id.mark_tv_content_quest);
        ansA = view.findViewById(R.id.mark_tv_ansa);
        ansB = view.findViewById(R.id.mark_tv_ansb);
        ansC = view.findViewById(R.id.mark_tv_ansc);
        ansD = view.findViewById(R.id.mark_tv_ansd);
        tvAnsA = view.findViewById(R.id.mark_ansa);
        tvAnsB = view.findViewById(R.id.mark_ansb);
        tvAnsC = view.findViewById(R.id.mark_ansc);
        tvAnsD = view.findViewById(R.id.mark_ansd);
        mark = view.findViewById(R.id.img_mark_state);
        Question question = questions.get(i);
        contentQuestion.setText(question.getContentQuestion());
        tvAnsA.setText(question.getAns1().getContentAns());
        tvAnsB.setText(question.getAns2().getContentAns());
        tvAnsC.setText(question.getAns3().getContentAns());
        tvAnsD.setText(question.getAns4().getContentAns());
        if(question.getAns1().isChoose()){
            tvAnsA.setTextColor(colorChoose);
            ansA.setTextColor(colorWhite);
            ansA.setBackground(drawableChoose);
        } else if(question.getAns2().isChoose()){
            tvAnsB.setTextColor(colorChoose);
            ansB.setTextColor(colorWhite);
            ansB.setBackground(drawableChoose);
        } else if(question.getAns3().isChoose()){
            tvAnsC.setTextColor(colorChoose);
            ansC.setTextColor(colorWhite);
            ansC.setBackground(drawableChoose);
        } else if(question.getAns4().isChoose()){
            tvAnsD.setTextColor(colorChoose);
            ansD.setTextColor(colorWhite);
            ansD.setBackground(drawableChoose);
        }
        if(question.getAns1().getContentAns().equals(question.getAnsRight())){
            tvAnsA.setTextColor(colorSelected);
            ansA.setBackground(drawableSelected);
            ansA.setTextColor(colorWhite);
        } else if(question.getAns2().getContentAns().equals(question.getAnsRight())){
            tvAnsB.setTextColor(colorSelected);
            ansB.setBackground(drawableSelected);
            ansB.setTextColor(colorWhite);
        } else if(question.getAns3().getContentAns().equals(question.getAnsRight())){
            tvAnsC.setTextColor(colorSelected);
            ansC.setBackground(drawableSelected);
            ansC.setTextColor(colorWhite);
        } else {
            tvAnsD.setTextColor(colorSelected);
            ansD.setBackground(drawableSelected);
            ansD.setTextColor(colorWhite);
        }
        mark.setImageResource((question.isNote())?R.drawable.ic_mark:R.drawable.ic_mark_yet);
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question.setNote(!question.isNote());
                mark.setImageResource((question.isNote())?R.drawable.ic_mark:R.drawable.ic_mark_yet);
                notifyDataSetChanged();
                SharedPreferences sharedPreferences = context.getSharedPreferences("stateApplication",Context.MODE_PRIVATE);
                String nameKind = sharedPreferences.getString("nameKindCurr",null);
                switch (nameKind){
                    case "Kỹ thuật":
                        database.queryData("UPDATE DetailQuestionTech SET isNoted = "+((question.isNote())?"1":"0")+" WHERE question = '"+question.getContentQuestion()+"' AND ans1 = '"+ question.getAns1().getContentAns()+"' AND ans2 = '"+question.getAns2().getContentAns()+"' AND ans3 = '"+question.getAns3().getContentAns()+"' AND ans4 = '"+question.getAns4().getContentAns()+"' AND ansRight = '"+question.getAnsRight()+"'");
                        break;
                    case "Kinh tế":
                        database.queryData("UPDATE DetailQuestionEconomy SET isNoted = "+((question.isNote())?"1":"0")+" WHERE question = '"+question.getContentQuestion()+"' AND ans1 = '"+ question.getAns1().getContentAns()+"' AND ans2 = '"+question.getAns2().getContentAns()+"' AND ans3 = '"+question.getAns3().getContentAns()+"' AND ans4 = '"+question.getAns4().getContentAns()+"' AND ansRight = '"+question.getAnsRight()+"'");
                        break;
                    case "Tin cơ sở":
                        database.queryData("UPDATE DetailQuestionOfficial SET isNoted = "+((question.isNote())?"1":"0")+" WHERE question = '"+question.getContentQuestion()+"' AND ans1 = '"+ question.getAns1().getContentAns()+"' AND ans2 = '"+question.getAns2().getContentAns()+"' AND ans3 = '"+question.getAns3().getContentAns()+"' AND ans4 = '"+question.getAns4().getContentAns()+"' AND ansRight = '"+question.getAnsRight()+"'");
                        break;
                    case "Quốc phòng":
                        database.queryData("UPDATE DetailQuestionDefence SET isNoted = "+((question.isNote())?"1":"0")+" WHERE question = '"+question.getContentQuestion()+"' AND ans1 = '"+ question.getAns1().getContentAns()+"' AND ans2 = '"+question.getAns2().getContentAns()+"' AND ans3 = '"+question.getAns3().getContentAns()+"' AND ans4 = '"+question.getAns4().getContentAns()+"' AND ansRight = '"+question.getAnsRight()+"'");
                        break;
                }
            }
        });
        return view;
    }
}
