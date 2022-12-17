package com.example.iamyoureye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class IshiharaTestActivity extends AppCompatActivity {

    LinearLayout btn_back;
    AppCompatButton btn_answer_1, btn_answer_2, btn_answer_3, btn_answer_4;
    TextView tv_point, tv_manual;
    ImageView imv_ishihara;
    ArrayList<Ishihara> ishihara24List = new ArrayList<>();
    public static ArrayList<String> answerList = new ArrayList<>();
    public static ArrayList<Boolean> resultList = new ArrayList<>();
    int ans_num;
    int ans_correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ishihara_test);

        btn_back = findViewById(R.id.btn_back);
        tv_point = findViewById(R.id.tv_point);
        tv_manual = findViewById(R.id.tv_manual);
        imv_ishihara = findViewById(R.id.imv_ishihara);
        btn_answer_1 = findViewById(R.id.btn_answer_1);
        btn_answer_2 = findViewById(R.id.btn_answer_2);
        btn_answer_3 = findViewById(R.id.btn_answer_3);
        btn_answer_4 = findViewById(R.id.btn_answer_4);

        btn_back.setOnClickListener(v -> finish());

        addListQuestion();


        ans_num =0;
        ans_correct =0;


        start();

    }

    private void addListQuestion() {
        ishihara24List.add(new Ishihara(R.drawable.ishihara_1,"12", "12", "Nothing", "0","0" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_2, "8","3", "8", "9","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_3,"29", "29", "70", "96","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_4,"5", "2", "3", "5","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_5, "3","3", "5", "6","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_6,"15", "12", "15", "17","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_7,"74", "17", "21", "74","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_8,"6", "5", "6", "8","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_9,"45", "12", "45", "73","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_10, "5","2", "3", "5","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_11,"7", "2", "4", "7","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_12, "16","16", "45", "70","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_13, "73","15", "42", "73","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_14, "Nothing","2", "5", "6","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_15, "Nothing","15", "42", "45","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_16, "26","2", "6", "26","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_17, "42","2", "4", "42","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_18, "Both","Red line", "Purple line", "Both","None" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_19, "Colored dots","Greenish line", "Blue triangle", "Colored dots","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_20, "A line","A line", "A star", "A spiral","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_21, "An orange line","A red circle", "A green circle", "An orange line","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_22, "A yellow-green line","A yellow-green line", "A green-bluish line", "Purple line","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_23, "An orange/red line","A blue-greenish or purple line", "An orange/red line", "A yellow-greenish line","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_24, "A orange line","A orange line", "A green line", "Both","Nothing" ));
    }

    private void start() {
        tv_point.setText("Question "+(ans_num+1) + " / " + ishihara24List.size());
        generate(ans_num);
    }

    private void generate(int ans_num) {
        btn_answer_1.setText(ishihara24List.get(ans_num).getAnswer_1());
        btn_answer_2.setText(ishihara24List.get(ans_num).getAnswer_2());
        if(ishihara24List.get(ans_num).getAnswer_3() != "0") {
            btn_answer_3.setVisibility(View.VISIBLE);
            btn_answer_3.setText(ishihara24List.get(ans_num).getAnswer_3());
        }else
            btn_answer_3.setVisibility(View.GONE);

        if(ishihara24List.get(ans_num).getAnswer_4() != "0") {
            btn_answer_4.setVisibility(View.VISIBLE);
            btn_answer_4.setText(ishihara24List.get(ans_num).getAnswer_4());
        }else
            btn_answer_4.setVisibility(View.GONE);

        imv_ishihara.setImageResource(ishihara24List.get(ans_num).getImg());
    }

    public void select(View view) {
        String answer = ((AppCompatButton) view).getText().toString().trim();
        String correct = ishihara24List.get(ans_num).getCorrect_answer();
        answerList.add(answer);

        if(answer.equals(correct)){
            ans_correct++;
            resultList.add(true);
        }else
            resultList.add(false);

        ans_num++;
        if (ans_num > ishihara24List.size() - 1) {
            Intent intent = new Intent(IshiharaTestActivity.this, GameOverActivity.class);
            intent.putExtra("points", ans_correct);
            startActivity(intent);
            finish();
        }
        else{
            start();
        }

    }
}