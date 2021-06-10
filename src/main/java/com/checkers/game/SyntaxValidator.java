package com.checkers.game;

import com.checkers.exceptions.ErrorException;

public class SyntaxValidator {
    final String syntaxPattern = "((^|\\s)([a-h][1-8]((\\-[a-h][1-8])|(\\:[a-h][1-8]){1,2}))){2}";

    public String handleMove(String moves) throws Exception {
        System.out.println(moves);
        if(moves.matches(syntaxPattern)){
            return moves;
        }
        else {
            throw new ErrorException("Syntax error");
        }
    }
}
