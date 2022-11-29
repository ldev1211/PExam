package ldev.ptithcm.pexam.ui;

import static ldev.ptithcm.pexam.application.ApplicationConfig.NAME_SQLITE;
import static ldev.ptithcm.pexam.application.ApplicationConfig.mSocket;

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

import com.example.pexam.R;
import ldev.ptithcm.pexam.adapter.ListViewNameExamAdapter;
import ldev.ptithcm.pexam.database.Database;
import ldev.ptithcm.pexam.model.Exam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;

public class TabListExFrag extends Fragment {
    private static final String TAG = "CURSOR";
    ListView lvExam;
    ListViewNameExamAdapter adapter;
    List<Exam> exams;
    SharedPreferences sharedPreferences;
    Database database;
    Cursor cursor;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_list_ex, container, false);
        sharedPreferences = requireActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        lvExam = view.findViewById(R.id.lv_exam);
        exams = new ArrayList<>();
        database = new Database(getContext(), NAME_SQLITE, null, 1);
        lvExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("namePart",exams.get(i).getNamePart());
                editor.putString("codePartCurr",exams.get(i).getCodePart());
                editor.putInt("time",exams.get(i).getTime());
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.toTestingExamFromListExam);
            }
        });
        initOn();
        initEmit();
        return view;
    }

    public void initEmit(){
        mSocket.emit("GET_EXAM",sharedPreferences.getString("codeSubjCurr",null));
    }
    public void initOn(){
        mSocket.on("RETURN_EXAM_PART", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if(getActivity()==null) return;
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = (JSONObject) args[0];
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("data_list");
                            for(int i=0;i<jsonArray.length();++i){
                                JSONObject tmp = jsonArray.getJSONObject(i);
                                int consequence=0;
                                cursor = database.getData("SELECT * FROM ConsequenceExam WHERE codePart = '"+tmp.getString("code_part")+"'");
                                while(cursor.moveToNext()){
                                    consequence = cursor.getInt(1);
                                }
                                exams.add(new Exam(
                                        tmp.getString("code_subj"),
                                        tmp.getString("code_part"),
                                        tmp.getString("name_part"),
                                        tmp.getInt("question_count"),
                                        tmp.getInt("time"),
                                        consequence
                                ));
                            }
                            if(adapter==null){
                                adapter = new ListViewNameExamAdapter(requireContext(),exams);
                                Log.d(TAG, "run: "+exams);
                                lvExam.setAdapter(adapter);
                            } else adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}