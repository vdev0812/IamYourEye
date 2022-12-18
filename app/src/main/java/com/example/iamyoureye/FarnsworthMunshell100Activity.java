package com.example.iamyoureye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.iamyoureye.farnsworth_munsell_100.data.ColoredBoxData;
import com.example.iamyoureye.farnsworth_munsell_100.model.ColoredBox;
import com.example.iamyoureye.farnsworth_munsell_100.utils.ColoredBoxAdapter;
import com.example.iamyoureye.farnsworth_munsell_100.utils.ColoredBoxTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class FarnsworthMunshell100Activity extends AppCompatActivity {

    private static final int[] ACTION_MENUS = {
            R.string.label_menu_check,
            R.string.label_menu_test_again
    };

    private boolean isDraggable = true;
    private boolean isReallyExit = false;

    private List<ColoredBox> mColoredBoxes;
    private ColoredBoxAdapter mColoredBoxAdapter;
    private ItemTouchHelper mItemTouchHelper;
    LinearLayout btn_check, btn_back, btn_more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farnsworth_munshell100);

        btn_check = findViewById(R.id.btn_check);
        btn_back = findViewById(R.id.btn_back);
        btn_more = findViewById(R.id.btn_more);

        btn_check.setOnClickListener(v -> onCheckResult());
        btn_more.setOnClickListener(v -> onTestAgain());
        btn_back.setOnClickListener(v -> finish());

        // Initialize adapter
        mColoredBoxes = new ArrayList<>();
        mColoredBoxAdapter = new ColoredBoxAdapter(mColoredBoxes, this::onColoredBoxStartDrag);
        mColoredBoxAdapter.setHasStableIds(true);

        // Initialize test RecyclerView using GridLayoutManager
        RecyclerView mRecyclerView = findViewById(R.id.test_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mColoredBoxAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 22);
        mRecyclerView.setLayoutManager(manager);

        // Initialize adapter with item touch listener
        ItemTouchHelper.Callback callback = new ColoredBoxTouchHelper(mColoredBoxAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        for (int i = 0; i < ACTION_MENUS.length; i++) {
            menu.add(0, i, i, ACTION_MENUS[i]).setShowAsAction(
                    i == 0 ? MenuItem.SHOW_AS_ACTION_IF_ROOM : MenuItem.SHOW_AS_ACTION_NEVER
            );
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        populateColoredBoxData();
        super.onResume();
    }

    // Populate data
    private void populateColoredBoxData() {
        mColoredBoxes.clear();
        mColoredBoxes.addAll(ColoredBoxData.getAll());
        mColoredBoxAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        // Handling user touch back on Android navigation button
        if (isReallyExit) super.onBackPressed();
        Toast.makeText(this, R.string.confirm_exit, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> isReallyExit = false, 2000);
        isReallyExit = true;
    }

    // Handling colored box drag
    private void onColoredBoxStartDrag(RecyclerView.ViewHolder holder) {
        if (isDraggable) mItemTouchHelper.startDrag(holder);
    }

    // Showing result and disable boxes to move
    private void onCheckResult() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.warning)
                .setMessage(R.string.check_confirm)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.label_menu_check, (d, w) -> {
                    isDraggable = false;
                    mColoredBoxAdapter.showValue(true);
                    onCalculateResult();
                }).create();
        dialog.show();
    }

    /**
     * Calculate error node using Farnsworth Munsell formula
     * Formula I've learn from this <a href="https://www.xritephoto.com/documents/literature/gmb/en/gmb_fm100_instructions_en.pdf">document</a>
     * The Farnsworth-Munsell 100-Hue Test for the examination of Color Discrimination by Dean Farnsworth revisied 1957
     */
    private void onCalculateResult() {
        // NOTE: Better not do this in view layer
        int[] val = new int[mColoredBoxes.size()];
        val[0] = 0;
        for (int i = 0; i < val.length - 2; i++) {
            int valLeft = 0, valRight = 0;
            int left = mColoredBoxes.get(i).position;
            int mid = mColoredBoxes.get(i + 1).position;
            int right = mColoredBoxes.get(i + 2).position;
            // Calculating error score | For detail read in document (page 5) mention above
            if (left > mid) valLeft = left - mid;
            else if (left < mid) valLeft = mid - left;
            if (mid > right) valRight = mid - right;
            else if (mid < right) valRight = right - mid;
            val[i + 1] = (valLeft + valRight) - 2;
        }
        val[val.length - 1] = 0;
        int totalErrorScore = 0;
        for (int i : val) totalErrorScore += i;
        onShowResult(totalErrorScore);
    }

    // Show the calculate result
    private void onShowResult(int totalErrorScore) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.test_result)
                .setMessage(getString(R.string.result_details, totalErrorScore))
                .setNegativeButton(R.string.close, (d, w) -> d.dismiss())
                .setPositiveButton(R.string.label_menu_test_again, (d, w) -> onTestAgain())
                .create();
        dialog.show();
    }

    // Retry test
    private void onTestAgain() {
        isDraggable = true;
        mColoredBoxAdapter.showValue(false);
        populateColoredBoxData();
    }
}