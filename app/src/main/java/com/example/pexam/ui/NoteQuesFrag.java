package com.example.pexam.ui;

import static com.example.pexam.application.ApplicationConfig.NAME_SQLITE;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pexam.R;
import com.example.pexam.adapter.ViewPagerNoteAdapter;
import com.example.pexam.database.Database;
import com.example.pexam.model.Docs;
import com.example.pexam.model.KindAndQuestionNote;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteQuesFrag extends Fragment {

    ImageView back,deleteAll;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerNoteAdapter adapter;
    Database database;
    TabLayoutMediator mediator;
    Cursor cursor;
    List<KindAndQuestionNote> kindAndQuestionNotes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_ques, container, false);
        back = view.findViewById(R.id.back);
        tabLayout = view.findViewById(R.id.tablayout_note_ques);
        viewPager2 = view.findViewById(R.id.pager_note_quest);
        deleteAll = view.findViewById(R.id.deleteAll);
        database = new Database(getContext(),NAME_SQLITE,null,1);
        cursor = database.getData("SELECT * FROM Note");
        kindAndQuestionNotes = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        while (cursor.moveToNext()){
            if (map.get(cursor.getString(0))==null){
                kindAndQuestionNotes.add(new KindAndQuestionNote(cursor.getString(1),new ArrayList<>()));
                kindAndQuestionNotes.get(kindAndQuestionNotes.size()-1).getListQuestion().add(new Docs(
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        true
                ));
            } else {
                for(int i=0;i<kindAndQuestionNotes.size();++i){
                    if(kindAndQuestionNotes.get(i).getNameKind().equals(cursor.getString(1))){
                        kindAndQuestionNotes.get(i).getListQuestion().add(new Docs(
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                true
                        ));
                        break;
                    }
                }
            }
            map.put(cursor.getString(0),cursor.getString(1));
        }
        adapter = new ViewPagerNoteAdapter(requireActivity(),viewPager2,kindAndQuestionNotes);
        viewPager2.setAdapter(adapter);
        mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String nameKind = kindAndQuestionNotes.get(position).getNameKind();
                View ctView = getLayoutInflater().inflate(R.layout.custom_item_tab_ex_doc,null);
                TextView tv = ctView.findViewById(R.id.tv_ex_docs);
                tv.setText(nameKind);
                tv.setTextColor(getResources().getColor((position>0)?R.color.tabColorHide:R.color.primaryColor));
                tab.setCustomView(ctView);
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tv = tab.getCustomView().findViewById(R.id.tv_ex_docs);
                tv.setTextColor(getResources().getColor(R.color.primaryColor));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tv = tab.getCustomView().findViewById(R.id.tv_ex_docs);
                tv.setTextColor(getResources().getColor(R.color.tabColorHide));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (kindAndQuestionNotes.size()>1) {
            mediator.attach();
        } else {
            tabLayout.setVisibility(View.GONE);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.toHomeFromNoteQues);
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_y_n);
                dialog.show();
                TextView tvCancel = dialog.findViewById(R.id.tvCancel);
                TextView tvAccept = dialog.findViewById(R.id.tvAccept);
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                tvAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.queryData("DELETE FROM Note WHERE 1=1");
                        tabLayout.setVisibility(View.GONE);
                        viewPager2.setVisibility(View.GONE);
                        dialog.cancel();
                    }
                });
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
                    Navigation.findNavController(v).navigate(R.id.toHomeFromNoteQues);
                    return true;
                }
                return false;
            }
        });
    }
}