package ldev.ptithcm.pexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pexam.R;
import ldev.ptithcm.pexam.model.Subject;

import java.util.List;

public class GridSubjectAdapter extends BaseAdapter {

    private List<Subject> subjects;
    private Context context;
    private LayoutInflater inflater;

    public GridSubjectAdapter(Context context,List<Subject> subjects) {
        this.subjects = subjects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int i) {
        return subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_subj,viewGroup,false);
        TextView tvNameSub,tvNumPart;
        tvNameSub = view.findViewById(R.id.tv_name_subj);
        tvNumPart = view.findViewById(R.id.tv_num_part);
        tvNameSub.setText(subjects.get(i).getNameSub());
        tvNumPart.setText(subjects.get(i).getNumPart()+" phần");
        return view;
    }
}
