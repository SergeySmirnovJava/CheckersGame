package com.checkers.game;

import com.checkers.moves.CheckerGame;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovesValidator {
    private final CheckerGame checker;
    private final Pattern startPositions;
    private final Pattern moveType;
    private Matcher matchPositions;

    public MovesValidator(String whiteCheckers, String blackCheckers){
        startPositions = Pattern.compile("[a-hA-h][1-8]");
        moveType = Pattern.compile("[:||-]");
        matchPositions = startPositions.matcher(whiteCheckers);
        ArrayList<String> whiteCheckers1 = new ArrayList<>();
        while (matchPositions.find()){
            whiteCheckers1.add(whiteCheckers.substring(matchPositions.start(), matchPositions.end()));
        }
        matchPositions = startPositions.matcher(blackCheckers);
        ArrayList<String> blackCheckers1 = new ArrayList<>();
        while (matchPositions.find()){
            blackCheckers1.add(blackCheckers.substring(matchPositions.start(), matchPositions.end()));
        }
        checker = new CheckerGame(whiteCheckers1, blackCheckers1);
    }

    public void handleMove(String moves) throws Exception {
        matchPositions = startPositions.matcher(moves);
        Matcher matchType = moveType.matcher(moves);

        while(matchPositions.find()) {
            checker.setCurrentCell(moves.substring(matchPositions.start(), matchPositions.end()));
            if(matchType.find()) {
                if (moves.substring(matchType.start(), matchType.end()).equals("-")) {
                    if (matchPositions.find()) {
                        checker.regularMove(moves.substring(matchPositions.start(), matchPositions.end()));
                    }
                } else{
                    if (matchPositions.find()) {
                        checker.attackMove(moves.substring(matchPositions.start(), matchPositions.end()));
                    }
                }
            }
            checker.swapCheckers();
        }
    }

    public String toString(){
        return checker.toString();
    }
}
