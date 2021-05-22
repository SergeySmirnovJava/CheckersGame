package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class QueenChecker extends Checker  {
    private RegularChecker regularChecker;
    private String result;
    public QueenChecker(List<String> currentCheckers, List<String> oppositeCheckers,
                                                            String currentCell, boolean side) {
        super(currentCheckers, oppositeCheckers, currentCell, side);
    }

    @Override
    int getNeighbours(String nextCell) throws BusyCellException, InvalidMoveException {
        int localArea = nextCell.hashCode() - currentCell.hashCode();
        if(localArea % 30 == 0 || localArea % 32 == 0 ){
            return localArea;
        }
        else {
            throw new InvalidMoveException();
        }
    }

    @Override
    String getOppositeChecker(String nextCell, int step) throws InvalidMoveException, ErrorException {
        int tempHash = nextCell.hashCode();
        int tempStep = step % 32 == 0 ? 32 : 30;
        int countCheckers = 0;
        while(tempHash != currentCell.hashCode()){
            tempHash = tempHash - tempStep;
                for(String tempString : oppositeCheckers){
                    if(tempHash == tempString.hashCode()){
                        countCheckers ++;
                    }
                }
        }
        switch (countCheckers){
            case 0:
                throw new ErrorException("Nothing to attack");
            case 1:
                return nextCell;
            default:
                throw new InvalidMoveException();
        }
    }

    @Override
    String getNextMove(String nextCell) throws WhiteCellException, BusyCellException, ErrorException, InvalidMoveException {

        return null;
    }
}
