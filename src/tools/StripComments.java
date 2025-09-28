package tools;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class StripComments {
    public static void main(String[] args) throws Exception {
        Path root = Paths.get("src");
        List<Path> javaFiles = new ArrayList<>();
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")) {
                    javaFiles.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        int changed = 0;
        for (Path file : javaFiles) {
            String original = Files.readString(file, StandardCharsets.UTF_8);
            String stripped = stripComments(original);
            if (!original.equals(stripped)) {
                Files.writeString(file, stripped, StandardCharsets.UTF_8);
                changed++;
            }
        }
        System.out.println("Arquivos processados: " + javaFiles.size() + ", alterados: " + changed);
    }

    private static String stripComments(String src) {
        StringBuilder out = new StringBuilder(src.length());
        boolean inString = false;
        boolean inChar = false;
        boolean inLineComment = false;
        boolean inBlockComment = false;
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            char next = i + 1 < src.length() ? src.charAt(i + 1) : '\0';

            if (inLineComment) {
                if (c == '\n') {
                    inLineComment = false;
                    out.append(c);
                }
                continue;
            }

            if (inBlockComment) {
                if (c == '*' && next == '/') {
                    inBlockComment = false;
                    i++; 
                }
                continue;
            }

            if (inString) {
                out.append(c);
                if (c == '\\' && next != '\0') { 
                    out.append(next);
                    i++;
                    continue;
                }
                if (c == '"') inString = false;
                continue;
            }

            if (inChar) {
                out.append(c);
                if (c == '\\' && next != '\0') {
                    out.append(next);
                    i++;
                    continue;
                }
                if (c == '\'') inChar = false;
                continue;
            }

            
            if (c == '"') {
                inString = true;
                out.append(c);
                continue;
            }
            if (c == '\'') {
                inChar = true;
                out.append(c);
                continue;
            }

            
            if (c == '/' && next == '/') {
                inLineComment = true;
                i++; 
                continue;
            }
            if (c == '/' && next == '*') {
                inBlockComment = true;
                i++; 
                continue;
            }

            out.append(c);
        }
        return out.toString();
    }
}
