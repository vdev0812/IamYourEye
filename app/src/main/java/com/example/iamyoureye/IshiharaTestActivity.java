package com.example.iamyoureye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class IshiharaTestActivity extends AppCompatActivity {

    LinearLayout btn_back;
    AppCompatButton btn_answer_1, btn_answer_2, btn_answer_3, btn_answer_4, btn_next;
    TextView tv_point, tv_manual, tv_result;
    ImageView imv_ishihara;
    ArrayList<Ishihara> ishihara24List = new ArrayList<>();
    public static ArrayList<String> answerList = new ArrayList<>();
    public static ArrayList<Boolean> resultList = new ArrayList<>();
    public static ArrayList<String> explainResultList = new ArrayList<>();
    ConstraintLayout layout_button_1, layout_button_2;
//    public static int person_normal =0;
//    public static int person_red_green_deficiency = 0;
//    public static int person_protanopia = 0;
//    public static int person_deuteranopia  = 0;
//    public static int person_total_color_blindness  = 0;
//    public static int person_lie  = 0;
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
        layout_button_1 = findViewById(R.id.layout_button_1);
        layout_button_2 = findViewById(R.id.layout_button_2);
        btn_next = findViewById(R.id.btn_next);
        tv_result = findViewById(R.id.tv_result);

        btn_back.setOnClickListener(v -> finish());

        addListQuestion();

        addExplainResult();

        ans_num =0;
        ans_correct =0;


        start();

    }

    private void addExplainResult() {
        explainResultList.add("Everyone should see number 12.");
        explainResultList.add("Normal view: 8.\n\nRed-green deficiency: 3.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 29.\n\nRed-green deficiency:70.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 5.\n\nRed-green deficiency: 2.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 3.\n\nRed-green deficiency: 5.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 15.\n\nRed-green deficiency: 17.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 74.\n\nRed-green deficiency: 21.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 6.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 45.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 5.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 7.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 16.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 73.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view or Total color blindness: don’t see anything.\n\nRed-green deficiency: 5.");
        explainResultList.add("Normal view or Total color blindness: don’t see anything.\n\nRed-green deficiency: 45.");
        explainResultList.add("Normal view: 26.\n\nProtanopia: 6.\n\nDeuteranopia: 2.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: 42.\n\nProtanopia: 2.\n\nDeuteranopia: 4.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: Both.\n\nProtanopia: Purple line.\n\nDeuteranopia: Red line.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: Nothing.\n\nRed-green deficiency: A line.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: A line.\n\nOther cases: Anything.");
        explainResultList.add("Normal view: An orange line.\n\nOther cases: Anything.");
        explainResultList.add("Normal view: A yellow-green line.\n\nRed-green deficiency: A green-bluish line and red.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Normal view: An orange/red line.\n\nRed-green deficiency: A green-bluish line and red.\n\nTotal color blindness: don’t see anything.");
        explainResultList.add("Everyone should see a line.");
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
        ishihara24List.add(new Ishihara(R.drawable.ishihara_19, "Nothing","Greenish line", "red line", "many line","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_20, "A line","A line", "A star", "A spiral","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_21, "An orange line","A red circle", "A green circle", "An orange line","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_22, "A yellow-green line","A red line", "A green-bluish line", "A green-bluish/red line","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_23, "An orange/red line","A blue-greenish or red line", "An orange/red line", "A yellow-greenish line","Nothing" ));
        ishihara24List.add(new Ishihara(R.drawable.ishihara_24, "A orange line","A orange line", "A green line", "Both","Nothing" ));
    }

    private void start() {
        tv_point.setText("Question "+(ans_num+1) + " / " + ishihara24List.size());
        generate(ans_num);
    }

    private void generate(int ans_num) {
        btn_answer_1.setText(ishihara24List.get(ans_num).getAnswer_1());
        btn_answer_2.setText(ishihara24List.get(ans_num).getAnswer_2());
        if(ans_num == 0)
            tv_manual.setVisibility(View.VISIBLE);
        else
            tv_manual.setVisibility(View.GONE);

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



    public void next(View view) {
        layout_button_1.setVisibility(View.VISIBLE);
        layout_button_2.setVisibility(View.VISIBLE);
        tv_result.setVisibility(View.GONE);
        btn_next.setVisibility(View.GONE);
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

    public void select(View view) {
        String answer = ((AppCompatButton) view).getText().toString().trim();
        String correct = ishihara24List.get(ans_num).getCorrect_answer();
        answerList.add(answer);

        if(answer.equals(correct)){
            ans_correct++;
            resultList.add(true);
//            person_normal++;
//            if(ans_num == 13 || ans_num == 14)
//                person_total_color_blindness++;
//            if(ans_num == 18)
//                person_total_color_blindness++;
        }else {
            resultList.add(false);
//            resultWrongAnswer(answer);
        }
        layout_button_1.setVisibility(View.GONE);
        layout_button_2.setVisibility(View.GONE);
        tv_result.setVisibility(View.VISIBLE);
        btn_next.setVisibility(View.VISIBLE);
        tv_result.setText(explainResultList.get(ans_num));


    }

//    private void resultWrongAnswer(String answer) {
//        switch (ans_num){
//            case 0:
//                person_lie++;
//                break;
//            case 1:
//                if(answer == "3")
//                    person_red_green_deficiency++;
//                else
//                    person_total_color_blindness++;
//                break;
//            case 2:
//                if(answer == "70")
//                    person_red_green_deficiency++;
//                else
//                    person_total_color_blindness++;
//                break;
//            case 3:
//                if(answer == "2")
//                    person_red_green_deficiency++;
//                else
//                    person_total_color_blindness++;
//                break;
//            case 4:
//                if(answer == "5")
//                    person_red_green_deficiency++;
//                else
//                    person_total_color_blindness++;
//                break;
//            case 5:
//                if(answer == "17")
//                    person_red_green_deficiency++;
//                else
//                    person_total_color_blindness++;
//                break;
//            case 6:
//                if(answer == "21")
//                    person_red_green_deficiency++;
//                else
//                    person_total_color_blindness++;
//                break;
//            case 7:
//            case 8:
//            case 9:
//            case 10:
//            case 11:
//            case 12:
//            case 19:
//            case 20:
//                person_total_color_blindness++;
//                person_deuteranopia++;
//                person_red_green_deficiency++;
//                person_protanopia++;
//                break;
//            case 13:
//                if(answer == "5")
//                    person_red_green_deficiency++;
//                break;
//            case 14:
//                if(answer == "45")
//                    person_red_green_deficiency++;
//                break;
//            case 15:
//                if(answer == "6")
//                    person_protanopia++;
//                else if(answer == "2")
//                    person_deuteranopia++;
//                break;
//            case 16:
//                if(answer == "2")
//                    person_protanopia++;
//                else if(answer == "4")
//                    person_deuteranopia++;
//                break;
//            case 17:
//                if(answer == "Purple line")
//                    person_protanopia++;
//                else if(answer == "Red line")
//                    person_deuteranopia++;
//                break;
//            case 18:
//                if(answer == "Greenish line")
//                    person_protanopia++;
//                else if(answer == "Red line")
//                    person_deuteranopia++;
//                else
//                    person_red_green_deficiency++;
//                break;
//            case 21:
//                if(answer == "A green-bluish line")
//                    person_protanopia++;
//                else if(answer == "Purple line")
//                    person_deuteranopia++;
//                else
//                    person_total_color_blindness++;
//                break;
//            case 22:
//                if(answer == "A blue-greenish or red line")
//                    person_red_green_deficiency++;
//                else if(answer == "A yellow-greenish line")
//                    person_deuteranopia++;
//                else
//                    person_total_color_blindness++;
//                break;
//            case 23:
//                person_lie++;
//                break;
//
//        }
//    }
}