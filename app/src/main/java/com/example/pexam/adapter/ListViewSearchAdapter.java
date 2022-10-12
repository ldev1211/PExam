package com.example.pexam.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.example.pexam.R;
import com.example.pexam.database.Database;
import com.example.pexam.model.QuestionSearch;
import java.util.List;

public class ListViewSearchAdapter extends BaseAdapter {
    Context context;
    List<QuestionSearch> questions;
    LayoutInflater inflater;
    Database database;

    public ListViewSearchAdapter(Context context,List<QuestionSearch> list) {
        this.context = context;
        this.questions = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.database = new Database(context.getApplicationContext(),"PExamSQLite.sqlite",null,1);
    }

    @Override
    public int getCount() {
        if (questions != null) {
            return Math.min(questions.size(), 5);
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    TextView searchedString;
    String[] originalWords;
    String[] words;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_search,viewGroup,false);
        searchedString = view.findViewById(R.id.tv_search_string);
        String original = questions.get(i).getContentQuestion();
        for(int incr=0;incr<originalWords.length;++incr){
            String replaceWith = "<b><span style='color:black'>"+originalWords[incr]+"</span></b>";
            original = original.replaceAll(originalWords[incr],replaceWith);
        }
        searchedString.setText(Html.fromHtml(original));
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filter(String stringKey){
        String searchText = stringKey.toLowerCase();
        questions.clear();
        if(searchText.length()==0) {
            notifyDataSetChanged();
            return;
        }
        originalWords = stringKey.split(" ");
        words = searchText.split(" ");
        String conditionQuery = "";
        int len = words.length;
        for(int i = 0; i < len; ++i){
            if(i == words.length-1) conditionQuery += "question LIKE '%"+words[i]+"%'";
            else conditionQuery += "question LIKE '%"+words[i]+"%' OR ";
        }
        Cursor cursor;
        cursor = database.getData("SELECT * FROM AllDetailQuestion WHERE "+conditionQuery);
        while (cursor.moveToNext()){
            int frequency = 0;
            String question = cursor.getString(3);
            for(int i=0;i<originalWords.length;++i){
                if(question.contains(originalWords[i])) frequency++;
            }
            questions.add(new QuestionSearch(question,
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getInt(9) == 1,
                    frequency)
            );
        }
        questions.sort((a,b)-> Integer.compare(b.getFrequency(), a.getFrequency()));
        notifyDataSetChanged();
    }
}
