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
    public void testLocalArea() throws ErrorException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Collections.singletonList("D6"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);

        boolean testResult = false;
        movesHandler.setCurrentCells("D6");

        testResult = movesHandler.searchLocalArea(movesHandler.getCurrentCells().toLowerCase().hashCode() + 30);
        Assert.assertTrue(testResult);

        testResult = movesHandler.searchLocalArea(movesHandler.getCurrentCells().toLowerCase().hashCode() + 32);
        Assert.assertTrue(testResult);

        testResult = movesHandler.searchLocalArea(movesHandler.getCurrentCells().toLowerCase().hashCode() - 30);
        Assert.assertTrue(testResult);

        testResult = movesHandler.searchLocalArea(movesHandler.getCurrentCells().toLowerCase().hashCode() - 32);
        Assert.assertTrue(testResult);

    }

    @Test
    public void testRegularMovie() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Arrays.asList("D6", "A1"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("D6");
        movesHandler.regularMovement("A3");

        ArrayList<String> expectWhiteCheckers = new ArrayList<>(Arrays.asList("A3", "A1"));
        Assert.assertArrayEquals(expectWhiteCheckers.toArray(), whiteCheckers.toArray());

        movesHandler.setCurrentCells("A1");
        movesHandler.regularMovement("H8");
        expectWhiteCheckers = new ArrayList<>(Arrays.asList("A3", "H8"));
        Assert.assertArrayEquals(expectWhiteCheckers.toArray(), whiteCheckers.toArray());

    }


    @Test
    public void testQueenCanAttack() throws ErrorException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Collections.singletonList("D6"));
        ArrayList<String> blackCheckers = new ArrayList<>();
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        boolean testResult = false;

        movesHandler.setCurrentCells("D6");
        testResult = movesHandler.isPossibleAttack("A1");
        Assert.assertFalse(testResult);

        whiteCheckers = new ArrayList<>(Collections.singletonList("D6"));
        blackCheckers = new ArrayList<>(Collections.singletonList("e5"));
        movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("D6");
        testResult = movesHandler.isPossibleAttack("A1");
        Assert.assertTrue(testResult);

        whiteCheckers = new ArrayList<>(Collections.singletonList("E5"));
        blackCheckers = new ArrayList<>(Collections.singletonList("h8"));
        movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("E5");
        testResult = movesHandler.isPossibleAttack("A1");
        Assert.assertFalse(testResult);

        whiteCheckers = new ArrayList<>(Collections.singletonList("E5"));
        blackCheckers = new ArrayList<>(Collections.singletonList("b2"));
        movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("E5");
        testResult = movesHandler.isPossibleAttack("H8");
        Assert.assertTrue(testResult);
    }

    @Test(expected = BusyCellException.class)
    public void testHasEnemyInMove() throws ErrorException, BusyCellException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Collections.singletonList("D6"));
        ArrayList<String> blackCheckers = new ArrayList<>(Arrays.asList("e5" , "F4"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("D6");
        movesHandler.getEnemyCheckerHash("G3");
    }

    @Test(expected = BusyCellException.class)
    public void testHasAllyInMove() throws ErrorException, BusyCellException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Arrays.asList("D6","e5"));
        ArrayList<String> blackCheckers = new ArrayList<>(Collections.singletonList("F4"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("D6");
        movesHandler.getEnemyCheckerHash("G3");
    }

    @Test
    public void testCorrectAttack() throws ErrorException, BusyCellException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Collections.singletonList("D6"));
        ArrayList<String> blackCheckers = new ArrayList<>(Arrays.asList("G3","e5"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("D6");
        movesHandler.getEnemyCheckerHash("F4");

        whiteCheckers = new ArrayList<>(Arrays.asList("D6","G3"));
        blackCheckers = new ArrayList<>(Collections.singletonList("e5"));
        movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("D6");
        movesHandler.getEnemyCheckerHash("F4");
    }

    @Test
    public void testAttackMovement() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        ArrayList<String> whiteCheckers = new ArrayList<>(Collections.singletonList("E5"));
        ArrayList<String> blackCheckers = new ArrayList<>(Arrays.asList("b2", "F4", "c7"));
        MovesHandler movesHandler = new MovesHandler(whiteCheckers, blackCheckers);

        movesHandler.setCurrentCells("E5");
        movesHandler.attackMovement("A1");
        ArrayList<String> expectWhiteCheckers = new ArrayList<>(Collections.singletonList("A1"));
        ArrayList<String> expectBlackCheckers = new ArrayList<>(Arrays.asList("F4", "c7"));
        Assert.assertArrayEquals(expectWhiteCheckers.toArray(), whiteCheckers.toArray());
        Assert.assertArrayEquals(expectBlackCheckers.toArray(), blackCheckers.toArray());

        whiteCheckers = new ArrayList<>(Collections.singletonList("D6"));
        movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("D6");
        movesHandler.attackMovement("G3");
        expectWhiteCheckers = new ArrayList<>(Collections.singletonList("G3"));
        expectBlackCheckers = new ArrayList<>(Collections.singletonList("c7"));
        Assert.assertArrayEquals(expectWhiteCheckers.toArray(), whiteCheckers.toArray());
        Assert.assertArrayEquals(expectBlackCheckers.toArray(), blackCheckers.toArray());

        whiteCheckers = new ArrayList<>(Collections.singletonList("D6"));
        movesHandler = new MovesHandler(whiteCheckers, blackCheckers);
        movesHandler.setCurrentCells("D6");
        movesHandler.attackMovement("B8");
        expectWhiteCheckers = new ArrayList<>(Collections.singletonList("B8"));
        expectBlackCheckers = new ArrayList<>(Collections.emptyList());
        Assert.assertArrayEquals(expectWhiteCheckers.toArray(), whiteCheckers.toArray());
        Assert.assertArrayEquals(expectBlackCheckers.toArray(), blackCheckers.toArray());

    }
    @AfterClass
    public static void endTest(){
        System.out.println("All test pass are successful!");
    }
}
