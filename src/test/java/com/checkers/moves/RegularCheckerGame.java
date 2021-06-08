package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;

public class RegularCheckerGame {
    CheckerGame regularChecker;
    ArrayList<String> whiteCells;
    ArrayList<String> blackCells;

    @BeforeClass
    public static void greetTest(){
        System.out.println("Start testing white regular checkers regular moves");
        System.out.println("--------------------------------------------------");
    }
    @Before
    public void setup(){
        whiteCells = new ArrayList<>(Arrays.asList("a1", "a3", "b2", "c1", "c3", "d2", "e1", "e3", "f2", "g1", "g3", "h2"));
        blackCells = new ArrayList<>(Arrays.asList("a7", "b6", "b8", "c7", "d6", "d8", "e7", "f6", "f8", "g7", "h6", "h8"));
        regularChecker = new CheckerGame(whiteCells, blackCells);
    }

    @Test
    public void swapCheckersSide(){
        String[] expectedCurrentCells = new String[]{"a7", "b6", "b8", "c7", "d6", "d8", "e7", "f6", "f8", "g7", "h6", "h8"};
        regularChecker.swapCheckers();
        Assert.assertArrayEquals(expectedCurrentCells, whiteCells.toArray());
    }

    @Test(expected = ErrorException.class)
    public void setCurrentCell() throws ErrorException {
        regularChecker.setCurrentCell("a7");
    }

    @Test(expected = BusyCellException.class)
    public void currentContainsNext() throws ErrorException, BusyCellException, InvalidMoveException {
        regularChecker.setCurrentCell("a1");
        regularChecker.checkMove("b2");
    }

    @Test(expected = InvalidMoveException.class)
    public void oppositeContainsNext() throws ErrorException, BusyCellException, InvalidMoveException {
        regularChecker.setCurrentCell("a1");
        regularChecker.checkMove("a7");
    }

    @Test(expected = InvalidMoveException.class)
    public void nextCellIsQueen() throws ErrorException, BusyCellException, InvalidMoveException {
        ArrayList<String> tempWhiteCheckers = new ArrayList<>(Arrays.asList("a7", "b6"));
        ArrayList<String> tempBlackCheckers = new ArrayList<>(Arrays.asList("h6", "f6"));
        CheckerGame tempRegular = new CheckerGame(tempWhiteCheckers, tempBlackCheckers);
        tempRegular.setCurrentCell("a7");
        tempRegular.checkMove("b8");
    }

    @Test(expected = InvalidMoveException.class)
    public void cellIsQueen() throws ErrorException, BusyCellException, InvalidMoveException {
        ArrayList<String> tempWhiteCheckers = new ArrayList<>(Arrays.asList("A7", "b6"));
        ArrayList<String> tempBlackCheckers = new ArrayList<>(Arrays.asList("h6", "f6"));
        CheckerGame tempRegular = new CheckerGame(tempWhiteCheckers, tempBlackCheckers);
        tempRegular.setCurrentCell("A7");
        tempRegular.checkMove("b8");
    }

    @Test(expected = WhiteCellException.class)
    public void regularCellNotWhite() throws ErrorException, WhiteCellException {
        regularChecker.setCurrentCell("a1");
        regularChecker.getNeighbours("a2");
    }

    @Test
    public void correctNextMove() throws ErrorException, WhiteCellException {
        int expectedArea = "b4".hashCode() - "a3".hashCode();
        regularChecker.setCurrentCell("a3");
        int resultArea = regularChecker.getNeighbours("b4");
        Assert.assertEquals(expectedArea, resultArea);
    }

    @Test(expected = ErrorException.class)
    public void regularLongMove() throws ErrorException, WhiteCellException {
        regularChecker.setCurrentCell("a3");
        regularChecker.getNeighbours("c5");
    }

    @Test
    public void noEnemyInArea() throws ErrorException, InvalidMoveException {
        boolean expectedMove = false;
        regularChecker.setCurrentCell("a3");
        boolean move = regularChecker.checkEnemyArea("b4");
        Assert.assertEquals(expectedMove, move);
    }

    @Test
    public void enemyInArea() throws ErrorException, BusyCellException, InvalidMoveException {
        ArrayList<String> tempWhiteCheckers = new ArrayList<>(Arrays.asList("e3", "b6"));
        ArrayList<String> tempBlackCheckers = new ArrayList<>(Arrays.asList("d4", "f6"));
        CheckerGame tempRegular = new CheckerGame(tempWhiteCheckers, tempBlackCheckers);
        tempRegular.setCurrentCell("e3");
        boolean expectedMove = true;
        boolean move = tempRegular.checkEnemyArea("f4");
        Assert.assertEquals(expectedMove, move);
    }

    @Test
    public void regularMove() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        ArrayList<String> expectedCells = new ArrayList<>(Arrays.asList("a1", "b4", "b2", "c1", "c3", "d2", "e1", "e3", "f2", "g1", "g3", "h2"));
        regularChecker.setCurrentCell("a3");
        regularChecker.regularMove("b4");
        Assert.assertArrayEquals(expectedCells.toArray(), whiteCells.toArray());
    }

    @AfterClass
    public static void endTest(){
        System.out.println("All tests passed successfully!");
    }
}