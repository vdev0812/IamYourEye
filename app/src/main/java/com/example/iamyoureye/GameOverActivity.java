package com.example.iamyoureye;

import static com.example.iamyoureye.IshiharaTestActivity.answerList;
import static com.example.iamyoureye.IshiharaTestActivity.resultList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity {

    TextView tv_point;
    private ArrayList<String> explainList = new ArrayList<>();
    private ArrayList<ResultIshihara> resultIshiharasList;
    ListView listView;
    MyArrayAdapter myArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        tv_point = findViewById(R.id.tv_point);
        listView = findViewById(R.id.listView);
        int p = getIntent().getExtras().getInt("points");

        tv_point.setText("Test result: "+p+"/24 Point");

        addExplain();

        resultIshiharasList = new ArrayList<>();
        addResult();
        myArrayAdapter = new MyArrayAdapter(this, R.layout.layout_item_result, resultIshiharasList, new OnClickItemInterface() {
            @Override
            public boolean itemClick(int img, int position) {
                androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(GameOverActivity.this, R.style.Style_Dialog_Rounded_Corner);
                LayoutInflater inflater = GameOverActivity.this.getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialog_result,null);
                dialog.setView(view1);

                AlertDialog optionDialog =  dialog.show();
                ImageView imageView = view1.findViewById(R.id.imv_ishihara);
                TextView tv_explain = view1.findViewById(R.id.tv_explain);
                imageView.setImageResource(img);
                tv_explain.setText(explainList.get(position));
                AppCompatButton btn_ok = view1.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionDialog.dismiss();
                    }
                });
                return true;
            }
        });
        listView.setAdapter(myArrayAdapter);
    }

    private void addResult() {
        for(int i=0; i< 24;i++){
            String explain;
            if(resultList.get(i)){
                explain = "Correct";
            }else{
                explain = explainList.get(i);
            }
            resultIshiharasList.add(new ResultIshihara(R.drawable.ishihara_1, answerList.get(i), explain));
        }
        resultIshiharasList.get(1).setImg(R.drawable.ishihara_2);
        resultIshiharasList.get(2).setImg(R.drawable.ishihara_3);
        resultIshiharasList.get(3).setImg(R.drawable.ishihara_4);
        resultIshiharasList.get(4).setImg(R.drawable.ishihara_5);
        resultIshiharasList.get(5).setImg(R.drawable.ishihara_6);
        resultIshiharasList.get(6).setImg(R.drawable.ishihara_7);
        resultIshiharasList.get(7).setImg(R.drawable.ishihara_8);
        resultIshiharasList.get(8).setImg(R.drawable.ishihara_9);
        resultIshiharasList.get(9).setImg(R.drawable.ishihara_10);
        resultIshiharasList.get(10).setImg(R.drawable.ishihara_11);
        resultIshiharasList.get(11).setImg(R.drawable.ishihara_12);
        resultIshiharasList.get(12).setImg(R.drawable.ishihara_13);
        resultIshiharasList.get(13).setImg(R.drawable.ishihara_14);
        resultIshiharasList.get(14).setImg(R.drawable.ishihara_15);
        resultIshiharasList.get(15).setImg(R.drawable.ishihara_16);
        resultIshiharasList.get(16).setImg(R.drawable.ishihara_17);
        resultIshiharasList.get(17).setImg(R.drawable.ishihara_18);
        resultIshiharasList.get(18).setImg(R.drawable.ishihara_19);
        resultIshiharasList.get(19).setImg(R.drawable.ishihara_20);
        resultIshiharasList.get(20).setImg(R.drawable.ishihara_21);
        resultIshiharasList.get(21).setImg(R.drawable.ishihara_22);
        resultIshiharasList.get(22).setImg(R.drawable.ishihara_23);
        resultIshiharasList.get(23).setImg(R.drawable.ishihara_24);
    }


    private  void addExplain(){
        explainList.add("All people should see the number 12, including those with total color blindness. If someone said they can't see something or saw something else - they are fibbing.");
        explainList.add("Those with normal color vision see an 8. Those with red-green deficiency see a 3. Those with total color blindness and weakness are not able to read any numeral.");
        explainList.add("Those with normal color vision see an 29. Those with red-green deficiency see a 70. Those with total color blindness and weakness are not able to read any numeral.");
        explainList.add("Those with normal color vision see an 5. Those with red-green deficiency see a 2. Those with total color blindness and weakness are not able to read any numeral.");
        explainList.add("Those with normal color vision see an 3. Those with red-green deficiency see a 5. Those with total color blindness and weakness are not able to read any numeral.");
        explainList.add("Those with normal color vision see an 15. Those with red-green deficiency see a 17. Those with total color blindness and weakness are not able to read any numeral.");
        explainList.add("Those with normal color vision see an 74. Those with red-green deficiency see a 21. Those with total color blindness and weakness are not able to read any numeral.");
        explainList.add("Those with normal color vision see an 6. The majority of those with color vision deficiencies are not able to read it or read it incorrectly.");
        explainList.add("Those with normal color vision see an 45. The majority of those with color vision deficiencies are not able to read it or read it incorrectly.");
        explainList.add("Those with normal color vision see an 5. The majority of those with color vision deficiencies are not able to read it or read it incorrectly.");
        explainList.add("Those with normal color vision see an 7. The majority of those with color vision deficiencies are not able to read it or read it incorrectly.");
        explainList.add("Those with normal color vision see an 16. The majority of those with color vision deficiencies are not able to read it or read it incorrectly.");
        explainList.add("Those with normal color vision see an 73. The majority of those with color vision deficiencies are not able to read it or read it incorrectly.");
        explainList.add("The majority of normal and those with total color blindness and weakness total color blindness and weakness are not able to read any numeral. The majority of those with red-green deficiencies should see a 5.");
        explainList.add("The majority of normal and those with total color blindness and weakness total color blindness and weakness are not able to read any numeral. The majority of those with red-green deficiencies should see a 45.");
        explainList.add("Those with normal color vision should see a 26. Red color blind (protanopia) people will see a 6, and mild red color blind people (mild protanomaly) will also faintly see a number 2. Green color blind (deuteranopia) people will see a 2, mild green color blind people (mild deuteranomaly) may also faintly see a number 6.");
        explainList.add("Those with normal color vision should see a 42. Red color blind (protanopia) people will see a 2, and mild red color blind people (mild protanomaly) will also faintly see a number 4. Green color blind (deuteranopia) people will see a 4, mild green color blind people (mild deuteranomaly) may also faintly see a number 2.");
        explainList.add("Those with normal color vision should be able to trace along both the purple and red lines. In protanopia (red color blind) and strong protanomaly only the purple line is traced, and in case of mild protanomaly both lines both lines are traced but the purple line is easier to follow. In deuteranopia (green color blind) and strong deuteranomaly only the red line is traced, and in case of mild deuteranomaly both lines both lines are traced but the red line is easier to follow.");
        explainList.add("The majority of normal and those with total color blindness and weakness are unable to follow the line. The majority of those with red-green deficiencies can trace the wiggly line, depending on the severity of the condition.");
        explainList.add("Those with normal color vision should be able to trace a green wiggly line. The majority of those with color vision deficiencies are unable to follow the line or follow a line different from the normal one.");
        explainList.add("Those with normal color vision should be able to trace an orange wiggly line. The majority of those with color vision deficiencies are unable to follow the line or follow a line different from the normal one.");
        explainList.add("Those with normal color vision should be able to trace the yellowish-green wiggly line. Those with red-green deficiencies trace the line connecting the bluesh-green and purple and those with total color blindness and weakness are not able to trace the line.");
        explainList.add("Those with normal color vision should be able to trace the red and orange wiggly line. Red-green deficiencies people will trace the red and blue-green wiggly line. People with total color blindness will be unable to trace any line.");
        explainList.add("Everyone should be able to trace this wiggly line, including those with total color blindness. lf someone said they can't see something, or saw something else - they are fibbing!");
    }
}