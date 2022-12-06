package com.doutoutdou.advent2022.day5;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
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


    void run(BiConsumer<List<Integer>, List<LinkedList<String>>> move) {
        var data = ExerciseUtils.loadFromFileAsStringList("5");
        long startTime = System.nanoTime();

        assert data != null;

        int stackLineIndex = getStackLineIndex(data);

        List<LinkedList<String>> puzzle = createPuzzleInput(data, stackLineIndex);

        List<List<Integer>> movements = createMovementsList(data, stackLineIndex);

        // On applique chaque mouvement
        for (List<Integer> movement : movements) {
            move.accept(movement, puzzle);
        }

        String result = puzzle.stream().filter(strings -> strings.size() != 0).map(LinkedList::getLast).collect(Collectors.joining());

        log.info(result);
        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

    }
    /**
     * Permet de récupérer l'index de la ligne contenant le nombre de stacks
     *
     * @param data le fichier d'entree
     * @return l'index de la ligne indiquant le nombre de stack
     */
    protected int getStackLineIndex(List<String> data) {
        int crateNumberIndex = 0;
        Pattern pattern = Pattern.compile("^\\s\\d", Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        // on cherche la ligne contenant le nombre de stack
        for (String line : data) {
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                break;
            }
            crateNumberIndex++;
        }
        return crateNumberIndex;
    }


    /**
     * Creer le jdd d'entrée sous forme d'une liste de linkedlist
     * @param data la liste des lignes contenant les crates et leurs positionnements
     * @param stackLineIndex l'index de la ligne contenant le nombre de stack
     * @return
     */
    protected List<LinkedList<String>> createPuzzleInput(List<String> data, int stackLineIndex) {
        // On cherche combien de stack sont presentes dans le puzzle
        // On compte le nombre despace par groupe de 3
        // si 4 espaces (1 * 3) plus celui du début, alors il y a 2 stacks
        // si 7 espaces (2 * 3) plus celui du debut, alors il y a 3 stacks
        // etc ...
        var numberOfStacks = (data.get(stackLineIndex).lastIndexOf(" ") / 3) + 1;

        // on init une liste avec le bon nombre de linkedlist
        // Lidee avec la linkedlist etait de pouvoir manipuler plus facilement mais en fait pas utile pour l'exercice
        List<LinkedList<String>> mapping = new ArrayList<>();
        for (int i = 0; i < numberOfStacks; i++) {
            mapping.add(new LinkedList<>());
        }

        // on opere maintenant sur les lignes contenant les crates
        // le but est de creer letat initial
        List<String> crates = data.subList(0, stackLineIndex);
        for (String line : crates) {
            // pour chaque ligne il faut trouver le nombre de caisse
            var numberOfCrates = line.chars().filter(ch -> ch == '[').count();
            // maintenant il faut trouver lemplacement des caisses
            for (int i = 0; i < numberOfCrates; i++) {
                // on recupere lindex du premier [ trouve
                int index = line.indexOf("[");
                // on calcule lindex pour savoir dans quelle liste ajouter lelement
                var elementIndex = index / 4;

                mapping.get(elementIndex).addFirst(line.substring(index + 1, index + 2));
                // on remplace le caractere pour la prochaine iteration
                line = line.replaceFirst("\\[", "r");
            }
        }
        return mapping;
    }

    /**
     * Cree une liste de liste contenant tous les mouvements à appliquer
     * @param data la liste des lignes contenant les mouvements
     * @param stackLineIndex l'index de la ligne contenant le nombre de stack
     * @return
     */
    protected List<List<Integer>> createMovementsList(List<String> data, int stackLineIndex) {
        // FIXME : Il serait bien mieux de faire un FOR et de faire directement les modifications plutot que dallouer des listes
        // Pour ensuite itérer sur ces mêmes listes ...
        // pas opti du tout, autant faire un for et faire les modifs directement dedans
        return data.subList(stackLineIndex + 2, data.size()).stream().map(s -> {
            // on pourrait le faire de maniere arbitraire car le format est tjrs le meme mais au moins la ca devrait etre future proof
            var moveIndex = s.indexOf("move");
            var formIndex = s.indexOf("from");
            var toIndex = s.indexOf("to");
            // +4 pour la longueur de move + lespace
            var moveValue = Integer.valueOf(s.substring(moveIndex + 5, formIndex - 1));
            var fromValue = Integer.valueOf(s.substring(formIndex + 5, toIndex - 1));
            var toValue = Integer.valueOf(s.substring(toIndex + 3));
            return new ArrayList<Integer>() {
                {
                    add(moveValue);
                    add(fromValue);
                    add(toValue);

                }
            };

        }).collect(Collectors.toList());
    }


}
