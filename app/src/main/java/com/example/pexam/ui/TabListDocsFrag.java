package com.example.pexam.ui;

import static com.example.pexam.application.ApplicationConfig.NAME_SQLITE;
import static com.example.pexam.application.ApplicationConfig.mSocket;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;

public class TabListDocsFrag extends Fragment {

    List<Docs> docs;
    ListViewDocsAdapter adapter;
    ListView lvDocs;
    Database database;
    Cursor cursor;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_list_docs, container, false);
        database = new Database(requireContext(),NAME_SQLITE,null,1);
        sharedPreferences = requireActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        lvDocs = view.findViewById(R.id.lv_docs);
        docs = new ArrayList<>();
        initOn();
        initEmit();
        return view;
    }

    private void initOn(){
        mSocket.on("RETURN_DOCS_SUBJ", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = (JSONObject) args[0];
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("data_list");
                            for (int i=0;i<jsonArray.length();++i){
                                JSONObject tmp = jsonArray.getJSONObject(i);
                                boolean isNote = false;
                                Cursor cursor = database.getData("SELECT COUNT(contentQuestion) AS count FROM Note WHERE codeKind = '"+sharedPreferences.getString("codeKindCurr",null)+"' AND contentQuestion = '"+tmp.getString("question_content")+"'");
                                while (cursor.moveToNext()){
                                    isNote = cursor.getInt(0) != 0;
                                }
                                docs.add(new Docs(
                                        tmp.getString("question_content"),
                                        tmp.getString("ansa"),
                                        tmp.getString("ansb"),
                                        tmp.getString("ansc"),
                                        tmp.getString("ansd"),
                                        tmp.getString("ans_right"),
                                        isNote
                                ));
                            }
                            if(adapter==null) {
                                adapter = new ListViewDocsAdapter(requireActivity(),docs);
                                lvDocs.setAdapter(adapter);
                            } else adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void initEmit(){
        mSocket.emit("GET_DOCS_SUBJ",sharedPreferences.getString("codeSubjCurr",null));
    }
}