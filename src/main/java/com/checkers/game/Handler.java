package com.checkers.game;

import com.checkers.exceptions.ErrorException;

public abstract class Handler {
    private Handler nextHandler;

    public Handler setNextHandler(Handler handler){
        nextHandler = handler;
        return nextHandler;
    }

    public abstract String handleMove(String moves) throws Exception;

    public String handleNext(String moves) throws Exception {
        if(nextHandler != null) {
            return nextHandler.handleMove(moves);
        }
        else {
            throw new ErrorException("Null");
        }
    }
}
