package com.dunzo.coffee;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String getFileAsString(String path) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            List<String> content = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.add(line);
            }
            return content.stream().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            return "";
        }
    }
}
