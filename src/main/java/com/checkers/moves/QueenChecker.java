package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;

import java.util.*;
public class QueenChecker extends Checker  {
    List<Integer> currentCheckersHash = new ArrayList<>();
    List<Integer> oppositeCheckersHash = new ArrayList<>();
    public QueenChecker(List<String> currentCheckers, List<String> oppositeCheckers,
                                                            String currentCell, boolean side) {
        super(currentCheckers, oppositeCheckers, currentCell, side);
        currentCheckers.forEach(s -> currentCheckersHash.add(s.hashCode()));
        oppositeCheckers.forEach(s -> oppositeCheckersHash.add(s.hashCode()));
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
    void checkEnemyArea(String nextCell, int step) throws InvalidMoveException {
        for(String hashOpposite : oppositeCheckers){
            int offsetHash = (hashOpposite.hashCode() - nextCell.hashCode());
            if(Math.abs(offsetHash % 32)  == 0  || Math.abs(offsetHash % 30) == 0){
                offsetHash = offsetHash % 30;
                if(!oppositeCheckersHash.contains(hashOpposite.hashCode() + (30 + offsetHash)) &&
                            currentCheckersHash.contains(hashOpposite.hashCode() + (30 + offsetHash))){
                    throw  new InvalidMoveException();
                }
            }
        }
    }

}
