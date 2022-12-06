package com.doutoutdou.advent2022.day5;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

@Log4j2
public class Exercise1Test extends CommonTest {

    @Test
    void version1() {
        run(move);
    }

    /**
     * Methode réalisant le déplacement des caisses
     */
    protected BiConsumer<List<Integer>, List<LinkedList<String>>> move = (movements, puzzle) -> {
        // Le nombre de caisse à déplacer
        var move = movements.get(0);

        // La liste pour laquelle il faut enlever des caisses
        LinkedList<String> input = puzzle.get(movements.get(1) - 1);
        // La liste pour laquelle il faut ajouter des caisses
        LinkedList<String> output = puzzle.get(movements.get(2) - 1);

        // on déplace du top d'une stack vers celle voulue
        for (int i = 0; i < move; i++) {
            output.addLast(input.remove(input.size() - 1));
        }
    };

}