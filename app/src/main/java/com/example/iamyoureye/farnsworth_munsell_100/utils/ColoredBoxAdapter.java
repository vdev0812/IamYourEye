package com.example.iamyoureye.farnsworth_munsell_100.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iamyoureye.R;
import com.example.iamyoureye.farnsworth_munsell_100.model.ColoredBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ColoredBoxAdapter extends RecyclerView.Adapter<ColoredBoxAdapter.ColoredBoxHolder>
    implements ColoredBoxTouchListener {
  
  // Locked Colored Boxes to not allow to move because it's the keys
  private final List<Integer> boxKeyPositions = Arrays.asList(0, 9, 10, 19, 20, 29, 30, 39, 40, 49);
  private final List<ColoredBox> boxes;
  private final ColoredBoxDragListener listener;
  private boolean textShow = false;
  
  public ColoredBoxAdapter(List<ColoredBox> boxes, ColoredBoxDragListener listener) {
    this.boxes = boxes;
    this.listener = listener;
  }
  
  @NonNull
  @Override
  public ColoredBoxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_coloredbox, parent, false);
    return new ColoredBoxHolder(view);
  }
  
  @SuppressLint("ClickableViewAccessibility")
  @Override
  public void onBindViewHolder(@NonNull final ColoredBoxHolder h, int p) {
    h.text.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (isKey(h.getAdapterPosition())) return false;
        else if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
          listener.onStartDrag(h);
        }
        return false;
      }
    });
    h.set(boxes.get(p));
  }
  
  @Override
  public int getItemCount() {
    return boxes.size();
  }
  
  // Showing the result value
  public void showValue(boolean b) {
    this.textShow = b;
    notifyDataSetChanged();
  }
  
  private boolean isKey(int pos) {
    return boxKeyPositions.contains(pos);
  }
  
  @Override
  public boolean onItemMove(int fromPosition, int toPosition) {
    if (isKey(fromPosition) || isKey(toPosition)) return false;
    if (textShow) return false;
    Collections.swap(boxes, fromPosition, toPosition);
    notifyItemMoved(fromPosition, toPosition);
    return false;
  }
  
  /**
   * ColoredBox ViewHolder for RecycleView
   */
  class ColoredBoxHolder extends RecyclerView.ViewHolder implements ColoredBoxHolderListener {
    
    private TextView text;
    
    ColoredBoxHolder(View v) {
      super(v);
      text = v.findViewById(R.id.item_box_text);
    }
    
    private void set(ColoredBox box) {
      text.setBackgroundColor(box.color);
      text.setText(textShow ? String.valueOf(box.value) : null);
    }
    
    @Override
    public void onItemSelected() {
      itemView.setBackgroundColor(Color.BLACK);
    }
    
    @Override
    public void onItemClear() {
      itemView.setBackgroundColor(0);
    }
  }
  
  public interface ColoredBoxDragListener {
    
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
  }
  
  public interface ColoredBoxHolderListener {
    
    void onItemSelected();
    
    void onItemClear();
  }
}
