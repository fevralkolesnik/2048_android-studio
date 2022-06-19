package com.example.a2048;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game extends Activity {

    private Board board; //игровое поле
    private TextView[][] tvBoard; //значения ячеек
    private final int[][] tvIds = {{R.id.tv00, R.id.tv01, R.id.tv02, R.id.tv03},
            {R.id.tv10, R.id.tv11, R.id.tv12, R.id.tv13},
            {R.id.tv20, R.id.tv21, R.id.tv22, R.id.tv23},
            {R.id.tv30, R.id.tv31, R.id.tv32, R.id.tv33}}; //чтобы код не загрузить
    private TextView score; //счет

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        RelativeLayout rel = (RelativeLayout) findViewById(R.id.completeLayout);

        board = new Board();
        score = (TextView) findViewById(R.id.textResult);
        tvBoard = new TextView[4][4];

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                tvBoard[i][j] = (TextView) findViewById(tvIds[i][j]);

        //начало игры это две ячейки со значением 2
        board.generateNewRandom();
        board.generateNewRandom();
        showBoard(); //внизу

        //все. дальше телефон реагирует на движения и после каждого действия показывается обновленные результаты ячеек
        rel.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                board.topSwipe();
                showBoard();
            }

            public void onSwipeRight() {
                board.rigthSwipe();
                showBoard();
            }

            public void onSwipeLeft() {
                board.leftSwipe();
                showBoard();
            }

            public void onSwipeBottom() {
                board.bottomSwipe();
                showBoard();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showBoard() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (board.getValue(i, j).compareTo(tvBoard[i][j].getText().toString()) != 0)
                    tvBoard[i][j].setText(board.getValue(i, j)); //выводим значение ячейки на экран
        score.setText("Score: "+board.getScore()); //выводим значение счета на экран
    }

    public void Finish(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}