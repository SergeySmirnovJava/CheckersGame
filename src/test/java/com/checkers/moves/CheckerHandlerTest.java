package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
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

import static org.junit.Assert.*;

public class CheckerHandlerTest {
    ArrayList<String> whiteCheckers = new ArrayList<>();
    ArrayList<String> blackCheckers = new ArrayList<>();
    MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);

    @BeforeClass
    public static void greetingTests() {
        System.out.println("Starting tests for regular checkers and regular moves...");
    }

    @Test
    public void testWhiteNextMoveQueen() throws ErrorException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5", "G7"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);

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
    public void testWhiteNextCellWhite() throws ErrorException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5", "G7"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);

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
    public void testNextMove() throws InvalidMoveException, WhiteCellException, ErrorException {
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5"));
        blackCheckers = new ArrayList<>(Collections.singletonList("c7"));
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
        blackCheckers = new ArrayList<>(Collections.singletonList("c7"));
        MovesHandler moveHandler = new MovesHandler(whiteCheckers, blackCheckers);
        ArrayList<String> expectCurrentCheckers = new ArrayList<>(Arrays.asList("c7"));
        ArrayList<String> expectOppositeCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5"));
        moveHandler.swapSides();
        Assert.assertArrayEquals(expectCurrentCheckers.toArray(), whiteCheckers.toArray());
        Assert.assertArrayEquals(expectOppositeCheckers.toArray(), blackCheckers.toArray());
    }

    @Test
    public void testLocalArea() throws ErrorException {
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5"));
        blackCheckers = new ArrayList<>(Collections.singletonList("c7"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        boolean nextCell;

        movesHandler.setCurrentCells("b6");
        nextCell = movesHandler.searchLocalArea(movesHandler.getCurrentCells().hashCode() + 30);
        assertTrue(nextCell);

        movesHandler.setCurrentCells("a7");
        nextCell = movesHandler.searchLocalArea(movesHandler.getCurrentCells().hashCode() + 30);
        assertFalse(nextCell);

        movesHandler.setCurrentCells("c5");
        nextCell = movesHandler.searchLocalArea(movesHandler.getCurrentCells().hashCode() - 30);
        assertFalse(nextCell);

        nextCell = movesHandler.searchLocalArea(movesHandler.getCurrentCells().hashCode() + 30);
        assertTrue(nextCell);

        nextCell = movesHandler.searchLocalArea(movesHandler.getCurrentCells().hashCode() + 32);
        assertTrue(nextCell);

        nextCell = movesHandler.searchLocalArea(movesHandler.getCurrentCells().hashCode() - 32);
        assertTrue(nextCell);
    }

    @Test
    public void testPossibleAttack() throws ErrorException {
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5"));
        blackCheckers = new ArrayList<>(Collections.singletonList("c7"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        boolean nextCell;

        movesHandler.setCurrentCells("b6");
        nextCell = movesHandler.isPossibleAttack("a5");
        assertTrue(nextCell);

        movesHandler.setCurrentCells("c5");
        nextCell = movesHandler.isPossibleAttack("d6");
        assertFalse(nextCell);
    }

    @Test
    public void testEnemyHash() throws ErrorException, BusyCellException {
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5", "d4"));
        blackCheckers = new ArrayList<>(Collections.singletonList("d6"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        int enemyHash;
        movesHandler.setCurrentCells("c5");
        enemyHash = movesHandler.getEnemyCheckerHash("e7");
        Assert.assertEquals("d6".hashCode(), enemyHash);
    }

    @Test
    public void testAttackMove() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5", "d4"));
        blackCheckers = new ArrayList<>(Collections.singletonList("d6"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);

        movesHandler.setCurrentCells("c5");
        movesHandler.attackMovement("e7");

        ArrayList<String> expectWhiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "e7", "d4"));
        ArrayList<String> expectBlackCheckers = new ArrayList<>(Collections.emptyList());

        Assert.assertArrayEquals(expectWhiteCheckers.toArray(), whiteCheckers.toArray());
        Assert.assertArrayEquals(expectBlackCheckers.toArray(), blackCheckers.toArray());
    }

    @Test
    public void testRegularMove() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        whiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5", "d4"));
        blackCheckers = new ArrayList<>(Collections.singletonList("d6"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);

        movesHandler.setCurrentCells("d4");
        movesHandler.regularMovement("e5");

        ArrayList<String> expectedWhiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6", "c5", "e5"));
        Assert.assertArrayEquals(expectedWhiteCheckers.toArray(), whiteCheckers.toArray());
    }

    @AfterClass
    public static void endTest(){
        System.out.println("All test pass are successful!");
    }
}