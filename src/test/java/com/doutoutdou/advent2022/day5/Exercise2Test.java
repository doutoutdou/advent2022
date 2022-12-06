package com.doutoutdou.advent2022.day5;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

@Log4j2
public class Exercise2Test extends CommonTest {

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

        // Pour lexercice 2, si + d'1 caisse à déplacer alors il faut garder lordre
        if (move > 1) {
            int j = move;
            for (int i = 0; i < move; i++) {
                // si + d'une caisse a deplacer alors on déplace la première dans la liste puis on passe aux suivantes
                output.addLast(input.remove(input.size() - j));
                j--;
            }
        } else {
            // Si 1 seule caisse alors on déplace juste du top d'une stack vers celle voulue
            output.addLast(input.remove(input.size() - 1));
        }
    };
}