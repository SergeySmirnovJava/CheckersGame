package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class RegularChecker  extends Checker{
    private String nextCell;
    public RegularChecker(List<String> currentCheckers, List<String> oppositeCheckers,
                                                                String currentCell, boolean side) {
        super(currentCheckers, oppositeCheckers, currentCell, side);
    }

    @Override
    int getNeighbours(String nextCell) throws BusyCellException, InvalidMoveException {
        int localArea = nextCell.hashCode() - currentCell.hashCode();
        if(localArea == 30  || localArea == 32 ){
            return localArea;
        }
        else {
            throw new InvalidMoveException();
        }
    }

    @Override
    String getOppositeChecker(String nextCell, int step) throws InvalidMoveException {
        for(String check : oppositeCheckers){
            if (check.hashCode() == nextCell.hashCode() - (step/2)) return check;
        }
        throw new InvalidMoveException();
    }


    @Override
    String getNextMove(String nextCell) throws WhiteCellException, BusyCellException,
                                                    ErrorException, InvalidMoveException {

        return nextCell;
    }
}
