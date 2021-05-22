package com.checkers.exceptions;

public class InvalidMoveException extends Exception{
    public InvalidMoveException(){
        super("invalid move");
    }
}
