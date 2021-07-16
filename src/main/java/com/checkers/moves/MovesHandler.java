package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;
import com.checkers.game.MovesValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MovesHandler {
    private ArrayList<String> currentCells;
    private ArrayList<String> oppositeCells;
    private ArrayList<Integer> hashCurrentCells = new ArrayList<>();
    private ArrayList<Integer> hashOppositeCells = new ArrayList<>();
    private String currentCheckerCell;
    int stepLocal;
    boolean currentCheckerSide = true;                                  // true - current move for white checkers
                                                                        // false - current move for black checkers

    public MovesHandler(ArrayList<String> whiteCheckers, ArrayList<String> blackCheckers){
        currentCells = whiteCheckers;
        oppositeCells = blackCheckers;
        currentCells.forEach(s -> hashCurrentCells.add(s.toLowerCase().hashCode()));
        oppositeCells.forEach(s -> hashOppositeCells.add(s.toLowerCase().hashCode()));
    }

    public void regularMovement(String currentCell, String nextCell) throws WhiteCellException,
            BusyCellException, InvalidMoveException, ErrorException {
        setCurrentCells(currentCell);
        moveHandle(nextCell);
        if (nextCell.matches("[a-h][1-8]") && Math.abs(stepLocal) > 32) throw new InvalidMoveException();
        if(isPossibleAttack(nextCell)) throw new InvalidMoveException();
        hashCurrentCells.set(hashCurrentCells.indexOf(currentCell.toLowerCase().hashCode()),
                                                                        nextCell.toLowerCase().hashCode());
        currentCells.set(currentCells.indexOf(currentCell), nextCell);
    }

    public void attackMovement(String nextCell) throws WhiteCellException,
            BusyCellException, InvalidMoveException {
        moveHandle(nextCell);
        if(!isMoveWhiteCell(nextCell)) throw new WhiteCellException();
        if(Math.abs(stepLocal) < 60) throw new InvalidMoveException();
        int enemyCheckerHash = getEnemyCheckerHash(nextCell);
        hashOppositeCells.remove((Integer) enemyCheckerHash);
        oppositeCells.removeIf(s -> s.toLowerCase().hashCode() == enemyCheckerHash);
        currentCells.set(currentCells.indexOf(getCurrentCells()), nextCell);
        hashCurrentCells.set(hashCurrentCells.indexOf(getCurrentCells().toLowerCase().hashCode()),
                                                                nextCell.toLowerCase().hashCode());
    }

    public int getEnemyCheckerHash(String nextCell) throws BusyCellException {
        int tempCount = 0;
        int hashOfEnemyChecker = 0;
        int tempStepCell = nextCell.toLowerCase().hashCode() - currentCheckerCell.toLowerCase().hashCode();
        tempStepCell = (tempStepCell/Math.abs(tempStepCell)) * ((Math.abs(tempStepCell) % 32 == 0) ? 32 : 30);
        Integer tempCellHash = nextCell.toLowerCase().hashCode();
        Integer tempCurrentCellHash = currentCheckerCell.toLowerCase().hashCode() + tempStepCell;
        while (!tempCellHash.equals(tempCurrentCellHash)){
            tempCellHash = tempCellHash - tempStepCell;
            if(hashCurrentCells.contains(tempCellHash) || hashOppositeCells.contains(tempCellHash)){
                if(hashOppositeCells.contains(tempCellHash)){
                    hashOfEnemyChecker = tempCellHash;
                }
                tempCount++;
            }

        }
        if (tempCount != 1) throw new BusyCellException();
        return hashOfEnemyChecker;
    }

    public void swapSides(){
        ArrayList<String> tempCurrentCells = new ArrayList<>(currentCells);
        ArrayList<Integer> tempHashCurrent = new ArrayList<>(hashCurrentCells);
        currentCells.clear();
        hashCurrentCells.clear();
        currentCells.addAll(oppositeCells);
        hashCurrentCells.addAll(hashOppositeCells);

        oppositeCells.clear();
        hashOppositeCells.clear();
        oppositeCells.addAll(tempCurrentCells);
        hashOppositeCells.addAll(tempHashCurrent);
    }

    public void setCurrentCells(String currentCell) throws ErrorException {
        if(!currentCells.contains(currentCell)) throw new ErrorException("No such checker");
        currentCheckerCell = currentCell;
    }
    public String getCurrentCells(){return currentCheckerCell;}

    public boolean moveHandle(String nextCell) throws InvalidMoveException,
            WhiteCellException {
        if(hashCurrentCells.contains(nextCell.toLowerCase().hashCode()) ||
                hashOppositeCells.contains(nextCell.toLowerCase().hashCode())) return false;
        if(!isNextMoveQueen(nextCell)) throw new InvalidMoveException();
        if(!isMoveWhiteCell(nextCell)) throw new WhiteCellException();
        return true;
    }

    public boolean isNextMoveQueen(String nextCell){
        int tempCurrentSide = currentCheckerSide ? 8 : 1;
        if(getCurrentCells().matches("[A-H][1-8]") && nextCell.matches("[a-h][1-8]")) return false;
        return !nextCell.matches("[a-h]" + tempCurrentSide);
    }

    // numbers 30 and 32 are calculated empirically
    public boolean isMoveWhiteCell(String nextCell){
        int tempStepLocal = nextCell.toLowerCase().hashCode() - currentCheckerCell.toLowerCase().hashCode();
        if((Math.abs(tempStepLocal) % 30 == 0) || (Math.abs(tempStepLocal) % 32 == 0)){
            stepLocal = tempStepLocal;
            return true;
        }
        else {
            return false;
        }
    }
    // 3088 3248
    public boolean isPossibleAttack(String nextCell){
        int rightDirection = 30;
        int leftDirection = 32;
        int searchingArea = nextCell.matches("[A-H]") ? 3 : 1;
        for(int i = 0; i < searchingArea; i++){
            for(int j = -1; j < 2; j+=2){
                int tempRightDirection = getCurrentCells().toLowerCase().hashCode() + j * rightDirection;
                int tempLeftDirection = getCurrentCells().toLowerCase().hashCode() + j * leftDirection;
                if(hashOppositeCells.contains(tempRightDirection) && searchLocalArea(tempRightDirection)) return true;
                if(hashOppositeCells.contains(tempLeftDirection) && searchLocalArea(tempLeftDirection)) return true;
            }
            rightDirection += 30;
            leftDirection +=32;
        }
        return false;
    }

    public boolean searchLocalArea(int hashSide){
        int stepDirection = (getCurrentCells().toLowerCase().hashCode() - hashSide) > 0 ? -1 : 1;
        int stepOffset  =   (Math.abs(getCurrentCells().toLowerCase().hashCode() - hashSide) % 32 == 0) ? 32 : 30;
        if(hashSide > 3088 && hashSide < 3248){
            int tempDirection = stepDirection * stepOffset;
             if(!hashOppositeCells.contains(hashSide + tempDirection) &&
                    !hashCurrentCells.contains(hashSide + tempDirection)){
                 return true;
            }
        }
        return false;
    }

}
