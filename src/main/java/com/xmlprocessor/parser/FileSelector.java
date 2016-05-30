package com.xmlprocessor.parser;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class FileSelector {

    public static List<Path> getFiles(String baseDir) throws IOException {
        return Files.find(Paths.get(baseDir), 1000, new FileBiPredicate(), FileVisitOption.FOLLOW_LINKS).collect(Collectors.toList());
    }

    static class FileBiPredicate implements BiPredicate<Path, BasicFileAttributes> {
        @Override
        public boolean test(Path path, BasicFileAttributes basicFileAttributes) {
            if (path.getFileName().toFile().getAbsolutePath().endsWith(".xml")) {
                return true;
            }
            return false;
        }
    }

}
