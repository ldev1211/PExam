package ldev.ptithcm.pexam.adapter;

import static ldev.ptithcm.pexam.application.ApplicationConfig.baseURLPExamServer;
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

import com.example.pexam.R;
import com.squareup.picasso.Picasso;

import ldev.ptithcm.pexam.database.Database;
import ldev.ptithcm.pexam.model.Question;

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
    ImageView imgQuestion,imgAnsA,imgAnsB,imgAnsC,imgAnsD;

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
        imgQuestion = view.findViewById(R.id.img_question_note_quest);
        imgAnsA = view.findViewById(R.id.img_ansa_note_quest);
        imgAnsB = view.findViewById(R.id.img_ansb_note_quest);
        imgAnsC = view.findViewById(R.id.img_ansc_note_quest);
        imgAnsD = view.findViewById(R.id.img_ansd_note_quest);
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
        if(question.getLinkImgQuestion().length()!=0) Picasso.get().load(question.getLinkImgQuestion()).into(imgQuestion);
        if(question.getAns1().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns1().getContentAns()).into(imgAnsA);
        else tvAnsA.setText(question.getAns1().getContentAns());
        if(question.getAns2().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns2().getContentAns()).into(imgAnsB);
        else tvAnsB.setText(question.getAns2().getContentAns());
        if(question.getAns3().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns3().getContentAns()).into(imgAnsC);
        else tvAnsC.setText(question.getAns3().getContentAns());
        if(question.getAns4().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns4().getContentAns()).into(imgAnsD);
        else tvAnsD.setText(question.getAns4().getContentAns());
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
        if(question.getAnsRight().equals("A")){
            tvAnsA.setTextColor(colorSelected);
            ansA.setBackground(drawableSelected);
            ansA.setTextColor(colorWhite);
        } else if(question.getAnsRight().equals("B")){
            tvAnsB.setTextColor(colorSelected);
            ansB.setBackground(drawableSelected);
            ansB.setTextColor(colorWhite);
        } else if(question.getAnsRight().equals("C")){
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
                String codeKind = sharedPreferences.getString("codeKindCurr",null);
                if(question.isNote()) {
                    database.queryData("INSERT INTO Note VALUES(" +
                            "'"+sharedPreferences.getString("codeKindCurr",null)+ "'" +
                            ",'"+sharedPreferences.getString("nameKindCurr",null) +"'" +
                            ",'"+question.getContentQuestion()+"'" +
                            ",'"+question.getLinkImgQuestion()+"'" +
                            ",'"+question.getAns1().getContentAns()+"'" +
                            ",'"+question.getAns2().getContentAns()+"'" +
                            ",'"+question.getAns3().getContentAns()+"'" +
                            ",'"+question.getAns4().getContentAns()+"'" +
                            ",'"+question.getAnsRight()+"')"
                    );
                } else database.queryData("DELETE FROM Note WHERE codeKind = '"+sharedPreferences.getString("codeKindCurr",null)+"' AND contentQuestion = '"+question.getContentQuestion()+"'");
            }
        });
        return view;
    }
}
