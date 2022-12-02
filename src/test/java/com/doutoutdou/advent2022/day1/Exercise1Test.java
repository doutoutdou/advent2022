package com.doutoutdou.advent2022.day1;

import com.doutoutdou.advent2022.utils.ExerciceUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

@Log4j2
class Exercise1Test {

    @Test
        // Version naive avec un for
    void version1() {
        var data = ExerciceUtils.loadFromFileAsStringList("1");
        long startTime = System.nanoTime();

        int biggestValue = 0;
        int currentValue = 0;
        for (int i = 0; i < Objects.requireNonNull(data).size(); i++) {
            if ("".equals(data.get(i))) {
                if (currentValue > biggestValue) {
                    biggestValue = currentValue;
                }
                currentValue = 0;
            } else {
                currentValue += Integer.parseInt(data.get(i));
            }
        }

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

        System.out.println(biggestValue);
    }

    @Test
    void version2() {
        // pas de moi, pour comprendre comment le faire sans un for
        var data = ExerciceUtils.loadFromFile("1");
        long startTime = System.nanoTime();

        int value = Arrays.stream(data.split("\n\n")).mapToInt(s -> Arrays.stream(s.split("\n")).mapToInt(Integer::parseInt).sum()).max().getAsInt();

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

        System.out.println(value);

    }

    // Première solution + rapide
//    02:24:08.962 [main] INFO com.doutoutdou.advent2022.day1.Exercise1Test - Duration 2280958ns
//70296
//        02:24:08.985 [main] INFO com.doutoutdou.advent2022.day1.Exercise1Test - Duration 6413644ns
//70296

}


