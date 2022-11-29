package ldev.ptithcm.pexam.adapter;

import static ldev.ptithcm.pexam.application.ApplicationConfig.baseURLPExamServer;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import ldev.ptithcm.pexam.model.KindAndQuestionNote;

public class ListViewNoteQuesAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    Database database;
    KindAndQuestionNote kindAndQuestionNote;

    public ListViewNoteQuesAdapter(KindAndQuestionNote kindAndQuestionNote, Context context) {
        this.kindAndQuestionNote = kindAndQuestionNote;
        this.context = context;
        database = new Database(context.getApplicationContext(),"PExamSQLite.sqlite",null,1);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return kindAndQuestionNote.getListQuestion().size();
    }

    @Override
    public Object getItem(int i) {
        return kindAndQuestionNote.getListQuestion().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    TextView contentQuestion,ansA,ansB,ansC,ansD,tvAnsA,tvAnsB,tvAnsC,tvAnsD;
    ImageView mark;
    Drawable drawableAnsRight;
    int colorPrimary,colorWhite;
    ImageView imgQuestion;
    ImageView imgAnsA,imgAnsB,imgAnsC,imgAnsD;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_note_ques,viewGroup,false);
        colorPrimary = view.getResources().getColor(R.color.primaryColor);
        colorWhite = view.getResources().getColor(R.color.white);
        contentQuestion = view.findViewById(R.id.mark_tv_content_quest);
        drawableAnsRight = view.getResources().getDrawable(R.drawable.shape_ans_selected);
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
        Docs noteQuestion = kindAndQuestionNote.getListQuestion().get(i);
        contentQuestion.setText(noteQuestion.getContentQuestion());
        if(noteQuestion.getLinkImg().length()!=0) Picasso.get().load(noteQuestion.getLinkImg()).into(imgQuestion);
        if(noteQuestion.getAns1().contains(baseURLPExamServer)) Picasso.get().load(noteQuestion.getAns1()).into(imgAnsA);
        else tvAnsA.setText(noteQuestion.getAns1());
        if(noteQuestion.getAns2().contains(baseURLPExamServer)) Picasso.get().load(noteQuestion.getAns2()).into(imgAnsB);
        else tvAnsB.setText(noteQuestion.getAns2());
        if(noteQuestion.getAns3().contains(baseURLPExamServer)) Picasso.get().load(noteQuestion.getAns3()).into(imgAnsC);
        else tvAnsC.setText(noteQuestion.getAns3());
        if(noteQuestion.getAns4().contains(baseURLPExamServer)) Picasso.get().load(noteQuestion.getAns4()).into(imgAnsD);
        else tvAnsD.setText(noteQuestion.getAns4());
        if(noteQuestion.getAnsRight().equals("A")){
            tvAnsA.setTextColor(colorPrimary);
            ansA.setTextColor(colorWhite);
            ansA.setBackground(drawableAnsRight);
        } else if(noteQuestion.getAnsRight().equals("B")){
            tvAnsB.setTextColor(colorPrimary);
            ansB.setTextColor(colorWhite);
            ansB.setBackground(drawableAnsRight);
        } else if(noteQuestion.getAnsRight().equals("C")){
            tvAnsC.setTextColor(colorPrimary);
            ansC.setTextColor(colorWhite);
            ansC.setBackground(drawableAnsRight);
        } else {
            tvAnsD.setTextColor(colorPrimary);
            ansD.setTextColor(colorWhite);
            ansD.setBackground(drawableAnsRight);
        }
        mark.setImageResource(R.drawable.ic_mark);
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kindAndQuestionNote.getListQuestion().remove(i);
                notifyDataSetChanged();
                database.queryData("DELETE FROM Note WHERE contentQuestion = '"+noteQuestion.getContentQuestion()+"'");
            }
        });
        return view;
    }
}
