package ldev.ptithcm.pexam.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pexam.R;
import ldev.ptithcm.pexam.database.Database;
import ldev.ptithcm.pexam.model.QuestionSearch;
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
    String original;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_search,viewGroup,false);
        searchedString = view.findViewById(R.id.tv_search_string);
        original = questions.get(i).getContentQuestion();
//        originalWords = original.split(" ");
//        for(int incr=0;incr<originalWords.length;++incr){
//            String replaceWith = "<b><span style='color:black'>"+originalWords[incr]+"</span></b>";
//            Log.d("TAG", "getView: "+replaceWith+" | "+originalWords[incr]+" length = "+originalWords.length+" | incr = "+incr);
//            original = original.replaceAll(originalWords[incr],replaceWith);
//        }
        searchedString.setText(Html.fromHtml(original));
        return view;
    }
}
