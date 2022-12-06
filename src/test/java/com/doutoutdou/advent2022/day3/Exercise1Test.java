package com.doutoutdou.advent2022.day3;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Log4j2
class Exercise1Test {

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
        var duplicateLetters = data.stream().map(s -> {
            var input1 = s.substring(0, s.length() / 2);
            var input2 = s.substring(s.length() / 2);
            return searchDuplicatedChar(input1, input2);
        }).toList()
                .stream()
                .flatMap(Optional::stream).toList();

        Set<Map.Entry<String, Integer>> letterValueSet = letterValue.entrySet();

        // On compte le nombre de fois où la lettre apparait
        Map<String, Long> stringWithOccurence = duplicateLetters.stream().collect(groupingBy(v -> v, Collectors.counting()));

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

// 22:19:10.871 [main] INFO com.doutoutdou.advent2022.day3.Exercise1Test - Duration 16589986ns
//22:19:10.874 [main] INFO com.doutoutdou.advent2022.day3.Exercise1Test - 7701

    /**
     * Recherche pour si une lettre de l'input1 est aussi présente dans l'input2
     * @param input1
     * @param input2
     * @return la lettre trouvée dans les 2 input sinon empty, sarrete a la premiere lettre trouvee
     */
    private Optional<String> searchDuplicatedChar(String input1, String input2) {
        return Arrays.stream(input1.split(""))
                .filter(input2::contains)
                .findFirst();
    }
}


