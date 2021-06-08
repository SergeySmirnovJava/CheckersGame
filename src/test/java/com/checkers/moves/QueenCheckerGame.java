package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class QueenCheckerGame {
    CheckerGame queenChecker;
    ArrayList<String> whiteCells;
    ArrayList<String> blackCells;

    @BeforeClass
    public static void greetTest(){
        System.out.println("Start testing white regular checkers regular moves");
        System.out.println("--------------------------------------------------");
    }

    @Before
    public void setup(){
        whiteCells = new ArrayList<>(Arrays.asList("a1", "A3", "b2"));
        blackCells = new ArrayList<>(Arrays.asList("g7", "h6", "h8"));
        queenChecker = new CheckerGame(whiteCells, blackCells);
    }

    @Test(expected = BusyCellException.class)
    public void currentCheckersContains() throws ErrorException, BusyCellException, InvalidMoveException {
        queenChecker.setCurrentCell("A3");
        queenChecker.checkMove("B2");
    }

    @Test(expected = InvalidMoveException.class)
    public void oppositeCheckersContains() throws ErrorException, BusyCellException, InvalidMoveException {
        queenChecker.setCurrentCell("A3");
        queenChecker.checkMove("H8");
    }

    @Test(expected = InvalidMoveException.class)
    public void convertToRegular() throws ErrorException, BusyCellException, InvalidMoveException {
        queenChecker.setCurrentCell("A3");
        queenChecker.checkMove("b4");
    }
    @AfterClass
    public static void endTest(){
        System.out.println("All tests passed successfully!");
    }
}
