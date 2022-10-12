package com.example.pexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pexam.R;
import com.example.pexam.model.GridKind;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    Context context;
    List<GridKind> listItemGrid;

    public GridAdapter(Context context, List<GridKind> listItemGrid) {
        this.context = context;
        this.listItemGrid = listItemGrid;
    }

    @Override
    public int getCount() {
        return listItemGrid.size();
    }

    @Override
    public Object getItem(int i) {
        return listItemGrid.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_grid,null);
        TextView tvNameItem = view.findViewById(R.id.tv_name_item_grid);
        TextView tvNumQuest = view.findViewById(R.id.tv_num_subj);
        ImageView imageView = view.findViewById(R.id.imgItemGrid);
        tvNumQuest.setText(listItemGrid.get(i).getNumSubj()+" môn học");
        tvNameItem.setText(listItemGrid.get(i).getName());
        imageView.setImageResource(listItemGrid.get(i).getImgItem());
        return view;
    }
}
