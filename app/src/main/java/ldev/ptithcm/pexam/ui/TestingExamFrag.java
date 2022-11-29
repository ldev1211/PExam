package ldev.ptithcm.pexam.ui;

import static ldev.ptithcm.pexam.application.ApplicationConfig.NAME_SQLITE;
import static ldev.ptithcm.pexam.application.ApplicationConfig.mSocket;
import static ldev.ptithcm.pexam.application.ApplicationConfig.numAnswered;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.pexam.R;
import ldev.ptithcm.pexam.adapter.ViewPagerTestingAdapter;
import ldev.ptithcm.pexam.database.Database;
import ldev.ptithcm.pexam.model.AnsState;
import ldev.ptithcm.pexam.model.Question;
import ldev.ptithcm.pexam.model.TimeCountDown;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.socket.emitter.Emitter;

public class TestingExamFrag extends Fragment {

    private static final String TAG = "state";
    TextView tvProgress,tvTimerCountDown,tvSubmit;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ImageView imageViewBack;
    TabLayoutMediator mediator;
    List<Question> questions;
    ViewPagerTestingAdapter adapter;
    TimerCountDownThread timerCountDownThread;
    View view;
    ProgressBar progressBar;
    Database database;
    Cursor cursor;
    float secondCDExam,secondCDExamCurr,count=0;
    String nameKind,nameSubj,namePart;
    SharedPreferences sharedPreferences;
    String codePart;
    boolean clickSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_testing_exam, container, false);
        tvProgress = view.findViewById(R.id.tv_progress);
        clickSubmit = false;
        tvTimerCountDown = view.findViewById(R.id.tv_count_down_timer);
        viewPager2 = view.findViewById(R.id.pager_question);
        progressBar = view.findViewById(R.id.progress_count_down);
        tabLayout = view.findViewById(R.id.tablayout_question);
        imageViewBack = view.findViewById(R.id.img_back);
        tvSubmit = view.findViewById(R.id.tv_submit);
        numAnswered = 0;
        database = new Database(getContext(),NAME_SQLITE,null,1);
        sharedPreferences = requireActivity().getSharedPreferences("stateApplication", Context.MODE_PRIVATE);
        nameKind = sharedPreferences.getString("nameKindCurr",null);
        nameSubj = sharedPreferences.getString("nameSubjCurr",null);
        namePart = sharedPreferences.getString("namePart",null);
        secondCDExam = sharedPreferences.getInt("time",0);
        codePart = sharedPreferences.getString("codePartCurr",null);
        questions = new ArrayList<>();
        initOn();
        initEmit();
        tvProgress.setText("Đã làm "+numAnswered+"/"+questions.size());
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabTextSize(tab,22,questions.get(tab.getPosition()).isChoose()?R.color.primaryColor:R.color.textTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabTextSize(tab,15,questions.get(tab.getPosition()).isChoose()?R.color.primaryColor:R.color.textTab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubmit = true;
                secondCDExamCurr = -1;
                Navigation.findNavController(view).navigate(R.id.toListExamFromTestingExam);
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.diaglog_submit);
                dialog.show();
                TextView tvPerNumQuestion = dialog.findViewById(R.id.tv_per_num_quest);
                TextView tvTimerCurr = dialog.findViewById(R.id.tv_timer_curr);
                TextView tvCancel = dialog.findViewById(R.id.tv_submit_no);
                TextView tvAccept = dialog.findViewById(R.id.tv_submit_yes);
                tvPerNumQuestion.setText("Đã làm "+numAnswered+"/"+questions.size()+" câu");
                TimeCountDown timeCountDown = getTimeCountDown((long) (secondCDExam - secondCDExamCurr));
                tvTimerCurr.setText((timeCountDown.getMinute()>=10)?timeCountDown.getMinute()+"":"0"+timeCountDown.getMinute()+":"+((timeCountDown.getSecond()>=10)?timeCountDown.getSecond():"0"+timeCountDown.getSecond()));
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                tvAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickSubmit = true;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("questionsWereChoose", (Serializable) questions);
                        bundle.putInt("timeCurr", (int) (secondCDExam-secondCDExamCurr));
                        secondCDExamCurr=-1;
                        dialog.cancel();
                        Navigation.findNavController(view).navigate(R.id.toConsequenceFromTestingExam,bundle);
                    }
                });
            }
        });
        timerCountDownThread = new TimerCountDownThread();
        timerCountDownThread.start();
        Log.d("state", "onCreateView: ");
        return view;
    }

    private void initEmit() {
        mSocket.emit("GET_QUESTION_TESTING",codePart);
    }

    private void initOn() {
        mSocket.on("RETURN_QUESTION_TESTING", new Emitter.Listener() {
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
                                questions.add(new Question(
                                        tmp.getString("question_content"),
                                        tmp.getString("img"),
                                        new AnsState(tmp.getString("ansa"),false,false),
                                        new AnsState(tmp.getString("ansb"),false,false),
                                        new AnsState(tmp.getString("ansc"),false,false),
                                        new AnsState(tmp.getString("ansd"),false,false),
                                        tmp.getString("ans_right"),
                                        false,
                                        false
                                ));
                            }
                            if(adapter==null){
                                adapter = new ViewPagerTestingAdapter(requireActivity(),viewPager2,questions,tabLayout,tvProgress);
                                viewPager2.setAdapter(adapter);
                                mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                                    @Override
                                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                        switch (position){
                                            case 0:
                                                tab.setCustomView(createCustomTabView(String.valueOf(position+1),22, R.color.textTab));
                                                break;
                                            default:
                                                tab.setCustomView(createCustomTabView(String.valueOf(position+1),15, R.color.textTab));
                                                break;
                                        }
                                    }
                                });
                                mediator.attach();
                            }
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
        if(secondCDExamCurr<0){
            Bundle bundle = new Bundle();
            bundle.putInt("timeCurr", (int) (secondCDExam-secondCDExamCurr));
            bundle.putSerializable("questionsWereChoose", (Serializable) questions);
            Navigation.findNavController(view).navigate(R.id.toConsequenceFromTestingExam,bundle);
        }
        requireView().setFocusableInTouchMode(true);
        requireView().requestFocus();
        requireView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    clickSubmit = true;
                    secondCDExamCurr=-1;
                    Navigation.findNavController(v).navigate(R.id.toListExamFromTestingExam);
                    return true;
                }
                return false;
            }
        });
    }
    private View createCustomTabView(String tabText, int tabSizeSp, int textColor){
        View tabCustomView = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tabTextView = tabCustomView.findViewById(R.id.tv_num_tab);
        tabTextView.setText(tabText);
        tabTextView.setTextSize(tabSizeSp);
        tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.getContext(), textColor));
        return tabCustomView;
    }

    private void setTabTextSize(TabLayout.Tab tab, int tabSizeSp, int textColor){
        View tabCustomView = tab.getCustomView();
        TextView tabTextView = tabCustomView.findViewById(R.id.tv_num_tab);
        tabTextView.setTextSize(tabSizeSp);
        tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.getContext(), textColor));
    }

    public TimeCountDown getTimeCountDown(long second){
        long minute = TimeUnit.SECONDS.toMinutes(second);
        second -= 60*minute;
        return new TimeCountDown(minute, second);
    }

    class TimerCountDownThread extends Thread{
        @Override
        public void run() {
            for(secondCDExamCurr = secondCDExam ;secondCDExamCurr>=0;--secondCDExamCurr,++count){
                TimeCountDown timeCountDownCurr = getTimeCountDown((long) secondCDExamCurr);
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTimerCountDown.setText(timeCountDownCurr.getMinute()+":"+timeCountDownCurr.getSecond());
                        progressBar.setProgress( (int)((count/secondCDExam)*100));
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Bundle bundle = new Bundle();
            bundle.putInt("timeGone", (int) secondCDExamCurr);
            if(secondCDExamCurr<0) secondCDExamCurr = 0;
            bundle.putInt("timeCurr", (int) (secondCDExam-secondCDExamCurr));
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(clickSubmit) return;
                    bundle.putSerializable("questionsWereChoose", (Serializable) questions);
                    Navigation.findNavController(view).navigate(R.id.toConsequenceFromTestingExam,bundle);
                }
            });
        }
    }
}