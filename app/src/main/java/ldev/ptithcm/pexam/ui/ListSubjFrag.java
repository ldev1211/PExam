package ldev.ptithcm.pexam.ui;

import static ldev.ptithcm.pexam.application.ApplicationConfig.mSocket;

import android.content.Context;
import android.content.SharedPreferences;
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
import ldev.ptithcm.pexam.adapter.GridSubjectAdapter;
import ldev.ptithcm.pexam.model.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.socket.emitter.Emitter;

public class ListSubjFrag extends Fragment {

    ImageView imgBack;
    TextView kindName;
    GridView gridSubj;
    GridSubjectAdapter adapter;
    List<Subject> subjects;
    String codeKind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_subj, container, false);
        subjects = new ArrayList<>();
        imgBack = view.findViewById(R.id.imgBack);
        kindName = view.findViewById(R.id.tv_name_kind);
        gridSubj = view.findViewById(R.id.grid_subj);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String nameKind = sharedPreferences.getString("nameKindCurr",null);
        kindName.setText(nameKind.toUpperCase(Locale.ROOT));
        codeKind = sharedPreferences.getString("codeKindCurr",null);
        gridSubj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nameSubj = subjects.get(i).getNameSub();
                editor.putString("nameSubjCurr",nameSubj);
                editor.putString("codeSubjCurr",subjects.get(i).getCodeSubj());
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
        initOn();
        initEmit();
        return view;
    }

    private void initOn() {
        mSocket.on("RETURN_SUBJ", new Emitter.Listener() {
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
                                subjects.add(new Subject(tmp.getString("code_subj"),tmp.getString("name_subj"),tmp.getInt("part_count")));
                            }
                            if(adapter==null){
                                adapter = new GridSubjectAdapter(requireActivity(),subjects);
                                gridSubj.setAdapter(adapter);
                            } else adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
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
    public void initEmit(){
        mSocket.emit("GET_SUBJ",codeKind);
    }
}