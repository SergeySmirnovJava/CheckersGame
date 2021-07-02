package com.checkers.moves;

import com.checkers.exceptions.ErrorException;
import com.checkers.game.SyntaxValidator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerValidator {
    private final String syntaxChecker = "((^|\\s)([a-hA-H][1-8]((\\-[a-hA-H][1-8])|(\\:[a-hA-H][1-8]){1,2}))){2}";
    private final Pattern patternMoveType = Pattern.compile("[:||-]");
    private ArrayList<String> whiteCheckers;
    private ArrayList<String> blackCheckers;
    private MovesHandler movesHandler;

    public CheckerValidator(String whiteCells, String blackCells){
        Pattern patternPositions = Pattern.compile("[a-hA-h][1-8]");
        Matcher matcherPositions = patternPositions.matcher(whiteCells);
        whiteCheckers = fillCheckersPositions(matcherPositions, whiteCells);
        matcherPositions = patternPositions.matcher(blackCells);
        blackCheckers = fillCheckersPositions(matcherPositions, blackCells);
        movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
    }

    public ArrayList<String> fillCheckersPositions(Matcher matcherPositions, String checkerCells){
        ArrayList<String> tempCells = new ArrayList<>();
        while (matcherPositions.find()){
            tempCells.add(checkerCells.substring(matcherPositions.start(), matcherPositions.end()));
        }
        return tempCells;
    }

    public String handleMovement(String movement) throws ErrorException {
        if(!movement.matches(syntaxChecker)) throw new ErrorException("Wrong syntax");
        Matcher matcherMoveType = patternMoveType.matcher(movement);


        return null;
    }
}
