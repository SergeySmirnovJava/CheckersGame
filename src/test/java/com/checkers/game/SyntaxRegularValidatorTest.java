package com.checkers.game;

import com.checkers.exceptions.ErrorException;
import org.junit.*;

import static org.junit.Assert.*;

public class SyntaxRegularValidatorTest {
    SyntaxValidator syntaxValidator;

    @BeforeClass
    public static void greetTest(){
        System.out.println("Test move syntax");
    }
    @Before
    public void setup(){
        syntaxValidator = new SyntaxValidator();
    }

    @Test
    public void handleRegularMove() throws Exception {
        String regularMove = "a3-b4 a2-h3";
        String result = syntaxValidator.handleMove(regularMove);
        Assert.assertEquals(regularMove, result);
    }

    @Test(expected = ErrorException.class)
    public void manyMoves() throws Exception {
        String regularMove = "a3-b4 a2-h3 a5-b6";
        syntaxValidator.handleMove(regularMove);
    }

    @Test(expected = ErrorException.class)
    public void wordError() throws Exception {
        String regularMove = "a3-i4 a2-h3";
        syntaxValidator.handleMove(regularMove);
    }

    @Test(expected = ErrorException.class)
    public void numberError() throws Exception{
        String regularMove = "a9-b4 a2-h3";
        syntaxValidator.handleMove(regularMove);
    }

    @Test
    public void handleAttackMove() throws Exception {
        String attackMove = "a3:b3 a2-h3";
        String result = syntaxValidator.handleMove(attackMove);
        Assert.assertEquals(attackMove, result);

        attackMove = "a3:b4 a2:h3";
        result = syntaxValidator.handleMove(attackMove);
        Assert.assertEquals(attackMove, result);

        attackMove = "a3-b4 a2:h3";
        result = syntaxValidator.handleMove(attackMove);
        Assert.assertEquals(attackMove, result);

        attackMove = "a3:b4:c5 a2-h3";
        result = syntaxValidator.handleMove(attackMove);
        Assert.assertEquals(attackMove, result);

        attackMove = "a3-b4 a2:h3:c5";
        result = syntaxValidator.handleMove(attackMove);
        Assert.assertEquals(attackMove, result);

        attackMove = "a3:b4:c4 a2:h3";
        result = syntaxValidator.handleMove(attackMove);
        Assert.assertEquals(attackMove, result);
    }

    @AfterClass
    public static void endTest(){
        System.out.println("All test are successful");
    }
}