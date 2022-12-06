package com.doutoutdou.advent2022.day3;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Log4j2
class Exercise2Test {

    private final static String lowercase = "abcdefghijklmnopqrstuvwxyz";
    private final static String uppercase = lowercase.toUpperCase();
    private final static Map<String, Integer> letterValue = new HashMap<>();

    @BeforeAll
    static void initMap() {
        populateMap(1, lowercase);
        populateMap(27, uppercase);
    }

    /**
     * utilisé pour peupler une map
     * @param value la lettre
     * @param input la valeur associée
     */
    private static void populateMap(int value, String input) {
        for (char c : input.toCharArray()) {
            letterValue.put(String.valueOf(c), value);
            value++;
        }
    }

    @Test
    void version1() {
        var data = ExerciseUtils.loadFromFileAsStringList("3");

        long startTime = System.nanoTime();
        assert data != null;
        List<String> commonLetters = new ArrayList<>();
        for (int i = 0; i < data.size(); i+= 3)  {
            // Faudrait voir comment faire ca avec un stream de plusieurs éléments mais la flemme
            searchCommonChar(data.get(i), data.get(i+1), data.get(i+2)).ifPresent(commonLetters::add);
        }

        Set<Map.Entry<String, Integer>> letterValueSet = letterValue.entrySet();

        // On compte le nombre de fois où la lettre apparait
        Map<String, Long> stringWithOccurence = commonLetters.stream()
                .collect(groupingBy(v -> v, Collectors.counting()));

        // On itère sur chaque clé
        // On récupère la valeur associée à cette clé et on multiplie par le nombre de fois où la clé est présente
        // Puis on flatmap pour obtenir une seule liste et enfin on fait la somme
        long sum = stringWithOccurence.entrySet().stream().map(inputES -> {
            var key = inputES.getKey();
            return letterValueSet.stream()
                    .filter(letterES -> key.equals(letterES.getKey()))
                    .map(letterES -> letterES.getValue() * inputES.getValue())
                    .findFirst();
        }).toList()
                .stream()
                .flatMap(Optional::stream)
                .toList()
                .stream()
                .mapToLong(value -> value)
                .sum();

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");
        log.info(sum);

    }

//22:55:57.177 [main] INFO com.doutoutdou.advent2022.day3.Exercise2Test - Duration 23532833ns
//22:55:57.181 [main] INFO com.doutoutdou.advent2022.day3.Exercise2Test - 2644

    /**
     * Recherche la lettre qui est commune entre les 3 string (1 seule lettre est commune)
     * @param input1
     * @param input2
     * @param input3
     * @return la lettre commune entre les 3 inputs
     */
    private Optional<String> searchCommonChar(String input1, String input2, String input3) {
        // On cherche toutes les lettres communes entre le 1 et le 2
        List<String> strings = Arrays.stream(input1.split(""))
                .filter(input2::contains)
                .toList();
        // De toutes les lettres communes entre 1 et 2, 1 seule doit se trouver dans le 3
        return strings.stream()
                .filter(input3::contains)
                .findFirst();
    }
}


