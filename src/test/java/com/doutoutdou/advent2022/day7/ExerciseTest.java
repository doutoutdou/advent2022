package com.doutoutdou.advent2022.day7;

import com.doutoutdou.advent2022.utils.ExerciseUtils;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class ExerciseTest {
    @Test
    void exo1() {

        long startTime = System.nanoTime();
        int limit = 100000;
        FileSystem fileSystem = buildFileSystem();

        // Il faut maintenant trouver les dossiers dont la taille ne dépasse pas 100000
        // On pourrait retourner la liste à la création mais ca serait un peu triché (déjà que l'on set la taille du parent dynamiquement)
        // on fait donc un stream récursif

        var result = fileSystem.getRootDirectory().flattened().map(Directory::getTotalSize).filter(totalSize -> totalSize <= limit).mapToInt(value -> value).sum();
        log.info(result);

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");
    }

    @Test
    void exo2() {
        long startTime = System.nanoTime();

        FileSystem fileSystem = buildFileSystem();

        var spaceNeeded = 30000000;
        // On calcule la taille libre actuellement, cad la taille totale - celle occupee
        var freeSize = 70000000 - fileSystem.getRootDirectory().getTotalSize();

        Optional<Integer> directorySize = fileSystem.getRootDirectory().flattened().map(Directory::getTotalSize).filter(totalSize -> totalSize + freeSize >= spaceNeeded).sorted().findFirst();
        directorySize.ifPresentOrElse(log::info, () -> log.info("no directory found"));

        log.info("Duration " + (System.nanoTime() - startTime) + "ns");

    }

    private static FileSystem buildFileSystem() {
        var data = ExerciseUtils.loadFromFileAsStringList("7");
        assert data != null;
        var fileSystem = new FileSystem();

        for (String line : data) {

            // dans le cas d'une commande de type cd
            if ("$".equals(line.substring(0, 1))) {
                if ("cd".equals(line.substring(2, 4))) {
                    fileSystem.moveIntoDirectory(line.substring(5));
                }
            } else {
                // sinon si on liste un fichier
                if (!"dir".equals(line.substring(0, 3))) {
                    String[] values = line.split(" ");
                    fileSystem.addFile(new File(values[1],
                            Integer.valueOf(values[0])));
                }
            }
        }
        return fileSystem;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    private static class FileSystem {
        private Directory currentDirectory;
        private Directory rootDirectory;

        /**
         * Ajoute un repertoire au répertoire courant
         *
         * @param directory
         */
        private void addDirectoryToCurrent(Directory directory) {
            if (currentDirectory.getDirectories() != null) {
                currentDirectory.getDirectories().add(directory);
            } else {
                currentDirectory.setDirectories(Stream.of(directory).collect(Collectors.toList()));
            }
        }

        /**
         * Se déplace dans le repertoire, le crée au préalable si non existant
         *
         * @param directoryName le nom du repertoire
         */
        private void moveIntoDirectory(String directoryName) {
            if ("..".equals(directoryName)) {
                // on remonte d'un niveau
                // On recalcule donc le path actuel et le repertoire courant
                currentDirectory = currentDirectory.parent;
            }
            // Très moche ce if qui ne sert qu'une fois
            else if ("/".equals(directoryName)) {
                rootDirectory = Directory.builder()
                        .name(directoryName)
                        .directories(new ArrayList<>())
                        .build();
                currentDirectory = rootDirectory;
            } else {
                // On gère le fait de ne pas recreer un repertoire déjà existant
                Optional<Directory> directoryAlreadyCreated = currentDirectory.directories.stream().filter(directory -> directoryName.equals(directory.name)).findFirst();
                // Si le repertoire existe déjà alors on le set juste comme repertoire courant
                // Sinon on le crée et on le set comme repertoire courant
                directoryAlreadyCreated.ifPresentOrElse(directory -> currentDirectory = directory, () -> {
                    var newDirectory = Directory.builder()
                            .name(directoryName)
                            .parent(currentDirectory)
                            .directories(new ArrayList<>())
                            .build();
                    addDirectoryToCurrent(newDirectory);
                    currentDirectory = newDirectory;
                });

            }
        }

        private void addFile(File file) {
            currentDirectory.addFile(file);
            currentDirectory.updateSizeOfParentDirectory(file.getSize());
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    private static class Directory {
        private String name;
        private List<File> files;
        private List<Directory> directories;
        private Directory parent;
        private int totalSize;

        public void addFile(File file) {
            if (files != null) {
                files.add(file);
            } else {
                setFiles(Stream.of(file).collect(Collectors.toList()));
            }
            // On augmente la taille totale du dossier courant
            totalSize += file.getSize();
        }

        /**
         * Met à jour la taille des repertoires parents
         *
         * @param fileSize la taille du fichier
         */
        public void updateSizeOfParentDirectory(int fileSize) {
            if (parent != null) {
                parent.updateSize(fileSize);
                parent.updateSizeOfParentDirectory(fileSize);
            }
        }

        public void updateSize(int size) {
            totalSize += size;
        }

        // Méthode totalement pompée sur le net quand je cherchai comment faire un stream récursif
        public Stream<Directory> flattened() {
            return Stream.concat(
                    Stream.of(this),
                    directories.stream().flatMap(Directory::flattened));
        }
    }

    @AllArgsConstructor
    @Getter
    private static class File {
        private String name;
        private Integer size;
    }
}
