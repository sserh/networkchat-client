package ru.raccoon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    String logFileName;
    private static Logger instance;

    private Logger(){}

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }

            }
        }
        return instance;
    }

    public boolean isLogFileExists() {
        if (logFileName == null) {
            return false;
        } else {
            File settingsFile = new File(logFileName);
            return settingsFile.exists();
        }
    }

    public void createLogFile(String logFileName) {
        File settingsFile = new File(logFileName);
        try {
            //создаём файл настроек
            settingsFile.createNewFile();
            this.logFileName = logFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void log(String data) {
        File file = new File(logFileName);
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS");
            fileWriter.write("[" + LocalDateTime.now().format(formatter) + "] " + data + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
