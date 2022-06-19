package com.example.a2048;

import java.util.ArrayList;
import java.util.Random;

public class Board { //доска

    private Square [] board; //массив ячеек
    private ArrayList<Square> vacios; //список пустых ячеек
    private int score; //счет

    public Board(){ //заполняем начальную доску нулями
        board = new Square [16];
        vacios = new ArrayList<Square>(); //список свободных ячеек
        score = 0;
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                board[i * 4 + j] = new Square(i,j,0);
                vacios.add(board[i * 4 + j]);
            }
        }
    }

    public void generateNewRandom() {
        int random = 0;
        Random randomGenerator = new Random();
        random = randomGenerator.nextInt(vacios.size()); //генерируем рандомное число из кол-ва свободных клеток
        vacios.get(random).setValue(2); //на выбранной свободной ячейке значение 2
        vacios.remove(random); //удаляем им списка свободных
    }

    public String getValue(int row, int column) { //метод, чтобы в ячейке появилось значение
        String value = "";
        value = Integer.toString(board[row*4+column].getValue());
        if (value.compareTo("0") == 0)
            return "";
        return value;
    }

    public boolean moveSquare(Square origin, Square destiny) { //когда мы двигаем ячейку с одного места на другое
        boolean moved = false;  //проверка случилось ли передвижение
        //перемещение ячейки
        if (origin.getValue() != 0 && destiny.getValue() == 0) { //если двигаем непустую клетку на пустую
            destiny.setValue(origin.getValue());                 //их зачения меняются местами
            origin.setValue(0);
            vacios.add(origin);                                  //добавляем пустую клетку в список
            vacios.remove(destiny);                              //и удаляем заполнившуюся
            moved = true;
        }
        //слияния двух
        else if (origin.getValue() != 0 && origin.getValue() == destiny.getValue() && origin.isCanMove() && destiny.isCanMove()){
            score += origin.getValue() + destiny.getValue();          //увеличиваем счет
            destiny.setValue(origin.getValue() + destiny.getValue()); //ячейке назначения присваиваем значение суммы 2х
            origin.setValue(0);                                       //начальную обнуляем
            vacios.add(origin);                                       //добавляем пустую клетку в список
            destiny.setCanMove(false);                                //на это движение ячейка больше не может ни с кем взаимодействовать
            moved = true;
        }
        return moved;
    }

    public int moveRow(int rowOrigin, boolean moveUp) { //перемещается строка вверх/вниз
        int moved = 0; //количество сдвинувшихся ячеек
        if (moveUp) {
            for (int i = 0; i < 4; i++)
                if (moveSquare(board[rowOrigin * 4 + i], board[rowOrigin * 4 - 4 + i])) //поочередно перемещаем ячейки строки
                    moved++;                                                            //если возвращено true может, то считаем
        }
        else {
            for (int i = 0; i < 4; i++)                                                 //аналогично для движения вниз
                if(moveSquare(board[rowOrigin * 4 + i], board[rowOrigin * 4 + 4 + i]))
                    moved++;
        }
        return moved;
    }
    public int moveColumn(int columnOrigin, boolean moveRight) {
        int moved = 0;
        if(moveRight) {
            for (int i = 0; i < 4; i++)
                if (moveSquare(board[i * 4 + columnOrigin], board[i * 4 + columnOrigin + 1]))
                    moved++;
        }
        else {
            for (int i = 0; i < 4; i++)
                if (moveSquare(board[i * 4 + columnOrigin],board[i * 4 + columnOrigin - 1]))
                    moved++;
        }
        return moved;
    }

    public int moveBlockUp() { //смахиваем вверх
        int moved = 0;
        for (int j = 0; j < 3; j++) //3 потому что одна по-любому стоит на месте
            for (int i = 1; i < 4; i++)
                moved += moveRow(i,true);
        return moved;
    }
    public int moveBlockDown() { //смахиваем вниз
        int moved = 0;
        for (int j = 0; j < 3; j++)
            for (int i = 2; i >= 0; i--)
                moved += moveRow(i,false);
        return moved;
    }
    public int moveBlockRight() { //смахиваем вправо
        int moved = 0;
        for (int j = 0; j < 3; j++)
            for (int i = 2; i >= 0; i--)
                moved += moveColumn(i,true);
        return moved;
    }
    public int moveBlockLeft() { //смахиваем вправо
        int moved = 0;
        for (int j = 0; j < 3; j++)
            for (int i = 1; i < 4; i++)
                moved += moveColumn(i,false);
        return moved;
    }

    public void setSquaresToCanMove() {
        for (int i = 0; i < 16; i++)
            board[i].setCanMove(true);
    }

    //регирование на движение
    public void topSwipe() {
        if(moveBlockUp() > 0) //если хотя бы один блок сдвинулся - значит игра не закончена
            generateNewRandom(); //значит начинаем по новой
        setSquaresToCanMove(); //а все блоги могут взаимодейстовать
    }
    public void bottomSwipe() {
        if(moveBlockDown() > 0)
            generateNewRandom();
        setSquaresToCanMove();
    }
    public void leftSwipe() {
        if(moveBlockLeft() > 0)
            generateNewRandom();
        setSquaresToCanMove();
    }
    public void rigthSwipe() {
        if(moveBlockRight() > 0)
            generateNewRandom();
        setSquaresToCanMove();
    }

    public String getScore(){
        return Integer.toString(score);
    }
}