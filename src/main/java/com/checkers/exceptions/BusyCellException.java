package com.checkers.exceptions;

public class BusyCellException extends  Exception{
    public BusyCellException(){
        super("busy cell");
    }
}
