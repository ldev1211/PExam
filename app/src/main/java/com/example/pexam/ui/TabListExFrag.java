package com.example.pexam.ui;

import static com.example.pexam.application.ApplicationConfig.NAME_SQLITE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pexam.R;
import com.example.pexam.adapter.ListViewNameExamAdapter;
import com.example.pexam.database.Database;
import com.example.pexam.model.Exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TabListExFrag extends Fragment {
    private static final String TAG = "CURSOR";
    ListView lvExam;
    ListViewNameExamAdapter adapter;
    List<Exam> exams;
    Database database;
    Cursor cursor;
    String nameSubj,nameKind;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_list_ex, container, false);
        lvExam = view.findViewById(R.id.lv_exam);
        exams = new ArrayList<>();
        database = new Database(getContext(), NAME_SQLITE, null, 1);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        nameSubj = sharedPreferences.getString("nameSubjCurr", null);
        nameKind = sharedPreferences.getString("nameKindCurr", null);
        switch (nameKind){
            case "Kỹ thuật":
                cursor = database.getData("SELECT * FROM SettingExamTech WHERE SettingExamTech.nameSubj = '"+nameSubj+"'");
                break;
            case "Kinh tế":
                cursor = database.getData("SELECT * FROM SettingExamEconomy WHERE SettingExamEconomy.nameSubj = '"+nameSubj+"'");
                break;
            case "Tin cơ sở":
                cursor = database.getData("SELECT * FROM SettingExamOfficial WHERE SettingExamOfficial.nameSubj = '"+nameSubj+"'");
                break;
            case "Quốc phòng":
                cursor = database.getData("SELECT * FROM SettingExamDefence WHERE SettingExamDefence.nameSubj = '"+nameSubj+"'");
                break;
        }
        while(cursor.moveToNext()){
            exams.add(new Exam(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),0));
        }
        switch (nameKind){
            case "Kỹ thuật":
                cursor = database.getData("SELECT * FROM ConsequenceExamTech WHERE nameSubj = '"+nameSubj+"'");
                break;
            case "Kinh tế":
                cursor = database.getData("SELECT * FROM ConsequenceExamEconomy WHERE nameSubj = '"+nameSubj+"'");
                break;
            case "Tin cơ sở":
                cursor = database.getData("SELECT * FROM ConsequenceExamOfficial WHERE nameSubj = '"+nameSubj+"'");
                break;
            case "Quốc phòng":
                cursor = database.getData("SELECT * FROM ConsequenceExamDefence WHERE nameSubj = '"+nameSubj+"'");
                break;
        }
        while(cursor.moveToNext()){
            for(int i=0;i<exams.size();++i){
                if(exams.get(i).getPart().equals(cursor.getString(1))){
                    exams.get(i).setNumAnsRight(cursor.getInt(2));
                }
            }
        }
        adapter = new ListViewNameExamAdapter(requireContext(),exams);
        lvExam.setAdapter(adapter);
        lvExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("namePart",exams.get(i).getPart());
                editor.putInt("time",exams.get(i).getTime());
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.toTestingExamFromListExam);
            }
        });
        return view;
    }
}