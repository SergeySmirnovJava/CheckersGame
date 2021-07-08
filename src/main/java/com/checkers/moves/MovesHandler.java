package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
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
            BusyCellException, InvalidMoveException {
        setCurrentCells(currentCell);
        moveHandle(nextCell);
        if (nextCell.matches("[a-h][1-8]") && Math.abs(stepLocal) > 32) throw new InvalidMoveException();

    }

    public void attackMovement(String currentCell, String nextCell) throws WhiteCellException,
            BusyCellException, InvalidMoveException {
        setCurrentCells(currentCell);
        moveHandle(nextCell);
        if(Math.abs(stepLocal) > 60) throw new InvalidMoveException();
        int enemyCheckerHash = getEnemyCheckerHash(nextCell);
        hashOppositeCells.remove(enemyCheckerHash);
        oppositeCells.removeIf(s -> s.toLowerCase().hashCode() == enemyCheckerHash);
        currentCells.set(currentCells.indexOf(currentCell), nextCell);
        hashCurrentCells.set(hashCurrentCells.indexOf(currentCell.toLowerCase().hashCode()),
                                                                nextCell.toLowerCase().hashCode());
        swapSides();
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

    public void setCurrentCells(String currentCell){
        currentCheckerCell = currentCell;
    }


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
        if(currentCheckerCell.matches("[A-H][1-8]") && nextCell.matches("[a-h][1-8]")) return false;
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
        int boundArea = nextCell.matches("[a-h][1-8]") ? 1 : 8;
        int checkPosition = 30;

        return false;
    }

}
