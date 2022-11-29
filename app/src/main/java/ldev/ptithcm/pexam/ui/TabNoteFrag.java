package ldev.ptithcm.pexam.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pexam.R;
import ldev.ptithcm.pexam.adapter.ListViewNoteQuesAdapter;
import ldev.ptithcm.pexam.model.KindAndQuestionNote;

public class TabNoteFrag extends Fragment {
    ListView listView;
    ListViewNoteQuesAdapter adapter;
    KindAndQuestionNote kindAndQuestionNote;

    public TabNoteFrag(KindAndQuestionNote kindAndQuestionNote){
        this.kindAndQuestionNote = kindAndQuestionNote;
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_note, container, false);
        listView = view.findViewById(R.id.lv_tab_note);
        adapter = new ListViewNoteQuesAdapter(kindAndQuestionNote,requireActivity());
        listView.setAdapter(adapter);
        return view;
    }
}