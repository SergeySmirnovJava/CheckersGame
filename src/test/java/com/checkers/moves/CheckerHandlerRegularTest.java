package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CheckerHandlerRegularTest {
    ArrayList<String> whiteCheckers = new ArrayList<>();
    ArrayList<String> blackCheckers = new ArrayList<>();
    MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);

    @BeforeClass
    public static void greetingTests() {
        System.out.println("Starting tests for regular checkers and regular moves...");
    }

    @Test
    public void testWhiteNextMoveQueen(){
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5", "G7"));

        boolean expectResult = false;
        movesHandler.setCurrentCells("a7");
        boolean resultTest = movesHandler.isNextMoveQueen("b8");
        Assert.assertEquals(expectResult, resultTest);

        movesHandler.setCurrentCells("G7");
        resultTest = movesHandler.isNextMoveQueen("h8");
        Assert.assertEquals(expectResult, resultTest);

        expectResult = true;
        movesHandler.setCurrentCells("G7");
        resultTest = movesHandler.isNextMoveQueen("H8");
        Assert.assertEquals(expectResult, resultTest);

        movesHandler.setCurrentCells("c5");
        resultTest = movesHandler.isNextMoveQueen("d6");
        Assert.assertEquals(expectResult, resultTest);

        movesHandler.setCurrentCells("a7");
        resultTest = movesHandler.isNextMoveQueen("B8");
        Assert.assertEquals(expectResult, resultTest);
    }

    @Test
    public void testWhiteNextCellWhite(){
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5", "G7"));

        boolean expectResult = false;
        movesHandler.setCurrentCells("a7");
        boolean resultTest = movesHandler.isMoveWhiteCell("b7");
        Assert.assertEquals(expectResult, resultTest);
        resultTest = movesHandler.isMoveWhiteCell("A8");
        Assert.assertEquals(expectResult, resultTest);

        movesHandler.setCurrentCells("G7");
        resultTest = movesHandler.isMoveWhiteCell("H7");
        Assert.assertEquals(expectResult, resultTest);

        expectResult = true;
        movesHandler.setCurrentCells("b6");
        resultTest = movesHandler.isMoveWhiteCell("c7");
        Assert.assertEquals(expectResult, resultTest);

        movesHandler.setCurrentCells("G7");
        resultTest = movesHandler.isMoveWhiteCell("H8");
        Assert.assertEquals(expectResult, resultTest);
    }

    @Test
    public void testNextMove() throws InvalidMoveException, WhiteCellException {
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5"));
        blackCheckers = new ArrayList<>(Arrays.asList("c7"));
        MovesHandler moveHandler = new MovesHandler(whiteCheckers, blackCheckers);
        boolean expectResult = false;
        moveHandler.setCurrentCells("b6");
        boolean resultTest = moveHandler.moveHandle("c7");
        Assert.assertEquals(expectResult, resultTest);

        expectResult = true;
        resultTest = moveHandler.moveHandle("a5");
        Assert.assertEquals(expectResult, resultTest);
    }

    @Test
    public void testSwapSides(){
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5"));
        blackCheckers = new ArrayList<>(Arrays.asList("c7"));
        MovesHandler moveHandler = new MovesHandler(whiteCheckers, blackCheckers);
        ArrayList<String> expectCurrentCheckers = new ArrayList<>(Arrays.asList("c7"));
        ArrayList<String> expectOppositeCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5"));
        moveHandler.swapSides();
        Assert.assertArrayEquals(expectCurrentCheckers.toArray(), whiteCheckers.toArray());
        Assert.assertArrayEquals(expectOppositeCheckers.toArray(), blackCheckers.toArray());
    }

    @AfterClass
    public static void endTest(){
        System.out.println("All test pass are successful!");
    }
}