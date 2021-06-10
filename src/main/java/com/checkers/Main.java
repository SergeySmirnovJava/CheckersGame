package com.checkers;

import com.checkers.game.MovesValidator;
import com.checkers.game.SyntaxValidator;
import com.checkers.moves.CheckerGame;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        List<String> checkers = Files.readAllLines(Paths.get("moves.txt"));
        String whiteCheckers = checkers.get(0);
        String blackCheckers = checkers.get(1);
        SyntaxValidator syntaxValidator = new SyntaxValidator();
        MovesValidator movesValidator = new MovesValidator(whiteCheckers, blackCheckers);
        checkers = checkers.subList(2, checkers.size());
        String tempMove;
        for (String moves : checkers){
            tempMove = syntaxValidator.handleMove(moves);
            movesValidator.handleMove(tempMove);
        }
        System.out.println(movesValidator);

    }
}
