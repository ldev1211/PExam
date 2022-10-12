package com.example.pexam.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pexam.model.KindAndQuestionNote;
import com.example.pexam.ui.TabNoteFrag;

import java.util.List;

public class ViewPagerNoteAdapter extends FragmentStateAdapter {

    ViewPager2 viewPager2;
    List<KindAndQuestionNote> kindAndQuestionNotes;

    public ViewPagerNoteAdapter(@NonNull FragmentActivity fragmentActivity, ViewPager2 viewPager2, List<KindAndQuestionNote> kindAndQuestionNotes) {
        super(fragmentActivity);
        this.viewPager2 = viewPager2;
        this.kindAndQuestionNotes = kindAndQuestionNotes;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new TabNoteFrag(kindAndQuestionNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return kindAndQuestionNotes.size();
    }
}
