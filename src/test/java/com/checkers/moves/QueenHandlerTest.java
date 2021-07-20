package com.checkers.moves;

import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QueenHandlerTest {
    @BeforeClass
    public static void greetingTests() {
        System.out.println("Starting tests for queen checkers ...");
    }

    @Test
    public void testWhiteNextMoveQueen() throws ErrorException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Arrays.asList("A7", "B6", "c5", "G7"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        boolean testResult = false;
        movesHandler.setCurrentCells("A7");
        testResult = movesHandler.isNextMoveQueen("b8");
        Assert.assertFalse(testResult);

        testResult = movesHandler.isNextMoveQueen("B8");
        Assert.assertTrue(testResult);

        movesHandler.setCurrentCells("B6");
        testResult = movesHandler.isNextMoveQueen("c7");
        Assert.assertFalse(testResult);
    }

    @Test
    public void testIsCheckers() throws ErrorException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Arrays.asList("A7", "B6", "c5", "G7"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("B6");
    }

    @Test(expected = ErrorException.class)
    public void testNoCheckers() throws ErrorException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Arrays.asList("A7", "B6", "c5", "G7"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("H8");
    }

    @Test
    public void testNextMoveQueen() throws ErrorException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Collections.singletonList("D4"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        boolean testResult = false;

        movesHandler.setCurrentCells("D4");
        testResult = movesHandler.isMoveWhiteCell("C5");
        Assert.assertTrue(testResult);
        testResult = movesHandler.isMoveWhiteCell("C4");
        Assert.assertFalse(testResult);

        testResult = movesHandler.isMoveWhiteCell("C3");
        Assert.assertTrue(testResult);
        testResult = movesHandler.isMoveWhiteCell("D3");
        Assert.assertFalse(testResult);

        testResult = movesHandler.isMoveWhiteCell("E5");
        Assert.assertTrue(testResult);
        testResult = movesHandler.isMoveWhiteCell("D5");
        Assert.assertFalse(testResult);

        testResult = movesHandler.isMoveWhiteCell("E3");
        Assert.assertTrue(testResult);
        testResult = movesHandler.isMoveWhiteCell("E4");
        Assert.assertFalse(testResult);

        testResult = movesHandler.isMoveWhiteCell("A7");
        Assert.assertTrue(testResult);


        testResult = movesHandler.isMoveWhiteCell("A1");
        Assert.assertTrue(testResult);

        testResult = movesHandler.isMoveWhiteCell("H8");
        Assert.assertTrue(testResult);

        testResult = movesHandler.isMoveWhiteCell("G1");
        Assert.assertTrue(testResult);
    }

    @Test
    public void testMoveHandle() throws ErrorException, InvalidMoveException, WhiteCellException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Arrays.asList("D4", "E3", "g1"));
        ArrayList<String> blackCheckers = new ArrayList<>(Arrays.asList("d2", "F5", "H8", "a1"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        boolean testResult = false;

        movesHandler.setCurrentCells("D4");
        testResult = movesHandler.moveHandle("G7");
        Assert.assertTrue(testResult);
        testResult = movesHandler.moveHandle("H8");
        Assert.assertFalse(testResult);
        testResult = movesHandler.moveHandle("A1");
        Assert.assertFalse(testResult);

        movesHandler.setCurrentCells("E3");
        testResult = movesHandler.moveHandle("G1");
        Assert.assertFalse(testResult);
        testResult = movesHandler.moveHandle("F4");
        Assert.assertTrue(testResult);
        testResult = movesHandler.moveHandle("D2");
        Assert.assertFalse(testResult);
    }

    @Test
    public void testLocalArea(){
        

    }


    @AfterClass
    public static void endTest(){
        System.out.println("All test pass are successful!");
    }
}
