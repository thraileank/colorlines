package com.example.myapplication.domain.lines.entities;

public class Ball {
    private BallColors color;
    private int position;
    private BallStates state;

    public Ball(BallColors color, int position, BallStates state) {
        this.color = color;
        this.position = position;
        this.state = state;
    }

    public BallColors getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public BallStates getState() {
        return state;
    }

    public void setState(BallStates state) { this.state = state; }
}
