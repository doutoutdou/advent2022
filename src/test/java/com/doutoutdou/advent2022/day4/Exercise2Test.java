package com.doutoutdou.advent2022.day4;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Log4j2
class Exercise2Test extends CommonTest {

    @Test
    void version2() {
        run(exercice2Predicate);
    }

    // Retourne vrai si
    // la première valeur est égale à la 3ème ou la 4eme ou la deuxième valeur est égale à la 3ème ou la 4ème
    // OU SI
    // la première valeur est supérieure à la  3ème et inférieure à la 4eme
    // OU SI
    // la deuxième valeur est supérieure à la  3ème et inférieure à la 4eme
    // OU SI
    // la première valeur est inférieure à la 3ème valeur et la deuxième valeur est supérieure à la 4eme
    Predicate<List<Integer>> exercice2Predicate = e -> (Objects.equals(e.get(0), e.get(2)) || Objects.equals(e.get(0), e.get(3)) || Objects.equals(e.get(1), e.get(2)) || Objects.equals(e.get(1), e.get(3))) || (e.get(0) > e.get(2) && e.get(0) < e.get(3)) || (e.get(1) > e.get(2) && e.get(1) < e.get(3)) || (e.get(0) < e.get(2) && e.get(1) > e.get(3));
//22:12:15.070 [main] INFO com.doutoutdou.advent2022.day4.CommonTest - Duration 1547954ns
//22:12:15.070 [main] INFO com.doutoutdou.advent2022.day4.CommonTest - 515
}



