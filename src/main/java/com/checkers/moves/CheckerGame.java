package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CheckerGame {
    ArrayList<String> currentCheckers;
    ArrayList<String> oppositeCheckers;
    ArrayList<Integer> hashCurrent = new ArrayList<>();
    ArrayList<Integer> hashOpposite = new ArrayList<>();
    String currentCell;
    boolean side = true;

    public CheckerGame(ArrayList<String> currentCheckers, ArrayList<String> oppositeCheckers){
        this.currentCheckers = currentCheckers;
        this.oppositeCheckers = oppositeCheckers;
        this.currentCheckers.forEach(s -> hashCurrent.add(s.hashCode()));
        this.oppositeCheckers.forEach(s -> hashOpposite.add(s.hashCode()));
    }

    public void regularMove(String nextCell) throws BusyCellException, InvalidMoveException,
            ErrorException, WhiteCellException {
        checkMove(nextCell);
        int step = getNeighbours(nextCell);
        checkEnemyArea(nextCell, step);
        currentCheckers.set(currentCheckers.indexOf(currentCell), nextCell);
    }

    public void attackMove(String nextCell){

    }

    public void setCurrentCell(String currentCell) throws ErrorException {
        if(!currentCheckers.contains(currentCell)) throw new ErrorException("No such checker");
        this.currentCell = currentCell;
    }

    public void checkEnemyArea(String nextCell, int step) throws InvalidMoveException {
        


        for(String hash : oppositeCheckers){
            int offsetHash = (hash.hashCode() - nextCell.hashCode());
            System.out.println(offsetHash);
            if(Math.abs(offsetHash % 32)  == 0  || Math.abs(offsetHash % 30) == 0){
                offsetHash = offsetHash % 30;
                if(!hashOpposite.contains(hash.hashCode() - (30 + offsetHash)) &&
                        hashCurrent.contains(hash.hashCode() - (30 + offsetHash))){
                    throw  new InvalidMoveException();
                }
            }
        }
    }


    public void checkMove(String nextCell) throws BusyCellException, InvalidMoveException {
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

    public int getNeighbours(String nextCell) throws ErrorException, WhiteCellException {
        int localArea = nextCell.hashCode() - currentCell.hashCode();
        if((Math.abs(localArea) % 30) == 0 || (Math.abs(localArea) % 32) == 0){
            if(nextCell.matches("[a-h][1-8]") && (Math.abs(localArea) > 32)) throw new ErrorException("Wrong move");
            return localArea;
        }
        else {
            throw new WhiteCellException();
        }
    }
}
