package com.example.a2048;

public class Square { //ячейка

    private int column; //столбец
    private int row; //строка
    private int value; //значение в ячейке
    private boolean canMove; //может ли она с кем то взаимодейтсовать (двигаться)

    public Square(int row, int column, int value) {
        super();
        this.column = column;
        this.row = row;
        this.value = value;
        this.canMove = true;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}