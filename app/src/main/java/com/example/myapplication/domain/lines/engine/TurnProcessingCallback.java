package com.example.myapplication.domain.lines.engine;

import com.example.myapplication.domain.lines.entities.Turn;

public interface TurnProcessingCallback {
    void pathCalculated(int[] path);
    void turnCalculated(Turn turn);
    void invalidTurn();
}
