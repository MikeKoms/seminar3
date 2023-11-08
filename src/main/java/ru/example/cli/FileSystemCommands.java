package ru.example.cli;

import java.util.List;

public interface FileSystemCommands {

    List<String> ls();
    String pwd();
    void cp(String source, String destination);
    void mv(String source, String destination);
    void mkdir(String name);
    void rm(String name);
    List<String> grep(String pattern);

}
