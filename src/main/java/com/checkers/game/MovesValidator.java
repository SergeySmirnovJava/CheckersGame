package com.checkers.game;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvaliMoveException;
import com.checkers.exceptions.WhiteCellException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovesValidator extends Handler{
    private final Pattern movePattern;
    private Matcher moveMatcher;
    private String  moves;
    private final List<String> currentCheckers;
    private final List<String> oppositeCheckers;
    private String nextPosition;
    public MovesValidator(List<String> currentPositions, List<String> oppositePositions){
        currentCheckers = currentPositions;
        oppositeCheckers = oppositePositions;
        String moveSyntax = "([a-hA-h])([1-8])";
        movePattern = Pattern.compile(moveSyntax);
    }

    @Override
    public String handleMove(String moves) throws Exception {
        moveMatcher = movePattern.matcher(moves);
        if(moveMatcher.find()){
            String currentPosition = moves.substring(moveMatcher.start(), moveMatcher.end());
            if(!isCellFree(currentPosition)) throw new BusyCellException();
            if(moves.contains(":")){
                attackMove(moves, currentPosition);
            }
            else{
                regularMove(moves, currentPosition);
            }
        }

        return null;
    }

    public boolean isCellFree(String position){
        return currentCheckers.contains(position) && !oppositeCheckers.contains(position);
    }



    public void regularMove(String moves, String currentPosition) throws BusyCellException,
                                                                WhiteCellException, ErrorException {
        if(moveMatcher.find()){
            String nextPosition = checkNeighbours(currentPosition, moves.substring(moveMatcher.start(), moveMatcher.end()));
            currentCheckers.set(currentCheckers.indexOf(currentPosition), nextPosition);
        }
    }

    public void attackMove(String moves, String currentCell) throws InvaliMoveException, BusyCellException {
        String currentPosition = currentCell;
        while (moveMatcher.find()){
            String attackPosition = moves.substring(moveMatcher.start(), moveMatcher.end());
            if(isCellFree(attackPosition)) throw new InvaliMoveException();
            currentCheckers.set(currentCheckers.indexOf(currentPosition), attackPosition);
            oppositeCheckers.remove(currentPosition);
            currentPosition = attackPosition;
        }
    }


    public String checkNeighbours(String currentPosition, String nextStep) throws BusyCellException, WhiteCellException,
            ErrorException
    {
        if(isCellFree(nextStep)) throw new BusyCellException();
        if(nextStep.hashCode() % 2 == 1) throw new WhiteCellException();
        int localArea = Math.abs(nextStep.hashCode() - currentPosition.hashCode());
        System.out.println(localArea);
        if(localArea == 30 || localArea == 32){
            return nextStep;
        }
        else {
            throw new ErrorException("error");
        }
    }
}
