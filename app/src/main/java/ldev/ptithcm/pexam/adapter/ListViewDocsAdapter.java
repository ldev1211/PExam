package ldev.ptithcm.pexam.adapter;

import static ldev.ptithcm.pexam.application.ApplicationConfig.NAME_SQLITE;
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
import ldev.ptithcm.pexam.model.Docs;

import java.util.List;

public class ListViewDocsAdapter extends BaseAdapter {

    Context context;
    List<Docs> docs;
    LayoutInflater inflater;

    public ListViewDocsAdapter(Context context, List<Docs> docs) {
        this.context = context;
        this.docs = docs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return docs.size();
    }

    @Override
    public Object getItem(int i) {
        return docs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    TextView contentQuestion,ansA,ansB,ansC,ansD,tvAnsA,tvAnsB,tvAnsC,tvAnsD;
    ImageView mark;
    int colorSelected,colorWhite;
    Drawable drawableSelected;
    Database database;
    SharedPreferences sharedPreferences;
    ImageView imgAnsA,imgAnsB,imgAnsC,imgAnsD;
    ImageView imgQuestion;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_note_ques,viewGroup,false);
        sharedPreferences = view.getContext().getSharedPreferences("stateApplication",Context.MODE_PRIVATE);
        database = new Database(view.getContext(),NAME_SQLITE,null,1);
        colorSelected = view.getResources().getColor(R.color.primaryColor);
        colorWhite = view.getResources().getColor(R.color.white);
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
        Docs doc = docs.get(i);
        contentQuestion.setText(doc.getContentQuestion());
        if(doc.getLinkImg().length()!=0) Picasso.get().load(doc.getLinkImg()).into(imgQuestion);
        if(doc.getAns1().contains(baseURLPExamServer)) Picasso.get().load(doc.getAns1()).into(imgAnsA);
        else tvAnsA.setText(doc.getAns1());
        if(doc.getAns2().contains(baseURLPExamServer)) Picasso.get().load(doc.getAns2()).into(imgAnsB);
        else tvAnsB.setText(doc.getAns2());
        if(doc.getAns3().contains(baseURLPExamServer)) Picasso.get().load(doc.getAns3()).into(imgAnsC);
        else tvAnsC.setText(doc.getAns3());
        if(doc.getAns4().contains(baseURLPExamServer)) Picasso.get().load(doc.getAns4()).into(imgAnsD);
        else tvAnsD.setText(doc.getAns4());
        if(doc.getAnsRight().equals("A")){
            tvAnsA.setTextColor(colorSelected);
            ansA.setBackground(drawableSelected);
            ansA.setTextColor(colorWhite);
        } else if(doc.getAnsRight().equals("B")){
            tvAnsB.setTextColor(colorSelected);
            ansB.setBackground(drawableSelected);
            ansB.setTextColor(colorWhite);
        } else if(doc.getAnsRight().equals("C")){
            tvAnsC.setTextColor(colorSelected);
            ansC.setBackground(drawableSelected);
            ansC.setTextColor(colorWhite);
        } else {
            tvAnsD.setTextColor(colorSelected);
            ansD.setBackground(drawableSelected);
            ansD.setTextColor(colorWhite);
        }
        mark.setImageResource((doc.isNote())?R.drawable.ic_mark:R.drawable.ic_mark_yet);
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docs.get(i).setNote(!docs.get(i).isNote());
                mark.setImageResource((docs.get(i).isNote())?R.drawable.ic_mark:R.drawable.ic_mark_yet);
                Log.d("TAG", "onClick: "+docs.get(i).getContentQuestion()+" and "+docs.get(i).isNote()+" and "+i);
                if(docs.get(i).isNote()) {
                    database.queryData("INSERT INTO Note VALUES(" +
                            "'"+sharedPreferences.getString("codeKindCurr",null)+ "'" +
                            ",'"+sharedPreferences.getString("nameKindCurr",null) +"'" +
                            ",'"+docs.get(i).getContentQuestion()+"'" +
                            ",'"+docs.get(i).getAns1()+"'" +
                            ",'"+docs.get(i).getAns2()+"'" +
                            ",'"+docs.get(i).getAns3()+"'" +
                            ",'"+docs.get(i).getAns4()+"'" +
                            ",'"+docs.get(i).getAnsRight()+"')"
                    );
                }
                else database.queryData("DELETE FROM Note WHERE codeKind = '"+sharedPreferences.getString("codeKindCurr",null)+"' AND contentQuestion = '"+docs.get(i).getContentQuestion()+"'");
            }
        });
        return view;
    }
}
