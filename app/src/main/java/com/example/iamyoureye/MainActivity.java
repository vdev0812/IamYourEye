package com.example.iamyoureye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ;
    ViewPager2 mViewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        mViewPager2 = findViewById(R.id.viewPager2);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        mViewPager2.setAdapter(viewPagerAdapter);
        mViewPager2.setUserInputEnabled(false);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.action_test_eyes)
                mViewPager2.setCurrentItem(0,true);
            else if(id == R.id.action_detect_color)
                mViewPager2.setCurrentItem(1,true);
            return true;
        });
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_detect_color).setChecked(true);
                        break;
                    default:
                        bottomNavigationView.getMenu().findItem(R.id.action_test_eyes).setChecked(true);
                        break;
                }
                changeColor(1);
            }
        });
    }

    public void changeColor(int type) {
        if(type == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                & ~ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else if(type == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

}