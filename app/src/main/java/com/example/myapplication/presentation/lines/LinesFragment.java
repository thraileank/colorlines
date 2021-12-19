package com.example.myapplication.presentation.lines;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentLinesBinding;
import com.example.myapplication.domain.lines.entities.Ball;
import com.example.myapplication.domain.lines.entities.BallStates;
import com.example.myapplication.presentation.lines.views.BallView;
import com.example.myapplication.presentation.lines.views.FieldViewGroup;

public class LinesFragment extends Fragment {

    public static int FIELD_COLUMNS_COUNT = 9;
    public static int FIELD_ROWS_COUNT = 9;

    private LinesViewModel linesViewModel;
    private FragmentLinesBinding binding;
    private final BallView[] ballViews = new BallView[81];
    private int selectedBallPosition = -1;
    private int fieldSize = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        linesViewModel =
                new ViewModelProvider(this).get(LinesViewModel.class);

        binding = FragmentLinesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initField();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Ball[] initialBallsData = linesViewModel.getInitialBallsData();
        for (Ball ballData : initialBallsData) {
            addBallView(ballData);
        }

        linesViewModel.ballsToAppear_.observe(
                getViewLifecycleOwner(),
                ballDatas -> {
                    for (Ball data : ballDatas) {
                        addBallView(data);
                    }
                }
        );

        linesViewModel.ballsToMakeActive_.observe(
                getViewLifecycleOwner(),
                positions -> {
                    for (Integer pos : positions) {
                        ballViews[pos].setState(BallStates.ACTIVE);
                    }
                }
        );

        linesViewModel.path_.observe(
                getViewLifecycleOwner(),
                path -> {
                    if (path != null) {
                        BallView ball = ballViews[selectedBallPosition];
                        if (ball != null) {
                            ball.stopBouncing();
                            int tileSize = fieldSize / 9;

                            Point[] checkpoints = new Point[path.length];

                            for (int i = 0; i < checkpoints.length; i++) {
                                int row = path[i] / FIELD_COLUMNS_COUNT;
                                int column = path[i] % FIELD_COLUMNS_COUNT;
                                int x = (column * tileSize) + (tileSize - ball.getWidth()) / 2;
                                int y = (row * tileSize) + (tileSize - ball.getWidth()) / 2;
                                checkpoints[i] = new Point(x, y);
                            }
                            ball.moveToNewPosition(checkpoints);
                            int newPosition = path[path.length - 1];
                            ballViews[newPosition] = ball;
                            ballViews[selectedBallPosition] = null;
                            selectedBallPosition = -1;
                        }
                    } else {
                        //TODO show denied turn animation
                    }
                }
        );
    }

    private void initField() {
        FieldViewGroup field = binding.field;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        fieldSize = Math.min(displaySize.x, displaySize.y);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(fieldSize, fieldSize);
        field.setLayoutParams(lp);
        field.setOnTouchListener((v, event) -> {
            if (selectedBallPosition != -1 && ballViews[selectedBallPosition] != null) {
                int desiredPosition = coordinatesToPosition(event.getX(), event.getY());
                if (ballViews[desiredPosition] == null) {
                    linesViewModel.makeTurn(selectedBallPosition, desiredPosition);
                }
            }
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addBallView(Ball ball) {
        int position = ball.getPosition();
        int row = position / FIELD_ROWS_COUNT;
        int column = position % FIELD_COLUMNS_COUNT;

        BallView ballView = ballViews[position];
        if (ballView == null) {
            ballView = BallView.getInstance(getContext(), ball.getColor(), ball.getState());

            float tileSize = fieldSize / (float) FIELD_COLUMNS_COUNT;
            float ballSize = 0f;
            if (ball.getState() == BallStates.ACTIVE) {
                ballSize = tileSize * 0.8f;
            } else {
                ballSize = tileSize * 0.3f;
            }
            ConstraintLayout.LayoutParams ballLp = new ConstraintLayout.LayoutParams(
                    (int) ballSize, (int) ballSize);
            ballView.setLayoutParams(ballLp);

            float tileX = tileSize * column + (tileSize - ballSize) / 2;
            float tileY = tileSize * row + (tileSize - ballSize) / 2;
            ballView.setX(tileX);
            ballView.setY(tileY);
            ballView.setOnClickListener(v -> {
                BallView view = (BallView) v;
                if (view.getState() == BallStates.READY_TO_APPEAR) {
                    return;
                }
                int clickedBallPosition = coordinatesToPosition(v.getX(), v.getY());
                if (clickedBallPosition != selectedBallPosition) {
                    if (selectedBallPosition != -1) {
                        ballViews[selectedBallPosition].stopBouncing();
                    }
                    if (!view.isBouncing()) {
                        view.startBouncing();
                    }
                    selectedBallPosition = clickedBallPosition;
                }
            });
            ballViews[position] = ballView;

            binding.getRoot().addView(ballView, ballLp);
        } else {
            ballView.setState(ball.getState());
            ballView.setColor(getContext(), ball.getColor());
        }
    }

    private int coordinatesToPosition(float x, float y) {
        int tileSize = fieldSize / FIELD_COLUMNS_COUNT;
        int row = (int)(y / tileSize);
        int column = (int)(x / tileSize);
        return row * FIELD_ROWS_COUNT + column;
    }
}