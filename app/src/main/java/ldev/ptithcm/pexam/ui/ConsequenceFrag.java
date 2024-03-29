package ldev.ptithcm.pexam.ui;

import static ldev.ptithcm.pexam.application.ApplicationConfig.NAME_SQLITE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pexam.R;
import ldev.ptithcm.pexam.adapter.ListViewQuestionConsequenceAdapter;
import ldev.ptithcm.pexam.database.Database;
import ldev.ptithcm.pexam.model.Question;
import ldev.ptithcm.pexam.model.TimeCountDown;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConsequenceFrag extends Fragment {

    View view;
    TextView tvPerQuest,tvOverTime,tvTimerConsequence,tvQuestDid,tvAnsRight,tvAnsWrong,tvPoint,tvConsequenceNameSubj,tvRedo;
    ListView lvQuestConsequence;
    ListViewQuestionConsequenceAdapter adapter;
    List<Question> questions;
    ImageView imgBack;
    ProgressBar progressBar;
    int numAnsRight,numAnsWrong,numQuestDid;
    String ansRight;
    int timeCurr,timeGone;
    Database database;
    Cursor cursor;
    String nameSubj,namePart,nameKind;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_consequence, container, false);
        questions = new ArrayList<>();
        sharedPreferences = requireActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        nameKind = sharedPreferences.getString("nameKindCurr",null);
        nameSubj = sharedPreferences.getString("nameSubjCurr",null);
        namePart = sharedPreferences.getString("namePart",null);
        database = new Database(requireContext(),NAME_SQLITE,null,1);
        questions = (List<Question>) getArguments().getSerializable("questionsWereChoose");
        timeCurr = getArguments().getInt("timeCurr");
        timeGone = getArguments().getInt("timeGone");
        tvRedo = view.findViewById(R.id.tv_redo);
        tvOverTime = view.findViewById(R.id.tv_over_time);
        tvOverTime.setVisibility((timeGone<0)?View.VISIBLE:View.GONE);
        tvConsequenceNameSubj = view.findViewById(R.id.tv_consequence_namesubj);
        progressBar = view.findViewById(R.id.progress_circle);
        imgBack = view.findViewById(R.id.img_back_consequence);
        tvPerQuest = view.findViewById(R.id.tv_per_question_consequence);
        tvTimerConsequence = view.findViewById(R.id.tv_timer_consequence);
        tvQuestDid = view.findViewById(R.id.tv_did_quest);
        tvPoint = view.findViewById(R.id.tv_point);
        tvAnsRight = view.findViewById(R.id.tv_num_ans_right);
        tvAnsWrong = view.findViewById(R.id.tv_num_ans_wrong);
        lvQuestConsequence = view.findViewById(R.id.lv_question_consequence);
        numQuestDid = 0;
        tvRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.toTestingExamFromConsequence);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.toListExamFromConsequence);
            }
        });
        for(Question question : questions){
            ansRight = question.getAnsRight();
            if(!question.isChoose()) numAnsWrong++;
            else {
                numQuestDid++;
                switch (question.getAnsRight()){
                    case "A":
                        if(question.getAns1().isChoose()) numAnsRight++;
                        else numAnsWrong++;
                        break;
                    case "B":
                        if(question.getAns2().isChoose()) numAnsRight++;
                        else numAnsWrong++;
                        break;
                    case "C":
                        if(question.getAns3().isChoose()) numAnsRight++;
                        else numAnsWrong++;
                        break;
                    case "D":
                        if(question.getAns4().isChoose()) numAnsRight++;
                        else numAnsWrong++;
                        break;
                }
            }
        }
        tvPerQuest.setText(numAnsRight+"/"+questions.size());
        tvPoint.setText(Html.fromHtml("Điểm số: <b><span style='color:#C33E37'>"+(((float)numAnsRight/questions.size())*10)+"</span></b>"));
        TimeCountDown timer = getTimeCountDown(timeCurr);
        tvTimerConsequence.setText(((timer.getMinute()>=10)?timer.getMinute()+"":"0"+timer.getMinute())+":"+((timer.getSecond()>=10)?timer.getSecond():"0"+timer.getSecond()));
        tvQuestDid.setText("Đã làm "+numQuestDid+"/"+questions.size());
        tvAnsWrong.setText(numAnsWrong+" câu");
        tvAnsRight.setText(numAnsRight+" câu");
        tvConsequenceNameSubj.setText(nameSubj);
        float floatNumAnsRight = numAnsRight;
        progressBar.setProgress((int) ((floatNumAnsRight/questions.size())*100));
        adapter = new ListViewQuestionConsequenceAdapter(requireActivity(),questions);
        lvQuestConsequence.setAdapter(adapter);
        ListAdapter listAdapter = lvQuestConsequence.getAdapter();
        int totalHeight = 0;
        for(int i=0;i<listAdapter.getCount();++i){
            View item = listAdapter.getView(i,null,lvQuestConsequence);
            item.measure(0,0);
            totalHeight += item.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lvQuestConsequence.getLayoutParams();
        params.height = totalHeight + (lvQuestConsequence.getDividerHeight()*(listAdapter.getCount()-1));
        lvQuestConsequence.setLayoutParams(params);
        lvQuestConsequence.requestLayout();
        Cursor cursor = database.getData("SELECT numAnsRight FROM ConsequenceExam WHERE codePart = '"+sharedPreferences.getString("codePartCurr",null)+"'");
        if (cursor.moveToNext()){
            if(cursor.getInt(0)<numAnsRight){
                database.queryData("INSERT OR REPLACE INTO ConsequenceExam VALUES ('"+sharedPreferences.getString("codePartCurr",null)+"',"+numAnsRight+")");
            }
        } else {
            database.queryData("INSERT OR REPLACE INTO ConsequenceExam VALUES ('"+sharedPreferences.getString("codePartCurr",null)+"',"+numAnsRight+")");
        }
        return view;
    }
    public TimeCountDown getTimeCountDown(long second){
        long minute = TimeUnit.SECONDS.toMinutes(second);
        second -= 60*minute;
        return new TimeCountDown(minute, second);
    }
    @Override
    public void onResume() {
        super.onResume();
        requireView().setFocusableInTouchMode(true);
        requireView().requestFocus();
        requireView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    Navigation.findNavController(v).navigate(R.id.toListExamFromConsequence);
                    return true;
                }
                return false;
            }
        });
    }
}