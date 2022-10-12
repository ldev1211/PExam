package com.example.pexam.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.pexam.R;
import com.example.pexam.adapter.GridAdapter;
import com.example.pexam.adapter.ListViewSearchAdapter;
import com.example.pexam.model.GridKind;
import com.example.pexam.model.QuestionSearch;

import java.util.ArrayList;
import java.util.List;

public class HomeFrag extends Fragment implements View.OnTouchListener {
    GridView gridViewKind;
    GridAdapter adapter;
    List<GridKind> listItemKind;
    ImageView note,popup;
    PopupMenu popupMenu;
    EditText eSearchQues;
    ListView lvSearchQuestion;
    ListViewSearchAdapter searchAdapter;
    List<QuestionSearch> questionSearches;
    ConstraintLayout constraintLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("stateApplication",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gridViewKind = view.findViewById(R.id.gridView);
        constraintLayout = view.findViewById(R.id.layout_parent);
        lvSearchQuestion = view.findViewById(R.id.lst_search);
        eSearchQues = view.findViewById(R.id.edtSearchQues);
        questionSearches = new ArrayList<>();
        searchAdapter = new ListViewSearchAdapter(requireActivity(),questionSearches);
        lvSearchQuestion.setAdapter(searchAdapter);
        note = view.findViewById(R.id.note_ques);
        popup = view.findViewById(R.id.popupMenu);
        eSearchQues.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    eSearchQues.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                } else {
                    eSearchQues.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search,0,0,0);
                }
            }
        });
        eSearchQues.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void afterTextChanged(Editable editable) {
                searchAdapter.filter(eSearchQues.getText().toString().trim());
            }
        });
        eSearchQues.setOnTouchListener(this);
        constraintLayout.setOnTouchListener(this);
        listItemKind = new ArrayList<>();
        listItemKind.add(new GridKind("Kỹ thuật", R.drawable.tech,160));
        listItemKind.add(new GridKind("Kinh tế", R.drawable.economy,57));
        listItemKind.add(new GridKind("Tin cơ sở", R.drawable.official,230));
        listItemKind.add(new GridKind("Quốc phòng", R.drawable.defence,120));
        adapter = new GridAdapter(getContext(),listItemKind);
        gridViewKind.setAdapter(adapter);
        lvSearchQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(requireContext(), questionSearches.get(i).getContentQuestion(), Toast.LENGTH_SHORT).show();
            }
        });
        gridViewKind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nameKind = listItemKind.get(i).getName();
                editor.putString("nameKindCurr",nameKind);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.toListSubjFromHome);
            }
        });
        popup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                popupMenu = new PopupMenu(getContext(),popup);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_popup,popupMenu.getMenu());
                popupMenu.show();
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.toNoteQues);
            }
        });
        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.edtSearchQues:
                lvSearchQuestion.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_parent:
                lvSearchQuestion.setVisibility(View.GONE);
                return true;
        }
        return false;
    }
}