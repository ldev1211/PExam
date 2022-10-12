package com.example.pexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.pexam.R;
import com.example.pexam.model.Exam;
import com.example.pexam.model.TimeCountDown;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListViewNameExamAdapter extends BaseAdapter {
    private List<Exam> exams;
    private Context context;
    private LayoutInflater layoutInflater;

    public ListViewNameExamAdapter(Context context,List<Exam> exams) {
        this.context = context;
        this.exams = exams;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return exams.size();
    }

    @Override
    public Object getItem(int i) {
        return exams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    TextView tvPartName,tvMaxNumQues,tvTime,tvPer;
    ProgressBar progressBar;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_part,viewGroup,false);
        progressBar = view.findViewById(R.id.progressBar);
        tvPartName = view.findViewById(R.id.tvNamePart);
        tvTime = view.findViewById(R.id.tvTime);
        tvPer = view.findViewById(R.id.tvPercent);
        tvMaxNumQues = view.findViewById(R.id.tvMaxNumQues);
        progressBar.setProgress((int)((float)exams.get(i).getNumAnsRight()/exams.get(i).getMaxNumQuest()*100));
        tvPer.setText(exams.get(i).getNumAnsRight()+"/"+exams.get(i).getMaxNumQuest());
        tvPartName.setText(exams.get(i).getPart());
        TimeCountDown timeCountDown = getTimeCountDown(exams.get(i).getTime());
        tvMaxNumQues.setText(exams.get(i).getMaxNumQuest()+" câu");
        tvTime.setText(timeCountDown.getMinute()+":"+((timeCountDown.getSecond()>=10)?timeCountDown.getSecond():"0"+timeCountDown.getSecond()));
        return view;
    }
    public TimeCountDown getTimeCountDown(long second){
        long minute = TimeUnit.SECONDS.toMinutes(second);
        second -= 60*minute;
        return new TimeCountDown(minute, second);
    }
}
