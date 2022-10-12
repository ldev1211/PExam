package com.example.pexam.ui;

import static com.example.pexam.application.ApplicationConfig.NAME_SQLITE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pexam.R;
import com.example.pexam.adapter.GridSubjectAdapter;
import com.example.pexam.database.Database;
import com.example.pexam.model.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ListSubjFrag extends Fragment {

    ImageView imgBack;
    TextView kindName;
    GridView gridSubj;
    GridSubjectAdapter adapter;
    List<Subject> subjects;
    Database database;
    Cursor cursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_subj, container, false);
        imgBack = view.findViewById(R.id.imgBack);
        kindName = view.findViewById(R.id.tv_name_kind);
        gridSubj = view.findViewById(R.id.grid_subj);
        database = new Database(getContext(),NAME_SQLITE,null,1);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String nameKind = sharedPreferences.getString("nameKindCurr",null);
        switch (nameKind){
            case "Kỹ thuật":
                cursor = database.getData("SELECT * FROM SettingExamTech");
                break;
            case "Kinh tế":
                cursor = database.getData("SELECT * FROM SettingExamEconomy");
                break;
            case "Tin cơ sở":
                cursor = database.getData("SELECT * FROM SettingExamOfficial");
                break;
            case "Quốc phòng":
                cursor = database.getData("SELECT * FROM SettingExamDefence");
                break;
        }
        kindName.setText(nameKind.toUpperCase(Locale.ROOT));
        subjects = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        while(cursor.moveToNext()){
            String nameSubj = cursor.getString(0);
            Integer value = map.get(nameSubj);
            if(value==null) map.put(nameSubj,1);
            else {
                value++;
                map.put(nameSubj,value);
            }
        }
        Set<String> set = map.keySet();
        for(String s:set){
            subjects.add(new Subject(s,map.get(s)));
        }
        adapter = new GridSubjectAdapter(getActivity(),subjects);
        gridSubj.setAdapter(adapter);
        gridSubj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nameSubj = subjects.get(i).getNameSub();
                editor.putString("nameSubjCurr",nameSubj);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.toListExamFromListSubj);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.toHomeFromListSubj);
            }
        });
        return view;
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
                    Navigation.findNavController(v).navigate(R.id.toHomeFromListSubj);
                    return true;
                }
                return false;
            }
        });
    }
}