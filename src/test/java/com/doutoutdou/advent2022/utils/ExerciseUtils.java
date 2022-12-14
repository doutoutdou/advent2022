 package com.doutoutdou.advent2022.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@UtilityClass
public class ExerciseUtils {

    public static String loadFromFile(String day) {
        ClassLoader classLoader = ExerciseUtils.class.getClassLoader();
        var file = new File(classLoader.getResource("exercise" + day).getFile());
        try {
            return FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> loadFromFileAsStringList(String day) {
        ClassLoader classLoader = ExerciseUtils.class.getClassLoader();
        var file = new File(classLoader.getResource("exercise" + day).getFile());
        try {
            return FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
