package com.doutoutdou.advent2022.day4;

import com.doutoutdou.advent2022.utils.ExerciceUtils;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Log4j2
public abstract class CommonTest {

    void run(Predicate<List<Integer>> predicate) {
        var data = ExerciceUtils.loadFromFileAsStringList("4");
        long startTime = System.nanoTime();
        int sum = 0;
        assert data != null;
        List<Integer> values = new ArrayList<>();
        for (String line : data) {
            //    2-88,13-89
            // On récupère donc 2 string
            String[] pairs = line.split(",");

            for (String element : pairs) {
                addElementsToList(element, values);
            }

            if (predicate.test(values)) {
                sum++;
            }

            values.clear();
        }


        log.info("Duration " + (System.nanoTime() - startTime) + "ns");
        log.info(sum);
    }

    /**
     * Split le string sur le "-" puis ajoute les 2 valeurs à la liste
     *
     * @param input
     * @param values
     */
    private void addElementsToList(String input, List<Integer> values) {
        var index = input.indexOf("-");

        values.add(Integer.parseInt(input.substring(0, index)));
        values.add(Integer.parseInt(input.substring(index + 1)));
    }
}
