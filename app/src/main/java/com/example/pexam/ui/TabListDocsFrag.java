package com.example.pexam.ui;

import static com.example.pexam.application.ApplicationConfig.NAME_SQLITE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pexam.R;
import com.example.pexam.adapter.ListViewDocsAdapter;
import com.example.pexam.database.Database;
import com.example.pexam.model.Docs;
import com.example.pexam.model.Question;

import java.util.ArrayList;
import java.util.List;

public class TabListDocsFrag extends Fragment {

    List<Docs> docs;
    ListViewDocsAdapter adapter;
    ListView lvDocs;
    Database database;
    Cursor cursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_list_docs, container, false);
        database = new Database(requireContext(),NAME_SQLITE,null,1);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        String nameKind = sharedPreferences.getString("nameKindCurr",null);
        String nameSubj = sharedPreferences.getString("nameSubjCurr",null);
        switch (nameKind){
            case "Kỹ thuật":
                cursor = database.getData("SELECT * FROM DetailQuestionTech WHERE nameSubj = '"+nameSubj+"'");
                break;
            case "Kinh tế":
                cursor = database.getData("SELECT * FROM DetailQuestionEconomy WHERE nameSubj = '"+nameSubj+"'");
                break;
            case  "Tin cơ sở":
                cursor = database.getData("SELECT * FROM DetailQuestionOfficial WHERE nameSubj = '"+nameSubj+"'");
                break;
            case "Quốc Phòng":
                cursor = database.getData("SELECT * FROM DetailQuestionDefence WHERE nameSubj = '"+nameSubj+"'");
                break;
        }
        lvDocs = view.findViewById(R.id.lv_docs);
        docs = new ArrayList<>();
        while(cursor.moveToNext()){
            docs.add(new Docs(cursor.getString(2)
                    ,cursor.getString(3)
                    ,cursor.getString(4)
                    ,cursor.getString(5)
                    ,cursor.getString(6)
                    ,cursor.getString(7)
                    ,(cursor.getInt(8)==1)));
        }
        adapter = new ListViewDocsAdapter(requireActivity(),docs);
        lvDocs.setAdapter(adapter);
        return view;
    }
}