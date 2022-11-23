package com.example.pexam.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import static com.example.pexam.application.ApplicationConfig.mSocket;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;

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
    Handler handler;
    Runnable runnable;
    String stringKeySearched;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("stateApplication",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        stringKeySearched="";
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(stringKeySearched.length()==0) {
                    questionSearches.clear();
                    searchAdapter.notifyDataSetChanged();
                    return;
                }
                mSocket.emit("SEARCH_QUESTION",stringKeySearched);
            }
        };
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
                handler.removeCallbacks(runnable);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                stringKeySearched = charSequence.toString();
                handler.postDelayed(runnable,300);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        view.setOnTouchListener(this);
        popup.setOnTouchListener(this);
        note.setOnTouchListener(this);
        gridViewKind.setOnTouchListener(this);
        eSearchQues.setOnTouchListener(this);
        constraintLayout.setOnTouchListener(this);
        listItemKind = new ArrayList<>();
        initOn();
        initEmit();
        lvSearchQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.layout_parent,new SearchedFragment(questionSearches.get(i)),"PROFILE_FRAG");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }
        });
        gridViewKind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nameKind = listItemKind.get(i).getName();
                String codeKind = listItemKind.get(i).getCodeKind();
                editor.putString("nameKindCurr",nameKind);
                editor.putString("codeKindCurr",codeKind);
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

    public void initOn() {
        mSocket.on("RETURN_SEARCHED_QUESTION", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                requireActivity().runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        questionSearches.clear();
                        JSONObject jsonObject = (JSONObject) args[0];
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = jsonObject.getJSONArray("data_list");
                            Log.d("TAG", "run: "+jsonArray);
                            for(int i=0;i< jsonArray.length();++i){
                                JSONObject tmp = jsonArray.getJSONObject(i);
                                questionSearches.add(new QuestionSearch(
                                        tmp.getString("question_content"),
                                        tmp.getString("ansa"),
                                        tmp.getString("ansb"),
                                        tmp.getString("ansc"),
                                        tmp.getString("ansd"),
                                        tmp.getString("ans_right"),
                                        tmp.getInt("frequency")
                                ));
                            }
                            questionSearches.sort((a,b)->Integer.compare(b.getFrequency(),a.getFrequency()));
                            if(searchAdapter==null){
                                searchAdapter = new ListViewSearchAdapter(requireActivity(),questionSearches);
                                lvSearchQuestion.setAdapter(searchAdapter);
                            } else searchAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        mSocket.on("RETURN_LIST_KIND", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = (JSONObject) args[0];
                            JSONArray jsonArray = jsonObject.getJSONArray("data_list");
                            for(int i=0;i< jsonArray.length();++i){
                                JSONObject tmp = jsonArray.getJSONObject(i);
                                listItemKind.add(new GridKind(tmp.getString("code_kind"),tmp.getString("name_kind"),tmp.getString("thumb"),0));
                            }
                            adapter = new GridAdapter(getContext(),listItemKind);
                            gridViewKind.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void initEmit(){
        mSocket.emit("GET_LIST_KIND");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.edtSearchQues:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if(eSearchQues.getCompoundDrawables()[2]!=null){
                        eSearchQues.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                }
                break;
            default:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    eSearchQues.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_search, 0);
                    hideKeyboard(requireActivity());
                    eSearchQues.setCursorVisible(false);
                }
                break;
        }
        return false;
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}