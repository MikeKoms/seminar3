package ru.example.downloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Downloader {

    public static void main(String[] args) throws IOException {
        String url = "https://raw.githubusercontent.com/apache/kafka/trunk/NOTICE";
//        downloadIOSimple(url);
//        downloadIOBuffered(url);
//        downloadNIO(url);
        resumableDownload(url);
    }

    public static void downloadIOSimple(String url) throws IOException {
        URL fileUrl = new URL(url);
        InputStream inputStream = fileUrl.openStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        try (FileOutputStream fileOutputStream = new FileOutputStream("fileName.txt")) {
           fileOutputStream.write(bufferedInputStream.readAllBytes());
        }
    }

    public static void downloadIOBuffered(String url) throws IOException {
        URL fileUrl = new URL(url);
        InputStream inputStream = fileUrl.openStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        try (FileOutputStream fileOutputStream = new FileOutputStream("fileName.txt")) {
            byte[] input;
            do {
                input = bufferedInputStream.readNBytes(512);
                fileOutputStream.write(input);
                System.out.println("-- check progress --");
            }
            while (input.length != 0);

        }
    }

    public static void downloadNIO(String url) throws IOException {
        URL fileUrl = new URL(url);
        Files.copy(fileUrl.openStream(), Path.of("fileName.txt"), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void resumableDownload(String url) throws IOException {
        // открыли соединения
        URL fileUrl = new URL(url);
        File outputFile = new File("fileName.txt");

        // допустим, это была первая попытка какая-нибудь
        HttpURLConnection httpConnection = (HttpURLConnection) fileUrl.openConnection();
        httpConnection.setRequestMethod("HEAD");
        long remoteFileSize = httpConnection.getContentLengthLong();
        httpConnection.disconnect();

        // открыли соединение заново и начали читать с нужных байтов
        httpConnection = (HttpURLConnection) fileUrl.openConnection();
        long existingFileSize = outputFile.length();
        if (existingFileSize < remoteFileSize) {
            httpConnection.addRequestProperty(
                    "Range",
                    "bytes=" + existingFileSize + "-" + remoteFileSize
            );
        }

        // записали батчами
        InputStream inputStream = httpConnection.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        try (OutputStream outputFileStream = new FileOutputStream(outputFile, true);
        ) {
            byte[] input;
            do {
                input = bufferedInputStream.readNBytes(128);
                outputFileStream.write(input);
                System.out.println("-- check progress --");
            }
            while (input.length != 0);

        }
    }
}
