package com.example.myapplication.domain.lines.engine;

import com.example.myapplication.domain.lines.entities.Ball;

public interface GameEngine {

    /**
     *
     * @param ballPosition start ball position
     * @param desiredPosition position ball should be moved to
     * @param callback callback that should be invoked when turn is fully calculated
     * @return array describing a path the ball should roll through
     */
    void makeTurn(int ballPosition, int desiredPosition, TurnProcessingCallback callback);
    Ball[] populateField(int activeBallsCount, int appearingBallsCount);
}
