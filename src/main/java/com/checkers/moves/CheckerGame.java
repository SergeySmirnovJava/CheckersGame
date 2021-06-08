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
        this.currentCheckers.forEach(s -> hashCurrent.add(s.toLowerCase().hashCode()));
        this.oppositeCheckers.forEach(s -> hashOpposite.add(s.toLowerCase().hashCode()));
    }

    public void regularMove(String nextCell) throws BusyCellException, InvalidMoveException,
            ErrorException, WhiteCellException {
        checkMove(nextCell);
        int step = getNeighbours(nextCell);
        if(nextCell.matches("[a-h][1-8]") && (Math.abs(step) > 32)) throw new ErrorException("Wrong move");
        int stepOffset = step % 30;
        if(checkEnemyArea()) throw new InvalidMoveException();
        currentCheckers.set(currentCheckers.indexOf(currentCell), nextCell);
    }

    public void attackMove(String nextCell) throws BusyCellException, InvalidMoveException,
            ErrorException, WhiteCellException {
        checkMove(nextCell);
        int step = getNeighbours(nextCell);
        if(Math.abs(step) < 60) throw new InvalidMoveException();
        int tempHash  = nextCell.hashCode();
        while (tempHash - (step/2) != currentCell.hashCode()){
            tempHash -= (step/2);
            int finalTempHash = tempHash;
            if(!oppositeCheckers.removeIf(s -> s.hashCode() == finalTempHash)) throw new ErrorException("No enemy checker");
        }
        currentCheckers.set(currentCheckers.indexOf(currentCell), nextCell);
        currentCell = nextCell;
    }

    public void setCurrentCell(String currentCell) throws ErrorException {
        if(!currentCheckers.contains(currentCell)) throw new ErrorException("No such checker");
        this.currentCell = currentCell;
    }

    public boolean checkEnemyArea(){
        int tempStep = 30;
        int tempHash;
        int j = 1;

        while(j < 64){
            for (int i = -1; i < 1; i += 2){
                tempHash = this.currentCell.toLowerCase().hashCode() + i * tempStep;
                if(hashOpposite.contains(tempHash)){
                    if((tempHash >= 3088 && tempHash <= 3248) && (tempHash % 56 != 0 && tempHash % 18 != 0)){
                        if(!hashOpposite.contains(tempHash + i * tempStep) &&
                                    !hashCurrent.contains(tempHash + i * tempStep)) {
                            if(hashCurrent.contains(tempHash - i * tempStep)){
                                if(this.currentCell.toLowerCase().hashCode() == (tempHash - i * tempStep)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            if(tempStep == 32 && this.currentCell.matches("[a-h][1-8]")){
                return false;
            }
            j++;
            tempStep = tempStep % 30 == 0 ? 32 * (j - 1) : 30 * j;
        }
        return false;
    }


    public void checkMove(String nextCell) throws BusyCellException, InvalidMoveException {
        int tempSide = side ? 8 : 1;
        if(this.currentCell.matches("[A-H][1-8]") && nextCell.matches("[a-h][1-8]")) throw new InvalidMoveException();
        if(currentCheckers.contains(nextCell.toLowerCase())) throw new BusyCellException();
        if(oppositeCheckers.contains(nextCell.toLowerCase())) throw new InvalidMoveException();
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
            return localArea;
        }
        else {
            throw new WhiteCellException();
        }
    }
}
