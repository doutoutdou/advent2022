package com.doutoutdou.advent2022.day8;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.doutoutdou.advent2022.day8.CommonTest.buildForrest;

@Log4j2
public class Exercise2Test {

    @Test
    void exo2() {
        long startTime = System.nanoTime();
        Tree[][] forrest = buildForrest();
        var columns = forrest[0].length;
        // On calcule le nombre d'arbres visibles = ceux des extremites
        var max = 0;
        var counter = 0;
        for (Tree[] trees : forrest) {
            for (int j = 0; j < columns; j++) {
                var tree = trees[j];
                if (!tree.isExtremity()) {
                    counter = countTree(tree);
                    max = Math.max(counter, max);
                }
            }
        }
        log.info(max);
        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

    }

    private int countTree(Tree tree) {
        int initialValue = tree.getValue();
        int topCounter = count(tree, initialValue, 0, t -> t.getTop() != null, (t2, value) -> t2.getTop().getValue() < value, Tree::getTop);
        int bottomCounter = count(tree, initialValue, 0, t -> t.getBottom() != null, (t2, value) -> t2.getBottom().getValue() < value, Tree::getBottom);
        int leftCounter = count(tree, initialValue, 0, t -> t.getLeft() != null, (t2, value) -> t2.getLeft().getValue() < value, Tree::getLeft);
        int rightCounter = count(tree, initialValue, 0, t -> t.getRight() != null, (t2, value) -> t2.getRight().getValue() < value, Tree::getRight);

        return topCounter * bottomCounter * leftCounter * rightCounter;
    }

    /**
     *
     * @param tree
     * @param initialTreeValue
     * @param counter
     * @param nextTreeIsNotNull
     * @param shouldContinue
     * @param getTree
     * @return
     */
    private int count(Tree tree, int initialTreeValue, int counter, Predicate<Tree> nextTreeIsNotNull, BiPredicate<Tree, Integer> shouldContinue, Function<Tree, Tree> getTree) {
        if (nextTreeIsNotNull.test(tree)) {
            counter++;
            if (shouldContinue.test(tree, initialTreeValue)) {
                return count(getTree.apply(tree), initialTreeValue, counter, nextTreeIsNotNull, shouldContinue, getTree);
            } else {
                return counter;
            }
        } else {
            return counter > 0 ? counter : 1;
        }

    }


}