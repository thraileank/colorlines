package com.example.myapplication.domain.lines.entities;

import java.util.List;

public class Turn {
    public Turn(List<Ball> ballsToAppear, List<Integer> ballsToMakeActive, List<Integer> ballsToDestroy, int score) {
        this.ballsToAppear = ballsToAppear;
        this.ballsToMakeActive = ballsToMakeActive;
        this.ballsToDestroy = ballsToDestroy;
        this.score = score;
    }

    public List<Ball> getBallsToAppear() {
        return ballsToAppear;
    }

    public List<Integer> getBallsToMakeActive() { return ballsToMakeActive; }

    public List<Integer> getBallsToDestroy() {
        return ballsToDestroy;
    }

    public int getScore() {
        return score;
    }

    List<Ball> ballsToAppear;
    List<Integer> ballsToMakeActive;
    List<Integer> ballsToDestroy;
    int score;
}
