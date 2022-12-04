package com.doutoutdou.advent2022.day4;

import com.doutoutdou.advent2022.utils.ExerciceUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Log4j2
class Exercise2Test {

    @Test
        // Version très naive
    void version1() {
        var data = ExerciceUtils.loadFromFileAsStringList("4");
        int sum = 0;
        assert data != null;
        for (String value : data) {
            //    2-88,13-89
            // On récupère donc 2 string
            String[] values = value.split(",");
            var v0Index = values[0].indexOf("-");
            var v1 = Integer.parseInt(values[0].substring(0, v0Index));
            var v2 = Integer.parseInt(values[0].substring(v0Index + 1));
            var v1Index = values[1].indexOf("-");
            var v3 = Integer.parseInt(values[1].substring(0, v1Index));
            var v4 = Integer.parseInt(values[1].substring(v1Index + 1));
            if ((v1 == v3 || v1 == v4 ||  v2 == v3 || v2 == v4) || (v1 > v3 && v1 < v4) || (v2 > v3 && v2 < v4) || (v1 < v3 && v2 > v4)) {
                sum++;
            }
        }
        long startTime = System.nanoTime();

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");
        log.info(sum);

    }


//    18:02:59.135 [main] INFO com.doutoutdou.advent2022.day4.Exercise2Test - Duration 1022ns
//18:02:59.137 [main] INFO com.doutoutdou.advent2022.day4.Exercise2Test - 883
}



