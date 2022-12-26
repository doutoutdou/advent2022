package com.doutoutdou.advent2022.day10;


import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ExerciseTest {

    @Test
    void exo1() {
        var data = ExerciseUtils.loadFromFileAsStringList("10");
        assert data != null;
        var cpu = new Cpu();
        for (String value : data) {
            if (value.startsWith("noop")) {
                cpu.noop();
            } else {
                String registerMove = value.split(" ")[1];

                if (registerMove.startsWith("-")) {
                    cpu.decreaseRegister(Integer.parseInt(registerMove.substring(1)));
                } else {
                    cpu.increaseRegister(Integer.parseInt(registerMove));
                }

            }
        }

        log.info(cpu.signalStrength);
    }

    @Test
    void exo2() {
        var data = ExerciseUtils.loadFromFileAsStringList("10");
        assert data != null;
        var cpu = new Cpu();
        var crt = new Crt();
        for (String value : data) {
            if (value.startsWith("noop")) {

                crt.draw(cpu.getRegister());
            } else {
                String registerMove = value.split(" ")[1];

                if (registerMove.startsWith("-")) {
                    crt.draw(cpu.getRegister());
                    crt.draw(cpu.getRegister());
                    cpu.decreaseRegister(Integer.parseInt(registerMove.substring(1)));
                } else {
                    crt.draw(cpu.getRegister());
                    crt.draw(cpu.getRegister());
                    cpu.increaseRegister(Integer.parseInt(registerMove));
                }

            }
        }
        for (String line : crt.lines) {
            log.info(line);
        }
    }

    static class Crt {
        private int position = 0;

        List<String> lines = new ArrayList<>();

        private StringBuilder sb = new StringBuilder();

        private void draw(int spritePosition) {
            if (position == spritePosition || position == spritePosition + 1 || position == spritePosition - 1) {
                sb.append("#");
            } else {
                sb.append(".");
            }
            if (position == 39) {
                lines.add(sb.toString());
                sb = new StringBuilder();
                position = 0;
            } else {
                position++;
            }
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    static class Cpu {
        private int register = 1;
        private int nbCycle = 0;

        private int signalStrength = 0;

        void noop() {
            updateNbCycle();
        }

        void updateNbCycle() {
            nbCycle++;
            if (nbCycle == 20 || nbCycle == 60 || nbCycle == 100 || nbCycle == 140 || nbCycle == 180 || nbCycle == 220) {
                signalStrength += nbCycle * register;
            }
        }

        void increaseRegister(int value) {
            updateNbCycle();
            updateNbCycle();
            register += value;
        }

        void decreaseRegister(int value) {
            updateNbCycle();
            updateNbCycle();
            register -= value;
        }


    }

}
