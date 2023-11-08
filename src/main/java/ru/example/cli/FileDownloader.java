package ru.example.cli;

public interface FileDownloader {
    String url = "https://raw.githubusercontent.com/apache/kafka/trunk/NOTICE";

    void downloadIOSimple(String url);

    void downloadIOBuffered(String url);

    void downloadNIO(String url);

    void resumableDownload(String url);
}
