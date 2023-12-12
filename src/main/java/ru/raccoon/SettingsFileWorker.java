package ru.raccoon;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class SettingsFileWorker {

    private final String SETTINGS_FILE_NAME = "settings.json";
    private final String SERVER_IP_PARAM_NAME = "server_ip";
    private final String SERVER_PORT_PARAM_NAME = "server_port";
    private final String CLIENT_NAME_PARAM_NAME = "client_name";

    public boolean isSettingsFileExists() {
        File settingsFile = new File(SETTINGS_FILE_NAME);
        return settingsFile.exists();
    }

    public void createSettingsFile() {
        File settingsFile = new File(SETTINGS_FILE_NAME);
        try {
            //создаём файл настроек
            settingsFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setParamValue(Parameter parameter, String value) {
        JSONParser parser = new JSONParser();
        Object obj = null;
        try (FileReader fileReader = new FileReader(SETTINGS_FILE_NAME)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String readedLine;
            while ((readedLine = bufferedReader.readLine()) != null) {
                obj = parser.parse(readedLine);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject;
        if (obj == null) {
            jsonObject = new JSONObject();
        } else {
            jsonObject = (JSONObject) obj;
        }
        switch (parameter) {
            case SERVER_IP -> jsonObject.put(SERVER_IP_PARAM_NAME, value);
            case SERVER_PORT -> jsonObject.put(SERVER_PORT_PARAM_NAME, value);
            case CLIENT_NAME -> jsonObject.put(CLIENT_NAME_PARAM_NAME, value);
        }
        try (FileWriter fileWriter = new FileWriter(SETTINGS_FILE_NAME)) {
            fileWriter.write(jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getParamValue(Parameter parameter) {
        JSONParser parser = new JSONParser();
        Object obj = null;

        try (FileReader fileReader = new FileReader(SETTINGS_FILE_NAME)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String readedLine;
            while ((readedLine = bufferedReader.readLine()) != null) {
                obj = parser.parse(readedLine);
            }
        } catch (ParseException | IOException e) {
            return "-1"; //если файла нет или не парсится, то возвращаем -1
        }
        JSONObject jsonObject = (JSONObject) obj;
        String str = "";
        assert jsonObject != null;
        switch (parameter) {
            case SERVER_IP -> str = jsonObject.get(SERVER_IP_PARAM_NAME).toString();
            case SERVER_PORT -> str = jsonObject.get(SERVER_PORT_PARAM_NAME).toString();
            case CLIENT_NAME -> str = jsonObject.get(CLIENT_NAME_PARAM_NAME).toString();
        }
        //если всё хорошо, возвращаем значение параметра
        return str;
    }
}
