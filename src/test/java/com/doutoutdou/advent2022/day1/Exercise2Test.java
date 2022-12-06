package com.doutoutdou.advent2022.day1;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Log4j2
class Exercise2Test {

    @Test
        // Version naive avec un for
    void version1() {
        var data = ExerciseUtils.loadFromFileAsStringList("1");

        long startTime = System.nanoTime();
        int currentValue = 0;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(data).size(); i++) {
            if ("".equals(data.get(i))) {
                list.add(currentValue);
                currentValue = 0;
            } else {
                currentValue += Integer.parseInt(data.get(i));
            }
        }
        // Cest nul car on ajoute tous les éléments alors qu'on sait que l'on ne souhaite en récupérer que 3
        List<Integer> sorted = list.stream().sorted((x, y) -> Integer.compare(y, x)).toList();

//        var expectedValue = sorted.subList(0, 3).stream().reduce(0, Integer::sum);
        var expectedValue = sorted.subList(0, 3).stream().mapToInt(Integer::valueOf)
                .sum();

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");
        log.debug(expectedValue);
    }

    @Test
        // Version naive avec un for mais sans ajouter tous les élements à la liste
    void version2() {
        var data = ExerciseUtils.loadFromFileAsStringList("1");

        long startTime = System.nanoTime();
        int maxElement = 3;
        int currentValue = 0;
        LinkedList<Integer> list = new LinkedList<>();
        boolean full = false;
        for (int i = 0; i < Objects.requireNonNull(data).size(); i++) {
            if ("".equals(data.get(i))) {
                if (!full) {
                    list.add(currentValue);
                    currentValue = 0;
                    if (maxElement == list.size()) {
                        // Si on arrive a 3 élements alors on trie la liste (dans le sens inverse) et indiqu qu'elle est pleine
                        list.sort((x, y) -> Integer.compare(y, x));
                        full = true;
                    }
                } else {
                   if (currentValue > list.getLast()) {
                       // Si la valeur courante est supérieure à la plus petite valeur alors on remplace
                       list.set(maxElement-1, currentValue);
                       // Il faut retrier la liste après ajout car la dernière valeur ajoutée n'est peut être pas la plus faible des 3
                       list.sort((x, y) -> Integer.compare(y, x));
                   }
                   currentValue = 0;
                }
            } else {
                currentValue += Integer.parseInt(data.get(i));
            }
        }

        var expectedValue = list.subList(0, 3).stream().mapToInt(Integer::valueOf)
                .sum();

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

        log.debug(expectedValue);
    }

    // 2nd solution + rapide
//    02:24:44.312 [main] INFO com.doutoutdou.advent2022.day1.Exercise2Test - Duration 5821496ns
//02:24:44.315 [main] DEBUG com.doutoutdou.advent2022.day1.Exercise2Test - 205381
//            02:24:44.329 [main] INFO com.doutoutdou.advent2022.day1.Exercise2Test - Duration 2414957ns
//02:24:44.329 [main] DEBUG com.doutoutdou.advent2022.day1.Exercise2Test - 205381

}


