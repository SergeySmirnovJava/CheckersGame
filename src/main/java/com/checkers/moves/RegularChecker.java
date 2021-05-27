package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.InvalidMoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegularChecker  extends Checker{
    List<Integer> currentCheckersHash = new ArrayList<>();
    List<Integer> oppositeCheckersHash = new ArrayList<>();
    public RegularChecker(ArrayList<String> currentCheckers, ArrayList<String> oppositeCheckers) {
        super(currentCheckers, oppositeCheckers);
        currentCheckers.forEach(s -> currentCheckersHash.add(s.hashCode()));
        oppositeCheckers.forEach(s -> oppositeCheckersHash.add(s.hashCode()));
    }

    @Override
    int getNeighbours(String nextCell) throws BusyCellException, InvalidMoveException {
        int localArea = (nextCell.toLowerCase().hashCode() - currentCell.toLowerCase().hashCode());
        if(Math.abs(localArea) == 30  || Math.abs(localArea) == 32 ){
            return localArea % 30;
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
    void checkEnemyArea(String nextCell, int step) throws InvalidMoveException {
        for(int i = 0; i < 2; i += 2){
            for(int j = -1; j < 1; j+=2){
                int opHash= nextCell.hashCode() + j * (60 + i * 2);
                if(oppositeCheckersHash.contains(nextCell.hashCode() + j*(30 + i)) &&
                        !oppositeCheckersHash.contains(opHash) &&
                            !currentCheckersHash.contains(opHash)){
                    throw new InvalidMoveException();
                }
            }
        }
    }

}
