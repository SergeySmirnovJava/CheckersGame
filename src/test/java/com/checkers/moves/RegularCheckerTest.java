package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;

public class RegularCheckerTest {
    RegularChecker regularChecker;
    ArrayList<String> whiteCells;
    ArrayList<String> blackCells;
    ArrayList<String> expectedCell;

    @BeforeClass
    public static void greetTest(){
        System.out.println("Start testing white regular checkers regular moves");
        System.out.println("--------------------------------------------------");
    }
    @Before
    public void setup(){
        whiteCells = new ArrayList<>(Arrays.asList("a1", "a3", "b2", "c1", "c3", "d2", "e1", "e3", "f2", "g1", "g3", "h2"));
        blackCells = new ArrayList<>(Arrays.asList("a7", "b6", "b8", "c7", "d6", "d8", "e7", "f6", "f8", "g7", "h6", "h8"));
        regularChecker = new RegularChecker(whiteCells, blackCells);
    }


    @Test(expected = ErrorException.class)
    public void whiteCheckerIsContain() throws ErrorException {
        regularChecker.setCurrentCell("d4");
    }

    @Test(expected = BusyCellException.class)
    public void checkAreaNextCell() throws BusyCellException, InvalidMoveException {
        regularChecker.checkRegularMoves("a3");
    }

    @Test(expected = InvalidMoveException.class)
    public void checkEnemyChecker() throws BusyCellException, InvalidMoveException {
        regularChecker.checkRegularMoves("a7");
    }

    @Test(expected = InvalidMoveException.class)
    public void checkQueenMove() throws BusyCellException, InvalidMoveException {
        ArrayList<String> whiteCellsTemp = new ArrayList<>(Arrays.asList("a1", "a7"));
        ArrayList<String> blackCellsTemp = new ArrayList<>(Arrays.asList("h8", "g7"));
        RegularChecker regularCheckerTemp = new RegularChecker(whiteCellsTemp, blackCellsTemp);
        regularCheckerTemp.checkRegularMoves("b8");
    }
    @Test
    public void swapWhiteCheckers(){
        String[] expectedSwap = {"a7", "b6", "b8", "c7", "d6", "d8", "e7", "f6", "f8", "g7", "h6", "h8"};
        regularChecker.swapCheckers();
        Assert.assertArrayEquals(expectedSwap, whiteCells.toArray());
    }

    @Test
    public void swapBlackCheckers(){
        String[] expectedSwap = {"a1", "a3", "b2", "c1", "c3", "d2", "e1", "e3", "f2", "g1", "g3", "h2"};
        regularChecker.swapCheckers();
        Assert.assertArrayEquals(expectedSwap, blackCells.toArray());
    }


    @AfterClass
    public static void endTest(){
        System.out.println("All tests passed successfully!");
    }
}