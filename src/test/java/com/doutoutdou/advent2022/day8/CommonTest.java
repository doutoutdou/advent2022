package com.doutoutdou.advent2022.day8;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
public abstract class CommonTest {


    /**
     * Créer un tableau à 2 dimensions d'arbres suivant le fichier d'entrée
     * @return
     */
    static Tree[][] buildForrest() {
        var data = ExerciseUtils.loadFromFileAsStringList("8");
        assert data != null;
        var rowsNumber = data.size();
        var columnsNumber = data.get(0).length();
        Tree[][] forrest = new Tree[rowsNumber][columnsNumber];
        int row = 0;

        for (String line : data) {

            int column = 0;
            String[] values = line.split("");

            for (String value : values) {
                Tree treeToAdd = createTree(forrest, row, column, Integer.parseInt(value));
                forrest[row][column] = treeToAdd;
                updateForrest(forrest, row, column, treeToAdd);
                column++;
            }
            row++;
        }
        return forrest;
    }

    /**
     * Crée un arbre
     * @param forrest le tableau à 2 dimensions contenant les arbres déjà traités
     * @param row l'indice de la ligne en cours de traitement
     * @param column l'indice de la colonne en cours de traitement
     * @param value la valeur à associer à l'arbre
     * @return un arbre
     */
    private static Tree createTree(Tree[][] forrest, int row, int column, int value) {
        // On récupère les 4 arbres (au maximum) adjacents à l'arbre ajouté
        Tree treeToAdd = Tree.builder()
                .value(value).build();

        // si extermite alors on l'indique pour ne pas traiter ces arbres plus tard
        if (row == 0 || column == 0 || row + 1 == forrest.length || column + 1 == forrest[0].length) {
            treeToAdd.setExtremity(true);
        }

        if (row > 0) {
            treeToAdd.setTop(forrest[row - 1][column]);
        }

        // Arbre en bas
        if (row + 1 < forrest.length) {
            treeToAdd.setBottom(forrest[row + 1][column]);
        }

        // arbre à gauche
        if (column > 0) {
            treeToAdd.setLeft(forrest[row][column - 1]);
        }

        // arbre à droite
        if (column + 1 < forrest[0].length) {
            treeToAdd.setRight(forrest[row][column + 1]);
        }

        return treeToAdd;

    }


    /**
     * Il s'agit de mettre à jour la forêt avec le nouvel arbre crée
     * @param forrest le tableau à 2 dimensions contenant les arbres
     * @param row l'indice de la ligne en cours de traitement
     * @param column l'indice de la colonne en cours de traitement
     * @param treeAdded la valeur à associer à l'arbre
     */
    private static void updateForrest(Tree[][] forrest, int row, int column, Tree treeAdded) {
        // S'il existe un arbre au dessus, alors il faut le mettre à jour avec l'arbre crée
        if (treeAdded.getTop() != null) {
            Tree topTree = forrest[row - 1][column];
            topTree.setBottom(treeAdded);
            forrest[row - 1][column] = topTree;
        }
        if (treeAdded.getBottom() != null) {
            Tree bottomTree = forrest[row + 1][column];
            bottomTree.setTop(treeAdded);
            forrest[row + 1][column] = bottomTree;
        }
        if (treeAdded.getLeft() != null) {
            Tree leftTree = forrest[row][column - 1];
            leftTree.setRight(treeAdded);
            forrest[row][column - 1] = leftTree;
        }
        if (treeAdded.getRight() != null) {
            Tree rightTree = forrest[row - 1][column];
            rightTree.setLeft(treeAdded);
            forrest[row][column + 1] = rightTree;
        }

    }

}
