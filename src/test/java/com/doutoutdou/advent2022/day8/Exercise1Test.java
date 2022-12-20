package com.doutoutdou.advent2022.day8;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.doutoutdou.advent2022.day8.CommonTest.buildForrest;

@Log4j2
public class Exercise1Test {
    @Test
    void exo1() {

        Tree[][] forrest = buildForrest();
        var columns = forrest[0].length;
        var counter = 0;
        for (Tree[] trees : forrest) {
            for (int j = 0; j < columns; j++) {
                var tree = trees[j];
                // Si cest une extremite, alors il est visible
                if (tree.isExtremity()) {
                    counter++;
                } else {
                    if (isTreeVisible(tree)) {
                        counter++;
                    }
                }
            }
        }
        log.info(counter);
    }

    /**
     * Retourne vrai si un arbre est visible par à de ses 4 côtés
     * @param tree l'arbre à traiter
     * @return
     */
    private boolean isTreeVisible(Tree tree) {
        // TOP
        if(isVisible(tree, tree.getValue(), t -> t.getTop() != null, (t2, value) -> t2.getTop().getValue() < value, Tree::getTop)) {
            return true;
        }
        // BOTTOM
        if(isVisible(tree, tree.getValue(), t -> t.getBottom() != null, (t2, value) -> t2.getBottom().getValue() < value, Tree::getBottom)) {
            return true;
        }
        // LEFT
        if(isVisible(tree, tree.getValue(), t -> t.getLeft() != null, (t2, value) -> t2.getLeft().getValue() < value, Tree::getLeft)) {
            return true;
        }
        // RIGHT
        return isVisible(tree, tree.getValue(), t -> t.getRight() != null, (t2, value) -> t2.getRight().getValue() < value, Tree::getRight);
    }

    /**
     * Retourne vrai si un arbre est visible
     * @param tree l'arbre courant
     * @param initialTreeValue la valeur de l'arbre initial pour lequel on cherche à savoir s'il est visible
     * @param nextTreeIsNotNull prédicat à utiliser pour savoir si l'arbre adjacent est null
     * @param shouldContinue biPrédicat à utiliser pour savoir si l'arbre adjacent cache l'arbre intial
     * @param getTree methode pour récupérer l'arbre adjacent
     * @return true si l'arbre initial est visible, false sinon
     */
    private boolean isVisible(Tree tree, int initialTreeValue, Predicate<Tree> nextTreeIsNotNull, BiPredicate<Tree, Integer> shouldContinue, Function<Tree, Tree> getTree) {
        // On verifie si l'arbre adjacent n'est pas null
        if(nextTreeIsNotNull.test(tree)) {
            // L'arbre adjacent est il inferieur à larbre initial ? si oui on continue
            if (shouldContinue.test(tree, initialTreeValue)) {
                // On continue le traitement en recupérant le prochain arbre
                return isVisible(getTree.apply(tree), initialTreeValue, nextTreeIsNotNull, shouldContinue, getTree);
            } else {
                // Cet arbre cache larbre initial qui est donc non visible
                return false;
            }
        } else {
            // on est arrivé à une extremité, l'arbre est donc visible
            return true;
        }

    }



}