package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;

public class QueenMoveRegular {
    CheckerGame queenChecker;
    ArrayList<String> whiteCells;
    ArrayList<String> blackCells;

    @BeforeClass
    public static void greetTest(){
        System.out.println("Start testing white queen checkers regular moves");
        System.out.println("--------------------------------------------------");
    }

    @Before
    public void setup(){
        whiteCells = new ArrayList<>(Arrays.asList("a1", "A3", "b2", "E3"));
        blackCells = new ArrayList<>(Arrays.asList("b6","g7", "h6", "h8"));
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

    @Test(expected = WhiteCellException.class)
    public void whiteCell() throws ErrorException, WhiteCellException {
        queenChecker.setCurrentCell("A3");
        queenChecker.getNeighbours("A8");
    }

    @Test
    public void correctMove() throws ErrorException, WhiteCellException {
        int expectedArea = "B4".hashCode() - "A3".hashCode();
        queenChecker.setCurrentCell("A3");
        int area = queenChecker.getNeighbours("B4");
        Assert.assertEquals(expectedArea, area);

        expectedArea = "D6".hashCode() - "A3".hashCode();
        area = queenChecker.getNeighbours("D6");
        Assert.assertEquals(expectedArea, area);
    }

    @Test
    public void enemyInArea() throws ErrorException {
        boolean expectResult = true;
        queenChecker.setCurrentCell("E3");
        boolean result = queenChecker.checkEnemyArea();
        Assert.assertEquals(expectResult, result);
    }

    @Test
    public void freeArea() throws ErrorException {
        boolean expectResult = false;
        queenChecker.setCurrentCell("A3");
        boolean result = queenChecker.checkEnemyArea();
        Assert.assertEquals(expectResult, result);
    }

    @Test
    public void regularMove() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        ArrayList<String> expectedCells = new ArrayList<>(Arrays.asList("a1", "D6", "b2", "E3"));
        queenChecker.setCurrentCell("A3");
        queenChecker.regularMove("D6");
        Assert.assertArrayEquals(expectedCells.toArray(), whiteCells.toArray());
    }

    @AfterClass
    public static void endTest(){
        System.out.println("All tests passed successfully!");
    }
}
