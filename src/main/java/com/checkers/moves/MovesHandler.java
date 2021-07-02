package com.checkers.moves;

import com.checkers.game.MovesValidator;

import java.util.ArrayList;

public class MovesHandler {
    private ArrayList<String> currentCells;
    private ArrayList<String> oppositeCells;


    public MovesHandler(ArrayList<String> whiteCheckers, ArrayList<String> blackCheckers){
        currentCells = whiteCheckers;
        oppositeCells = blackCheckers;
    }
}
