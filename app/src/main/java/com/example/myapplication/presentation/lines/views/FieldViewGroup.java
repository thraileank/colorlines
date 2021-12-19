package com.example.myapplication.presentation.lines.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class FieldViewGroup extends GridLayout {
    private BallView selectedBall = null;

    public FieldViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        setColumnCount(9);
        setRowCount(9);
        setAlignmentMode(GridLayout.ALIGN_BOUNDS);

        for (int i = 0; i < 81; i++) {
            ImageView tile = new ImageView(context);
            tile.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.tile));
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.height = 0;
            layoutParams.width = 0;
            int currentCol = i % 9;
            int currentRow = i / 9;
            // The last parameter in the specs is the weight, which gives equal size to the cells
            layoutParams.columnSpec = GridLayout.spec(currentCol, 1, 1);
            layoutParams.rowSpec = GridLayout.spec(currentRow, 1, 1);
            addView(tile, layoutParams);
        }
    }

    public void setSelectedBall(BallView ball) {
        selectedBall = ball;
    }
}
