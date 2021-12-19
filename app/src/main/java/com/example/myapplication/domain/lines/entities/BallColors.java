package com.example.myapplication.domain.lines.entities;

import com.example.myapplication.R;

public enum BallColors {
    RED(R.color.ball_red),
    DARK_RED(R.color.ball_dark_red),
    YELLOW(R.color.ball_yellow),
    PINK(R.color.ball_pink),
    CYAN(R.color.ball_cyan),
    BLUE(R.color.ball_blue),
    GREEN(R.color.ball_green);

    private final int colorId;
    BallColors(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }
}
