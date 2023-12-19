package ru.raccoon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    String logFileName;

    private Logger(){}

    private static final class InstanceHolder {
        private static final Logger instance = new Logger();
    }

    public static Logger getInstance() {
        return InstanceHolder.instance;
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
