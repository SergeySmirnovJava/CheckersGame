package com.checkers.moves;

import com.checkers.exceptions.BusyCellException;
import com.checkers.exceptions.ErrorException;
import com.checkers.exceptions.InvalidMoveException;
import com.checkers.exceptions.WhiteCellException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckerMoveAttack {
    CheckerGame regularChecker;
    ArrayList<String> whiteCells;
    ArrayList<String> blackCells;

    @BeforeClass
    public static void greetTest(){
        System.out.println("Start testing white regular checkers attack moves");
        System.out.println("--------------------------------------------------");
    }
    @Before
    public void setup(){
        whiteCells = new ArrayList<>(Arrays.asList("b4", "d4", "f4", "g1"));
        blackCells = new ArrayList<>(Arrays.asList("c5", "c7", "e5", "g7", "h8"));
        regularChecker = new CheckerGame(whiteCells, blackCells);
    }

    @Test
    public void attack() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        ArrayList<String> expectedCurrent = new ArrayList<>(Arrays.asList("d6", "d4", "f4", "g1"));
        ArrayList<String> expectedOpposite = new ArrayList<>(Arrays.asList("c7", "e5", "g7", "h8"));
        regularChecker.setCurrentCell("b4");
        regularChecker.attackMove("d6");
        Assert.assertArrayEquals(expectedCurrent.toArray(), whiteCells.toArray());
        Assert.assertArrayEquals(expectedOpposite.toArray(), blackCells.toArray());
    }

    @Test(expected = InvalidMoveException.class)
    public void regularMove() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        regularChecker.setCurrentCell("b4");
        regularChecker.attackMove("c5");
    }

    @Test(expected = ErrorException.class)
    public void falseAttack() throws ErrorException, WhiteCellException, BusyCellException, InvalidMoveException {
        regularChecker.setCurrentCell("g1");
        regularChecker.attackMove("e3");
    }
}
