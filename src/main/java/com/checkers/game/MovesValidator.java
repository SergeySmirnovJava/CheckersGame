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

public class MovesValidator {
    private final Pattern movePattern;
    private final String  moveSyntax;
    private final List<String> currentCheckers;
    private final List<String> oppositeCheckers;
    private Checker checker;

    public MovesValidator(List<String> currentPositions, List<String> oppositePositions){
        currentCheckers = currentPositions;
        oppositeCheckers = oppositePositions;
        moveSyntax = "([a-hA-h])([1-8])";
        movePattern = Pattern.compile(moveSyntax);
    }

    public void handleMove(String moves, boolean side) throws Exception {
        Matcher moveMatcher = movePattern.matcher(moveSyntax);
        System.out.println("move");
        if(moveMatcher.find()) {
            String currentPosition = moves.substring(moveMatcher.start(), moveMatcher.end());
            if (moves.matches("A-H")) {
                checker = new QueenChecker(currentCheckers, oppositeCheckers, currentPosition, side);
            } else {
                checker = new RegularChecker(currentCheckers, oppositeCheckers, currentPosition, side);
            }
        }
        while (moveMatcher.find()){
            String nextCell = moves.substring(moveMatcher.start(), moveMatcher.end());
            if(moves.contains("-")){
                checker.regularMove(nextCell);
            }
            else{
                checker.attackMove(nextCell);
            }
        }
    }
}
