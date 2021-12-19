package com.example.myapplication.presentation.lines;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.domain.lines.engine.GameEngine;
import com.example.myapplication.domain.lines.engine.GameEngineImpl;
import com.example.myapplication.domain.lines.engine.TurnProcessingCallback;
import com.example.myapplication.domain.lines.entities.Ball;
import com.example.myapplication.domain.lines.entities.Turn;

import java.util.List;

public class LinesViewModel extends ViewModel implements TurnProcessingCallback {

    private final GameEngine engine;

    public MutableLiveData<List<Ball>> ballsToAppear_ = new MutableLiveData<>();
    public MutableLiveData<List<Integer>> ballsToMakeActive_ = new MutableLiveData<>();
    public MutableLiveData<List<Integer>> ballsToDestroy_ = new MutableLiveData<>();
    public MutableLiveData<Integer> score_ = new MutableLiveData<>();
    public MutableLiveData<int[]> path_ = new MutableLiveData<>();

    public LinesViewModel() {
        engine = new GameEngineImpl();
    }

    public Ball[] getInitialBallsData() {
        return engine.populateField(3, 3);
    }

    public void makeTurn(int ballPosition, int desiredPosition) {
        engine.makeTurn(ballPosition, desiredPosition, this);
    }

    @Override
    public void pathCalculated(int[] path) {
        path_.setValue(path);
    }

    @Override
    public void turnCalculated(Turn turn) {
        score_.setValue(turn.getScore());
        ballsToAppear_.setValue(turn.getBallsToAppear());
        ballsToMakeActive_.setValue(turn.getBallsToMakeActive());
        ballsToDestroy_.setValue(turn.getBallsToDestroy());
    }

    @Override
    public void invalidTurn() {
        path_.setValue(new int[] {});
    }
}