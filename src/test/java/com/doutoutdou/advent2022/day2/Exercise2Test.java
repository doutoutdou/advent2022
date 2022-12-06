package com.doutoutdou.advent2022.day2;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Objects;

@Log4j2
class Exercise2Test {

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

    // A Z ROCK
    // B Y PAPER
    // C Z SCISSORS
    private int getScore(String input) {
        return switch (input) {
            case "A Z" -> 2 + 6;
            case "B X" -> 1 + 0;
            case "C Y" -> 3 + 3;
            case "A Y" -> 1 + 3;
            case "C X" -> 2 + 0;
            case "B Z" -> 3 + 6;
            case "A X" -> 3 + 0;
            case "B Y" -> 2 + 3;
            case "C Z" -> 1 + 6;
            default -> 0;
        };
    }

    // Première solution + rapide
//07:07:25.625 [main] INFO com.doutoutdou.advent2022.day2.Exercise2Test - Duration 984439ns
//12014
//        07:07:25.647 [main] INFO com.doutoutdou.advent2022.day2.Exercise2Test - Duration 2755597ns
//12014


}


