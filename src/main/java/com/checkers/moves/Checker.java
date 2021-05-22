package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;
import java.util.List;

public abstract class Checker {
    List<String> currentCheckers;
    List<String> oppositeCheckers;
    String currentCell;
    int queenSide;
    public Checker(List<String> currentCheckers, List<String> oppositeCheckers, String currentCell, boolean side){

        this.currentCheckers = currentCheckers;
        this.oppositeCheckers = oppositeCheckers;
        this.currentCell = currentCell;
        queenSide = side ? 1 : 8;
    }


    public int getSide(){
        return queenSide;
    }
    public String regularMove(String nextCell) throws WhiteCellException, BusyCellException,
                                                        InvalidMoveException, ErrorException {
        int step = getNeighbours(nextCell);
        currentCheckers.set(currentCheckers.indexOf(currentCell), nextCell);
        return nextCell;
    }

    public String attackMove(String nextCell) throws WhiteCellException, BusyCellException,
                                                        InvalidMoveException, ErrorException {
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
        if(currentCheckers.contains(nextCell)) throw new BusyCellException();
        if(oppositeCheckers.contains(nextCell)) throw new InvalidMoveException();
        if(nextCell.matches("[a-h]" + queenSide)) throw new InvalidMoveException();
    }

    abstract int getNeighbours(String nextCell) throws BusyCellException, InvalidMoveException;
   // public int getLocalNeighbours(String nextCell) throws BusyCellException, InvalidMoveException, WhiteCellException, ErrorException {
    /*    System.out.println(currentCell);
        System.out.println(nextCell);
        if(currentCheckers.contains(nextCell)) throw new BusyCellException();
        if(oppositeCheckers.contains(nextCell)) throw new InvalidMoveException();
        int localArea = nextCell.hashCode() - currentCell.hashCode();
        if(localArea < -700) throw new ErrorException("Wrong cast"); // todo think about checking case
        System.out.println(localArea);
        if((localArea % 30 == 0) || (localArea % 32) == 0){
            return localArea;
        }
        else {
            throw new WhiteCellException();
        }*/

   // }

    abstract String getNextMove(String nextCell) throws WhiteCellException, BusyCellException, ErrorException, InvalidMoveException;
    abstract String getOppositeChecker(String nextCell, int step) throws InvalidMoveException, ErrorException;
}
