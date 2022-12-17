package com.example.iamyoureye;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<ResultIshihara> {

    Activity context;
    int idLayout;
    ArrayList<ResultIshihara> myList;
    OnClickItemInterface onClick;

    public MyArrayAdapter(Activity context, int idLayout, ArrayList<ResultIshihara> myList, OnClickItemInterface onClick) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflactor = context.getLayoutInflater();
        convertView = myInflactor.inflate(idLayout,null);
        ResultIshihara myResultIshihara = myList.get(position);
        ImageView img = convertView.findViewById(R.id.view1);
        TextView tv_answer = convertView.findViewById(R.id.tv_answer);
        TextView tv_explain = convertView.findViewById(R.id.tv_explain);
        LinearLayout postLayout = convertView.findViewById(R.id.postLayout);

        img.setImageResource(myResultIshihara.getImg());
        tv_answer.setText(myResultIshihara.getAnswer());
        tv_explain.setText(myResultIshihara.getExplain());

        postLayout.setOnClickListener(v -> onClick.itemClick(myResultIshihara.getImg(), position));

        return  convertView;
    }
}
