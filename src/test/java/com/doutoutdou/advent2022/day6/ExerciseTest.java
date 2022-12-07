package com.doutoutdou.advent2022.day6;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

@Log4j2
class ExerciseTest {

    @Test
    void exo1() {
        run(4);

    }
    @Test
    void exo2() {
        run(14);
    }

    private void run(Integer length) {
        var data = ExerciseUtils.loadFromFile("6");
        long startTime = System.nanoTime();
        int result = 0;
        assert data != null;

        for (int i = 0; i < data.length() - (length - 1); i++) {
            // on recupere N caracteres et on met dans un SET
            // vu qu un set naccepte pas de duplica, si la taille est de N alors on est bon
            // sinon il faut continuer diterer
            String substring = data.substring(i, i + length);
            if (Arrays.stream(substring.split("")).collect(Collectors.toSet()).size() == length) {
                result = i + length;
                break;
            }
            // Sinon on peut faire via un tableau de char (trouve sur le net javoue)
//            if (substring.chars().distinct().sum() == length) {
//                result = i + length;
//                break;
//            }
        }

        log.info(result);

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

    }
}
