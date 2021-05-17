package com.checkers.game;

import com.checkers.exceptions.ErrorException;

public abstract class Handler {
    private Handler nextHandler;

    public Handler setNextHandler(Handler handler){
        nextHandler = handler;
        return nextHandler;
    }

    public abstract String handleMove(String moves) throws Exception;

    public void handleNext(String moves) throws Exception {
        if(nextHandler != null) {
            nextHandler.handleMove(moves);
        }
    }
}
