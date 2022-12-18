package com.example.iamyoureye;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class EyeTestFragment extends Fragment {

    AppCompatButton btn_ishihara, btn_farnsworth_munsell;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eye_test, container, false);

        btn_ishihara = view.findViewById(R.id.btn_ishihara);
        btn_farnsworth_munsell = view.findViewById(R.id.btn_farnsworth_munsell);

        btn_ishihara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IshiharaTestActivity.class);
                startActivity(intent);
            }
        });

        btn_farnsworth_munsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FarnsworthMunshell100Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}