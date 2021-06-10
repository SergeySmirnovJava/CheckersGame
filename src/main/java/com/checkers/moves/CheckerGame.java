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
        if(checkEnemyArea()) throw new InvalidMoveException();
        hashCurrent.set(hashCurrent.indexOf(currentCell.toLowerCase().hashCode()), nextCell.toLowerCase().hashCode());
        currentCheckers.set(currentCheckers.indexOf(currentCell), nextCell);
    }

    public void attackMove(String nextCell) throws BusyCellException, InvalidMoveException,
            ErrorException, WhiteCellException {
        checkMove(nextCell);
        int step = getNeighbours(nextCell);
        if(Math.abs(step) < 60) throw new InvalidMoveException();
        int side = step < 0 ? -1 : 1;
        step = (step % 30 == 0 ? 30 : 32) * side;
        int tempHash  = nextCell.toLowerCase().hashCode();
        int count = 0;
        int finalTempHash = 0;
        while (tempHash != currentCell.toLowerCase().hashCode()){
            tempHash -= (step);
            if(hashOpposite.contains(tempHash)){
                finalTempHash = tempHash;
                count++;
            }
        }
        if(count != 1) throw new InvalidMoveException();
        int finalTempHash1 = finalTempHash;
        oppositeCheckers.removeIf(s -> s.hashCode() == finalTempHash1);
        hashOpposite.removeIf(h -> h.equals(finalTempHash1));
        hashCurrent.set(hashCurrent.indexOf(currentCell.toLowerCase().hashCode()), nextCell.toLowerCase().hashCode());
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
        if(currentCell.matches("[A-H][1-8]") && nextCell.matches("[a-h][1-8]")) throw new InvalidMoveException();
        if(hashCurrent.contains(nextCell.toLowerCase().hashCode())) throw new BusyCellException();
        if(hashOpposite.contains(nextCell.toLowerCase().hashCode())) throw new InvalidMoveException();
        if(nextCell.matches("[a-h]" + tempSide)) throw new InvalidMoveException();
    }

    public void swapCheckers(){
        this.side = !side;
        ArrayList<String> tempCheckers = new ArrayList<>(currentCheckers);
        ArrayList<Integer> tempHash = new ArrayList<>(hashCurrent);
        Iterator<String> iterator = currentCheckers.iterator();
        Iterator<Integer> hashIterator = hashCurrent.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
            hashIterator.next();
            hashIterator.remove();
        }
        currentCheckers.addAll(oppositeCheckers);
        hashCurrent.addAll(hashOpposite);
        iterator = oppositeCheckers.iterator();
        hashIterator = hashOpposite.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
            hashIterator.next();
            hashIterator.remove();
        }
        oppositeCheckers.addAll(tempCheckers);
        hashOpposite.addAll(tempHash);
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
    @Override
    public String toString(){
        StringBuilder results = new StringBuilder();
        for(String currentLines : currentCheckers){
            results.append(currentLines).append(" ");
        }
        results.append("\n");
        for(String oppositeLines : oppositeCheckers){
            results.append(oppositeLines).append(" ");
        }
        return results.toString();
    }
}
