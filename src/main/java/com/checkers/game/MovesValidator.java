package com.checkers.game;

import com.checkers.moves.CheckerGame;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovesValidator {
    private ArrayList<String> whiteCheckers;
    private ArrayList<String> blackCheckers;
    private CheckerGame checker;
    private final Pattern startPositions;
    private Matcher matchPositions;

    public MovesValidator(String whiteCheckers, String blackCheckers){
        startPositions = Pattern.compile("[a-hA-h][1-8]");
        matchPositions = startPositions.matcher(whiteCheckers);
        while (matchPositions.find()){
            this.whiteCheckers.add(whiteCheckers.substring(matchPositions.start(), matchPositions.end()));
        }
        matchPositions = startPositions.matcher(blackCheckers);
        while (matchPositions.find()){
            this.blackCheckers.add(blackCheckers.substring(matchPositions.start(), matchPositions.end()));
        }
        checker = new CheckerGame(this.whiteCheckers, this.blackCheckers);
    }

    public void handleMove(String moves) throws Exception {
        matchPositions = startPositions.matcher(moves);
        if(matchPositions.find()) {
            checker.setCurrentCell(moves.substring(matchPositions.start(), matchPositions.end()));
            if (moves.contains("-")) {
                if(matchPositions.find()) {
                    checker.regularMove(moves.substring(matchPositions.start(), matchPositions.end()));
                }
            } else {
                while(matchPositions.find()) {
                    checker.attackMove(moves.substring(matchPositions.start(), matchPositions.end()));
                }
            }
        }
        checker.swapCheckers();
    }
}
