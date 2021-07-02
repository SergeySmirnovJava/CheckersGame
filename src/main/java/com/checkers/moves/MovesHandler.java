package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;
import com.checkers.game.MovesValidator;

import java.util.ArrayList;
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
        moveHandle(currentCell, nextCell);
        if (nextCell.matches("[a-h][1-8]") && Math.abs(stepLocal) > 32) throw new InvalidMoveException();

    }

    public void moveHandle(String currentCell, String nextCell) throws BusyCellException, InvalidMoveException,
            WhiteCellException {
        currentCheckerCell = currentCell;
        if(hashCurrentCells.contains(nextCell.toLowerCase().hashCode()) ||
                hashOppositeCells.contains(nextCell.toLowerCase().hashCode())) throw new BusyCellException();
        if(!isNextMoveQueen(nextCell)) throw new InvalidMoveException();
        if(!isMoveWhiteCell(nextCell)) throw new WhiteCellException();
    }
    
    public boolean isNextMoveQueen(String nextCell){
        int tempCurrentSide = currentCheckerSide ? 8 : 1;
        if(currentCheckerCell.matches("[A-H][1-8]") && nextCell.matches("[a-h][1-8]")) return false;
        return (nextCell.matches("[a-h]" + tempCurrentSide));
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

    public boolean isEnemyInArea(String nextCell){
        int boundArea = nextCell.matches("[a-h][1-8]") ? 4 : 64;
        for (int i = 0; i < boundArea; i++){
            for(int j = -1; j < 1; j += 2){

            }
        }
        return false;
    }
}
