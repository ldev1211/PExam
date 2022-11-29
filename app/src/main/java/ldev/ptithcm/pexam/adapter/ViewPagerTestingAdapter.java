package ldev.ptithcm.pexam.adapter;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import ldev.ptithcm.pexam.model.Question;
import ldev.ptithcm.pexam.ui.QuestionFrag;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ViewPagerTestingAdapter extends FragmentStateAdapter {

    ViewPager2 viewPager2;
    List<Question> questions;
    TabLayout tabLayout;
    TextView tvAnswered;

    public ViewPagerTestingAdapter(@NonNull FragmentActivity fragmentActivity,ViewPager2 viewPager2,List<Question> list,TabLayout tabLayout,TextView tvAnswered) {
        super(fragmentActivity);
        this.viewPager2 = viewPager2;
        this.questions = list;
        this.tabLayout = tabLayout;
        this.tvAnswered = tvAnswered;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Question question = questions.get(position);
        QuestionFrag questionFrag = new QuestionFrag(tabLayout.getTabAt(position),tvAnswered,questions.size());
        Bundle bundle = new Bundle();
        bundle.putSerializable("question",question);
        questionFrag.setArguments(bundle);
        return questionFrag;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
