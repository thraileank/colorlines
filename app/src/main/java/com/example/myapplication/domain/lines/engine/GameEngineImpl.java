package com.example.myapplication.domain.lines.engine;

import com.example.myapplication.domain.lines.entities.Ball;
import com.example.myapplication.domain.lines.entities.BallColors;
import com.example.myapplication.domain.lines.entities.BallStates;
import com.example.myapplication.domain.lines.entities.Turn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameEngineImpl implements GameEngine {

    @Override
    public void makeTurn(int ballPosition, int desiredPosition, TurnProcessingCallback callback) {

    }

    @Override
    public Ball[] populateField(int activeBallsCount, int appearingBallsCount) {
        return new Ball[] {};
    }
}
