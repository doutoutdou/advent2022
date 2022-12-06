package com.doutoutdou.advent2022.day2;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Objects;

@Log4j2
class Exercise1Test {

    @Test
    void version1() {
        var data = ExerciseUtils.loadFromFileAsStringList("2");
        long startTime = System.nanoTime();

        int score = 0;
        for (int i = 0; i < Objects.requireNonNull(data).size(); i++) {
            score += getScore(data.get(i));
        }

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

        System.out.println(score);
    }

    // https://stackoverflow.com/questions/24308146/why-is-a-combiner-needed-for-reduce-method-that-converts-type-in-java-8
    @Test
    void version2() {
        var data = ExerciseUtils.loadFromFileAsStringList("2");
        long startTime = System.nanoTime();

        int score = data
                .stream()
                .reduce(0,
                        (accumulatedInt, str) -> accumulatedInt + getScore(str),                 //accumulator
                        Integer::sum); //combiner


        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

        System.out.println(score);

    }

    private int getScore(String input) {
        return switch (input) {
            case "A Z" -> 3;
            case "B X" -> 1;
            case "C Y" -> 2;
            case "A Y" -> 2 + 6;
            case "C X" -> 1 + 6;
            case "B Z" -> 3 + 6;
            case "A X" -> 1 + 3;
            case "B Y" -> 2 + 3;
            case "C Z" -> 3 + 3;
            default -> 0;
        };
    }
    // Première solution + rapide
//06:57:19.240 [main] INFO com.doutoutdou.advent2022.day2.Exercise1Test - Duration 961891ns
//11873
//        06:57:19.259 [main] INFO com.doutoutdou.advent2022.day2.Exercise1Test - Duration 2587629ns
//11873

}


