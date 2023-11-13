package ru.example.cli.implemented;

import ru.example.cli.FileSystemCommands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;

public class IOFileCommandsService implements FileSystemCommands {
    @Override
    public List<String> ls() throws IOException {
        return null;
    }

    @Override
    public String pwd() {
        return new File("").getAbsolutePath();
    }

    @Override
    public void cp(String source, String destination) {
        File src = new File(source);
        File dst = new File(destination);
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream(src));
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(dst))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void mv(String source, String destination) {
        File fileToMove = new File(source);
        boolean isMoved = fileToMove.renameTo(new File(destination));
        if (!isMoved) {
            throw new IllegalStateException(destination);
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
