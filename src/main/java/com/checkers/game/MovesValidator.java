package com.checkers.game;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;

import com.checkers.exceptions.WhiteCellException;
import com.checkers.moves.Checker;
import com.checkers.moves.QueenChecker;
import com.checkers.moves.RegularChecker;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovesValidator extends Handler{
    private final Pattern movePattern;
    private Matcher moveMatcher;
    private String  moveSyntax;
    private final List<String> currentCheckers;
    private final List<String> oppositeCheckers;
    private String nextPosition;
    private Checker checker;

    public MovesValidator(List<String> currentPositions, List<String> oppositePositions){
        currentCheckers = currentPositions;
        oppositeCheckers = oppositePositions;
        moveSyntax = "([a-hA-h])([1-8])";
        movePattern = Pattern.compile(moveSyntax);
    }

    @Override
    public String handleMove(String moves) throws Exception {
        moveMatcher = movePattern.matcher(moveSyntax);
        System.out.println("move");
        if(moveMatcher.find()){
            String currentPosition = moves.substring(moveMatcher.start(), moveMatcher.end());
            if(moveMatcher.group(1).matches("A-H")){
                int l =1;
            }
            else {
                int k = 9;
            }
            return null;
        }
        else{
            throw new ErrorException("Error");
        }
    }

    public boolean isCellFree(String position){
        return currentCheckers.contains(position) && !oppositeCheckers.contains(position);
    }



    public String regularMove(String moves, String currentPosition) throws BusyCellException,
                                                                WhiteCellException, ErrorException {
        String nextPosition = null;
        while(moveMatcher.find()){
            nextPosition = checkNeighbours(currentPosition, moves.substring(moveMatcher.start(), moveMatcher.end()));
            currentCheckers.set(currentCheckers.indexOf(currentPosition), nextPosition);
        }
        return nextPosition;
    }

    public String attackMove(String moves, String currentCell) throws InvalidMoveException, BusyCellException {
        String currentPosition = currentCell;
        while (moveMatcher.find()){
            String attackPosition = moves.substring(moveMatcher.start(), moveMatcher.end());
            if(isCellFree(attackPosition)) throw new InvalidMoveException();
            currentCheckers.set(currentCheckers.indexOf(currentPosition), attackPosition);
            oppositeCheckers.remove(currentPosition);
            currentPosition = attackPosition;
        }
        return currentPosition;
    }


    public String checkNeighbours(String currentPosition, String nextStep) throws BusyCellException, WhiteCellException,
            ErrorException
    {
        if(isCellFree(nextStep)) throw new BusyCellException();
        if(nextStep.hashCode() % 2 == 1) throw new WhiteCellException();
        int localArea = Math.abs(nextStep.hashCode() - currentPosition.hashCode());
        System.out.println(currentPosition);
        System.out.println(localArea);
        if(localArea == 30 || localArea == 32){
            return nextStep;
        }
        else {
            throw new ErrorException("error");
        }
    }

}
