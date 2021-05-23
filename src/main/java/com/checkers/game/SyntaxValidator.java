package com.checkers.game;

import com.checkers.exceptions.ErrorException;

public class SyntaxValidator {
    final String syntaxPattern = "((^|\\s)([a-h][1-8]((\\-[a-h][1-8])|(\\:[a-h][1-8]){1,2}))){2}";

    public String handleMove(String moves) throws Exception {
        if(moves.matches(syntaxPattern)){
            System.out.println("move");
            return moves;
        }
        else {
            throw new ErrorException("Syntax error");
        }
    }
}
