package ldev.ptithcm.pexam.ui;

import static ldev.ptithcm.pexam.application.ApplicationConfig.baseURLPExamServer;
import static ldev.ptithcm.pexam.application.ApplicationConfig.numAnswered;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pexam.R;
import ldev.ptithcm.pexam.model.Question;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class QuestionFrag extends Fragment implements View.OnClickListener {

    View view;
    TextView contentQuestion,ansA,ansB,ansC,ansD,a,b,c,d,tvAnswered;
    boolean isHideA = false,isHideB = false,isHideC = false,isHideD = false;
    ImageView hideA,hideB,hideC,hideD;
    Question question;
    int colorSelected,colorNormal,colorHide,colorWhite,maxNumQuest;
    Drawable drawableNormal,drawableSelected;
    TabLayout.Tab tabItem;
    ImageView imgQuestion;
    ImageView imgAnsA,imgAnsB,imgAnsC,imgAnsD;

    public QuestionFrag(TabLayout.Tab tabItem,TextView tvAnswered,int maxNumQuest){
        this.tabItem = tabItem;
        this.tvAnswered = tvAnswered;
        this.maxNumQuest = maxNumQuest;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question, container, false);
        drawableNormal = getResources().getDrawable(R.drawable.shape_ans);
        drawableSelected = getResources().getDrawable(R.drawable.shape_ans_selected);
        colorSelected = getResources().getColor(R.color.primaryColor);
        colorNormal = getResources().getColor(R.color.black);
        colorHide = getResources().getColor(R.color.colorHide);
        colorWhite = getResources().getColor(R.color.white);
        contentQuestion = view.findViewById(R.id.tv_content_quest);
        imgQuestion = view.findViewById(R.id.img_question);
        imgAnsA = view.findViewById(R.id.img_ansa);
        imgAnsB = view.findViewById(R.id.img_ansb);
        imgAnsC = view.findViewById(R.id.img_ansc);
        imgAnsD = view.findViewById(R.id.img_ansd);
        ansA = view.findViewById(R.id.ansa);
        ansB = view.findViewById(R.id.ansb);
        ansC = view.findViewById(R.id.ansc);
        ansD = view.findViewById(R.id.ansd);
        a = view.findViewById(R.id.tv_ansa);
        b = view.findViewById(R.id.tv_ansb);
        c = view.findViewById(R.id.tv_ansc);
        d = view.findViewById(R.id.tv_ansd);
        hideA = view.findViewById(R.id.visivility_a);
        hideB = view.findViewById(R.id.visivility_b);
        hideC = view.findViewById(R.id.visivility_c);
        hideD = view.findViewById(R.id.visivility_d);
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        hideA.setOnClickListener(this);
        hideB.setOnClickListener(this);
        hideC.setOnClickListener(this);
        hideD.setOnClickListener(this);
        Bundle bundle = getArguments();
        if(bundle!=null){
            question = (Question) bundle.get("question");
            if(question!=null){
                contentQuestion.setText(question.getContentQuestion());
                if(question.getLinkImgQuestion().length()>0) Picasso.get().load(question.getLinkImgQuestion()).into(imgQuestion);

                if(question.getAns1().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns1().getContentAns()).into(imgAnsA);
                else ansA.setText(question.getAns1().getContentAns());

                if(question.getAns2().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns2().getContentAns()).into(imgAnsB);
                else ansB.setText(question.getAns2().getContentAns());

                if(question.getAns3().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns3().getContentAns()).into(imgAnsC);
                else ansC.setText(question.getAns3().getContentAns());

                if(question.getAns4().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns4().getContentAns()).into(imgAnsD);
                else ansD.setText(question.getAns4().getContentAns());
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("questionSaveState",question);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState==null) return;
        question = (Question) savedInstanceState.get("questionSaveState");
        contentQuestion.setText(question.getContentQuestion());

        boolean isHideA = question.getAns1().isHide();
        if(question.getAns1().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns1().getContentAns()).into(imgAnsA);
        else ansA.setText(question.getAns1().getContentAns());
        ansA.setTextColor((isHideA)?colorHide:(question.getAns1().isChoose())?colorSelected:colorNormal);
        a.setTextColor((isHideA)?colorHide:(question.getAns1().isChoose())?colorWhite:colorNormal);
        a.setBackground((question.getAns1().isChoose())?drawableSelected:drawableNormal);
        hideA.setImageResource((isHideA)?R.drawable.eye:R.drawable.ic_visibility_outline);

        boolean isHideB = question.getAns2().isHide();
        if(question.getAns2().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns2().getContentAns()).into(imgAnsA);
        else ansB.setText(question.getAns2().getContentAns());
        ansB.setTextColor((isHideB)?colorHide:(question.getAns2().isChoose())?colorSelected:colorNormal);
        b.setTextColor((isHideB)?colorHide:(question.getAns2().isChoose())?colorWhite:colorNormal);
        b.setBackground((question.getAns2().isChoose())?drawableSelected:drawableNormal);
        hideB.setImageResource((isHideB)?R.drawable.eye:R.drawable.ic_visibility_outline);

        boolean isHideC = question.getAns3().isHide();
        if(question.getAns3().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns3().getContentAns()).into(imgAnsA);
        else ansC.setText(question.getAns3().getContentAns());
        ansC.setTextColor((isHideC)?colorHide:(question.getAns3().isChoose())?colorSelected:colorNormal);
        c.setTextColor((isHideC)?colorHide:(question.getAns3().isChoose())?colorWhite:colorNormal);
        c.setBackground((question.getAns3().isChoose())?drawableSelected:drawableNormal);
        hideC.setImageResource((isHideC)?R.drawable.eye:R.drawable.ic_visibility_outline);

        boolean isHideD = question.getAns4().isHide();
        if(question.getAns4().getContentAns().contains(baseURLPExamServer)) Picasso.get().load(question.getAns4().getContentAns()).into(imgAnsA);
        else ansD.setText(question.getAns4().getContentAns());
        ansD.setTextColor((isHideD)?colorHide:(question.getAns4().isChoose())?colorSelected:colorNormal);
        d.setBackground((question.getAns4().isChoose())?drawableSelected:drawableNormal);
        d.setTextColor((isHideD)?colorHide:(question.getAns4().isChoose())?colorWhite:colorNormal);
        hideD.setImageResource((isHideD)?R.drawable.eye:R.drawable.ic_visibility_outline);
    }
    
    private void setTabTextSize(TabLayout.Tab tab, int tabSizeSp, int textColor){
        View tabCustomView = tab.getCustomView();
        TextView tabTextView = tabCustomView.findViewById(R.id.tv_num_tab);
        tabTextView.setTextSize(tabSizeSp);
        tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.getContext(), textColor));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ansa:
            case R.id.img_ansa:
            case R.id.tv_ansa:
                if(question.getAns1().isHide()) return;
                question.getAns1().setChoose(true);

                ansA.setTextColor(colorSelected);
                a.setBackground(drawableSelected);
                a.setTextColor(colorWhite);

                if(!question.getAns2().isHide()){
                    ansB.setTextColor(colorNormal);
                    b.setTextColor(colorNormal);
                    b.setBackground(drawableNormal);
                    question.getAns2().setChoose(false);
                }
                if(!question.getAns3().isHide()){
                    ansC.setTextColor(colorNormal);
                    c.setTextColor(colorNormal);
                    c.setBackground(drawableNormal);
                    question.getAns3().setChoose(false);
                }
                if(!question.getAns4().isHide()){
                    ansD.setTextColor(colorNormal);
                    d.setTextColor(colorNormal);
                    d.setBackground(drawableNormal);
                    question.getAns4().setChoose(false);
                }
                break;
            case R.id.ansb:
            case R.id.img_ansb:
            case R.id.tv_ansb:
                if(question.getAns2().isHide()) return;
                question.getAns2().setChoose(true);

                ansB.setTextColor(colorSelected);
                b.setBackground(drawableSelected);
                b.setTextColor(colorWhite);

                if(!question.getAns1().isHide()){
                    ansA.setTextColor(colorNormal);
                    a.setTextColor(colorNormal);
                    a.setBackground(drawableNormal);
                    question.getAns1().setChoose(false);
                }
                if(!question.getAns3().isHide()){
                    ansC.setTextColor(colorNormal);
                    c.setTextColor(colorNormal);
                    c.setBackground(drawableNormal);
                    question.getAns3().setChoose(false);
                }
                if(!question.getAns4().isHide()){
                    ansD.setTextColor(colorNormal);
                    d.setTextColor(colorNormal);
                    d.setBackground(drawableNormal);
                    question.getAns4().setChoose(false);
                }
                break;
            case R.id.ansc:
            case R.id.img_ansc:
            case R.id.tv_ansc:
                if(question.getAns3().isHide()) return;
                question.getAns3().setChoose(true);

                ansC.setTextColor(colorSelected);
                c.setBackground(drawableSelected);
                c.setTextColor(colorWhite);

                if(!question.getAns1().isHide()){
                    ansA.setTextColor(colorNormal);
                    a.setTextColor(colorNormal);
                    a.setBackground(drawableNormal);
                    question.getAns1().setChoose(false);
                }
                if(!question.getAns2().isHide()){
                    ansB.setTextColor(colorNormal);
                    b.setTextColor(colorNormal);
                    b.setBackground(drawableNormal);
                    question.getAns2().setChoose(false);
                }
                if(!question.getAns4().isHide()){
                    ansD.setTextColor(colorNormal);
                    d.setTextColor(colorNormal);
                    d.setBackground(drawableNormal);
                    question.getAns4().setChoose(false);
                }
                break;
            case R.id.ansd:
            case R.id.img_ansd:
            case R.id.tv_ansd:
                if(question.getAns4().isHide()) return;
                question.getAns4().setChoose(true);

                ansD.setTextColor(colorSelected);
                d.setBackground(drawableSelected);
                d.setTextColor(colorWhite);

                if(!question.getAns1().isHide()){
                    ansA.setTextColor(colorNormal);
                    a.setTextColor(colorNormal);
                    a.setBackground(drawableNormal);
                    question.getAns1().setChoose(false);
                }
                if(!question.getAns2().isHide()){
                    ansB.setTextColor(colorNormal);
                    b.setTextColor(colorNormal);
                    b.setBackground(drawableNormal);
                    question.getAns2().setChoose(false);
                }
                if(!question.getAns3().isHide()){
                    ansC.setTextColor(colorNormal);
                    c.setTextColor(colorNormal);
                    c.setBackground(drawableNormal);
                    question.getAns3().setChoose(false);
                }
                break;
            case R.id.visivility_a:
                isHideA = !isHideA;
                ansA.setTextColor((isHideA)?colorHide:colorNormal);
                a.setTextColor((isHideA)?colorHide:colorNormal);
                a.setBackground(drawableNormal);
                hideA.setImageResource((isHideA)?R.drawable.eye:R.drawable.ic_visibility_outline);
                question.getAns1().setHide(isHideA);
                question.getAns1().setChoose(false);
                break;
            case R.id.visivility_b:
                isHideB = !isHideB;
                ansB.setTextColor((isHideB)?colorHide:colorNormal);
                b.setTextColor((isHideB)?colorHide:colorNormal);
                b.setBackground(drawableNormal);
                hideB.setImageResource((isHideB)?R.drawable.eye:R.drawable.ic_visibility_outline);
                question.getAns2().setHide(isHideB);
                question.getAns2().setChoose(false);
                break;
            case R.id.visivility_c:
                isHideC = !isHideC;
                ansC.setTextColor((isHideC)?colorHide:colorNormal);
                c.setTextColor((isHideC)?colorHide:colorNormal);
                c.setBackground(drawableNormal);
                hideC.setImageResource((isHideC)?R.drawable.eye:R.drawable.ic_visibility_outline);
                question.getAns3().setHide(isHideC);
                question.getAns3().setChoose(false);
                break;
            case R.id.visivility_d:
                isHideD = !isHideD;
                ansD.setTextColor((isHideD)?colorHide:colorNormal);
                d.setTextColor((isHideD)?colorHide:colorNormal);
                d.setBackground(drawableNormal);
                hideD.setImageResource((isHideD)?R.drawable.eye:R.drawable.ic_visibility_outline);
                question.getAns4().setHide(isHideD);
                question.getAns4().setChoose(false);
                break;
        }
        if(question.getAns1().isChoose() ||
                question.getAns2().isChoose() ||
                question.getAns3().isChoose() ||
                question.getAns4().isChoose()){
            if(question.isChoose()) return;
            question.setChoose(true);
            numAnswered++;
            tvAnswered.setText("Đã làm "+numAnswered+"/"+maxNumQuest);
            setTabTextSize(tabItem,22,R.color.primaryColor);
        } else {
            if(!question.isChoose()) return;
            question.setChoose(false);
            numAnswered--;
            tvAnswered.setText("Đã làm "+numAnswered+"/"+maxNumQuest);
            setTabTextSize(tabItem,22,R.color.textTab);
        }
    }
}