package ru.example.cli.implemented;

import ru.example.cli.FileSystemCommands;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

public class NIOFileCommandsService implements FileSystemCommands {

    @Override
    public List<String> ls() throws IOException {
        Stream<Path> list = Files.list(Paths.get(pwd()));

        return list
                .filter(file -> !Files.isDirectory(file))
                .map(file -> file.getFileName().toString())
                .toList();
    }

    @Override
    public String pwd() {
        return FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString();
    }

    @Override
    public void cp(String source, String destination) throws IOException {
        Files.copy(Paths.get(source), Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void mv(String source, String destination) {
        Path fileToMovePath = Paths.get(source);
        Path targetPath = Paths.get(destination);
        try {
            Files.move(fileToMovePath, targetPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void mkdir(String name) {

    }

    @Override
    public void rm(String name) {

    }

    @Override
    public List<String> grep(String pattern) {
        return null;
    }
}
