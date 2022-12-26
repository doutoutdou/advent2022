package com.doutoutdou.advent2022.day9;


import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

@Log4j2
public class ExerciseTest {

    @Test
    void exo1() {
        run(1);
    }

    @Test
    void exo2() {
        run(9);
    }

    void run(int ropeLength) {
        var data = ExerciseUtils.loadFromFileAsStringList("9");
        assert data != null;
        // on cree un set pour stocker toutes les coordonnees
        HashSet<Coordinate> tailCoordinates = new HashSet<>();
        var headCoordinate = new Coordinate();
        tailCoordinates.add(new Coordinate());

        var rope = new Rope();
        rope.addKnots(ropeLength);


        for (String input : data) {
            String[] movement = input.split(" ");

            Consumer<Coordinate> action = getAction(movement[0]);
            int distance = Integer.parseInt(movement[1]);

            for (int i = 0; i < distance; i++) {
                action.accept(headCoordinate);
                rope.move(headCoordinate);

                tailCoordinates.add(new Coordinate(rope.knots.get(rope.knots.size() - 1).getX(), rope.knots.get(rope.knots.size() - 1).getY()));

            }
        }

        log.info(tailCoordinates.size());
    }

    /**
     * @param direction la direction du mouvement
     * @return Retourne l'action à effectuer en fonction de la position
     */
    private Consumer<Coordinate> getAction(String direction) {
        return switch (direction) {
            case "U" -> Coordinate::moveUp;
            case "D" -> Coordinate::moveDown;
            case "R" -> Coordinate::moveRight;
            case "L" -> Coordinate::moveLeft;
            default -> throw new IllegalArgumentException();
        };
    }

    @NoArgsConstructor
    @Getter
    static class Rope {
        List<Coordinate> knots = new ArrayList<>();

        void addKnots(int numberOfKnots) {
            for (int i = 0; i < numberOfKnots; i++) {
                // Methode de merde pour rajouter des noeuds
                this.getKnots().add(new Coordinate(0, 0));
            }
        }

        void move(Coordinate coordinateToFollow) {
            // si le 1er noeud bouge et que dautres noeuds, alors on voit s'il faut les faire bouger
            if (this.knots.get(0).move(coordinateToFollow) && this.knots.size() > 1) {
                this.moveOthersKnots(1);
            }
        }

        void moveOthersKnots(int knotIndex) {
            // Tant que l'on n'est pas à la fin et que ca bouge, on bouge le point dapres
            if (knotIndex < this.knots.size() && this.knots.get(knotIndex).move(this.knots.get(knotIndex - 1))) {
                moveOthersKnots(++knotIndex);
            }
        }

    }

    // TODO refacto lobjet qui devrait s'appeler knot et disposer d'un autre objet position peut être ?

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    static class Coordinate {
        private int x;
        private int y;

        public void moveUp() {
            y++;
        }

        void moveDown() {
            y--;
        }

        void moveRight() {
            x++;
        }

        void moveLeft() {
            x--;
        }

        /**
         * @param coordinateToFollow le point à suivre
         * @return true si un mouvement a eu lieu
         */
        boolean move(Coordinate coordinateToFollow) {
            var horizontalMovement = Math.abs(coordinateToFollow.getX() - this.x) > 1;
            var verticalMovement = Math.abs(coordinateToFollow.getY() - this.y) > 1;
            // Il ne faut se déplacer que si la différence est de + de 1 sur l'un des axes
            if (horizontalMovement || verticalMovement) {
                if (coordinateToFollow.getX() == this.x) {
                    verticalMovement(coordinateToFollow);
                } else if (coordinateToFollow.getY() == this.y) {
                    horizontalMovement(coordinateToFollow);
                } else {
                    // Sinon déplacement en diagonale on cumule les 2 mouvements
                    verticalMovement(coordinateToFollow);
                    horizontalMovement(coordinateToFollow);
                }
                return true;
            }
            return false;
        }

        private void verticalMovement(Coordinate coordinateToFollow) {
            // Il ne faut se déplacer que si l'écart est supérieur à 1
            if (coordinateToFollow.getY() > this.y) {
                moveUp();
            } else if (coordinateToFollow.getY() < this.y) {
                moveDown();
            }
        }

        private void horizontalMovement(Coordinate coordinateToFollow) {
            if (coordinateToFollow.getX() > this.x) {
                moveRight();
            } else if (coordinateToFollow.getX() < this.x) {
                moveLeft();
            }
        }

    }


}
