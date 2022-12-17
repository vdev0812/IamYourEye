package com.example.iamyoureye.farnsworth_munsell_100.data;

import android.graphics.Color;

import com.example.iamyoureye.farnsworth_munsell_100.model.ColoredBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColoredBoxData {
  
  // Colors data taken manually from https://www.xrite.com/hue-test for learning purposes
  private static final String[] COLORS = {
          "#ac7773", "#ab746d", "#ab7366", "#aa7468", "#a67161", "#a5725f", "#a4715c", "#a47259", "#a27355", "#a27554", "#a37752", "#a37951", "#a47b4f", "#a37b48", "#a17e48", "#a18148", "#9e8449", "#998347", "#9c8a4c", "#9a8a4c", "#95894c", "#8f8c49",// row 1
          "#8f8c49", "#8e8f56", "#8b9058", "#88915c", "#859360", "#809262", "#7e9666", "#7e9567", "#79966a", "#75956c", "#71986c", "#6a9871", "#699973", "#689a77", "#609777", "#5a947b", "#599684", "#589681", "#589681", "#569685", "#549688", "#549987",// row 2
          "#549688", "#4f988d", "#50978f", "#499892", "#449994", "#4a9996", "#4c9898", "#509a9d", "#5497a0", "#5496a2", "#5896a3", "#6095a5", "#6696a4", "#6792a5", "#6c92a9", "#6b8da6", "#7290aa", "#728daa", "#758da9", "#798aa8", "#7a85a3", "#8188a5",// row 3
          "#8188a5", "#8489a6", "#8887a6", "#8b88a5", "#8e88a4", "#9187a2", "#9386a0", "#9886a0", "#98849d", "#9a8197", "#9f8298", "#9d7c91", "#a37d92", "#a77e90", "#a77b8c", "#a87989", "#aa7884", "#ac7982", "#ac747d", "#ac757a", "#a97275", "#ad7775"// row 4
  };
  
  public static List<ColoredBox> getAll() {
    List<ColoredBox> data = new ArrayList<>();
    for (int i = 0; i < COLORS.length; i++) {
      int color = Color.parseColor(COLORS[i]);
      int value = i == 0 ? 88 : i;
      data.add(new ColoredBox(color, i, value));
    }
    
    // Shuffling boxes per row except the keys
    int t = 0;
    for (int i = 0; i < 4; i++) {
      Collections.shuffle(data.subList(1 + t, 21 + t)); // 1 - (8+1)
      t += 22;
    }
    return Collections.unmodifiableList(data);
  }
}
