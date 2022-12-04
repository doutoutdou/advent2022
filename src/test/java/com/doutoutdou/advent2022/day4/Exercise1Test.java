package com.doutoutdou.advent2022.day4;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

@Log4j2
class Exercise1Test extends CommonTest {

    @Test
    void version1() {
        run(exercice1Predicate);
    }

    // Retourne vrai si
    // la première valeur est inférieure ou égale à la  3ème et la deuxième valeur est supérieure ou égale à la 4eme
    // OU SI
    // la première valeur est supérieure ou égale à la  3ème et la deuxième valeur est inférieure ou égale à la 4eme
    Predicate<List<Integer>> exercice1Predicate = e -> e.get(0) <= e.get(2) && e.get(1) >= e.get(3) || e.get(0) >= e.get(2) && e.get(1) <= e.get(3);

//22:12:15.051 [main] INFO com.doutoutdou.advent2022.day4.CommonTest - Duration 5677394ns
//22:12:15.055 [main] INFO com.doutoutdou.advent2022.day4.CommonTest - 883
}


