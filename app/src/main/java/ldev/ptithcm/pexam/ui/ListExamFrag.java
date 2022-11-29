package ldev.ptithcm.pexam.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pexam.R;
import ldev.ptithcm.pexam.adapter.ViewPagerListExAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ListExamFrag extends Fragment {

    ImageView imgBack;
    TextView tvNameExam;
    ViewPager2 viewPager2;
    ViewPagerListExAdapter adapter;
    TabLayout tabLayout;
    TabLayoutMediator mediator;
    View viewTabExam,viewTabDocs;
    TextView tvTabExam,tvTabDocs;
    View view;
    SharedPreferences sharedPreferences;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_exam, container, false);
        viewTabExam = getLayoutInflater().inflate(R.layout.custom_item_tab_ex_doc,null);
        viewTabDocs = getLayoutInflater().inflate(R.layout.custom_item_tab_ex_doc,null);
        tvTabExam = viewTabExam.findViewById(R.id.tv_ex_docs);
        tvTabDocs = viewTabDocs.findViewById(R.id.tv_ex_docs);
        imgBack = view.findViewById(R.id.image_list_exam_back);
        viewPager2 = view.findViewById(R.id.pager_list_exam);
        tabLayout = view.findViewById(R.id.tab_exam_or_docs);
        tvNameExam = view.findViewById(R.id.tv_exam_name);
        sharedPreferences = requireActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        String nameKindFrListSubj = sharedPreferences.getString("nameSubjCurr",null);
        tvNameExam.setText(nameKindFrListSubj);
        adapter = new ViewPagerListExAdapter(requireActivity(),viewPager2);
        viewPager2.setAdapter(adapter);
        mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tvTabExam.setText("Bài kiểm tra");
                        tab.setCustomView(viewTabExam);
                        break;
                    case 1:
                        tvTabDocs.setText("Tài liệu trắc nghiệm");
                        tvTabDocs.setTextColor(getResources().getColor(R.color.tabColorHide));
                        tab.setCustomView(viewTabDocs);
                        break;
                }
            }
        });
        mediator.attach();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tvTabExam.setTextColor(getResources().getColor(R.color.primaryColor));
                        break;
                    case 1:
                        tvTabDocs.setTextColor(getResources().getColor(R.color.primaryColor));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tvTabExam.setTextColor(getResources().getColor(R.color.tabColorHide));
                        break;
                    case 1:
                        tvTabDocs.setTextColor(getResources().getColor(R.color.tabColorHide));
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.toListSubjFromListExam);
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
                    Navigation.findNavController(v).navigate(R.id.toListSubjFromListExam);
                    return true;
                }
                return false;
            }
        });
    }
}