package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Checker {
    ArrayList<String> currentCheckers;
    ArrayList<String> oppositeCheckers;
    String currentCell;
    boolean side = true;
    public Checker(ArrayList<String> currentCheckers, ArrayList<String> oppositeCheckers){
        this.currentCheckers = currentCheckers;
        this.oppositeCheckers = oppositeCheckers;
    }

    public void setCurrentCell(String currentCell) throws ErrorException {
        if(!currentCheckers.contains(currentCell)) throw new ErrorException("No cell");
        this.currentCell = currentCell;
    }

    public String regularMove(String nextCell) throws  BusyCellException, InvalidMoveException {
        checkRegularMoves(nextCell);
        int step = getNeighbours(nextCell);
        checkEnemyArea(nextCell, step);
        currentCheckers.set(currentCheckers.indexOf(currentCell), nextCell);
        return nextCell;
    }

    public String attackMove(String nextCell) throws BusyCellException, InvalidMoveException, ErrorException {
        checkRegularMoves(nextCell);
        int step = getNeighbours(nextCell);
        if(Math.abs(step) < 60)  throw  new InvalidMoveException();
        String temp = getOppositeChecker(nextCell, step);
        currentCheckers.set(currentCheckers.indexOf(currentCell), nextCell);
        currentCell = nextCell;
        oppositeCheckers.remove(temp);
        return nextCell;
    }

    public void checkRegularMoves(String nextCell) throws BusyCellException, InvalidMoveException {
        int tempSide = side ? 8 : 1;
        if(currentCheckers.contains(nextCell)) throw new BusyCellException();
        if(oppositeCheckers.contains(nextCell)) throw new InvalidMoveException();
        if(nextCell.matches("[a-h]" + tempSide)) throw new InvalidMoveException();
    }

    public void swapCheckers(){
        this.side = !side;
        ArrayList<String> tempCheckers = new ArrayList<>(currentCheckers);
        Iterator<String> iterator = currentCheckers.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        currentCheckers.addAll(oppositeCheckers);
        iterator = oppositeCheckers.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        oppositeCheckers.addAll(tempCheckers);
    }

    abstract int getNeighbours(String nextCell) throws BusyCellException, InvalidMoveException;
    abstract String getOppositeChecker(String nextCell, int step) throws InvalidMoveException, ErrorException;
    abstract void checkEnemyArea(String nextCell, int step) throws InvalidMoveException;
}
