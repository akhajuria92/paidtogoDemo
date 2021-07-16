package com.aaronevans.paidtogo.ui.main.stats;


import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class MyXAxisValueFormatter extends ValueFormatter {

    private String[] mValues;

    public MyXAxisValueFormatter(String[] values) {
        this.mValues = values;
    }


    @Override
    public String getFormattedValue(float value) {
        int intValue = (int) value;

        if (mValues.length > intValue && intValue >= 0) return mValues[intValue];

        return "";
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
      //  return mValues[(int) value];
        int intValue = (int) value;

        if (mValues.length > intValue && intValue >= 0) return mValues[intValue];

        return "";
    }

    /** this is only needed if numbers are returned, else return 0 */

}