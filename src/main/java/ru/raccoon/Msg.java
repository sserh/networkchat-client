package ru.raccoon;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Msg {

    private static final String MSG_CLIENT_NAME = "client_name";
    private static final String MSG_TIME = "msg_time";
    private static final String MSG_TEXT = "msg";

    public static String packMsg(String msg) {
        JSONObject jsonObject = new JSONObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        jsonObject.put(MSG_CLIENT_NAME, Main.getMyName());
        jsonObject.put(MSG_TIME, LocalDateTime.now().format(formatter));
        jsonObject.put(MSG_TEXT, msg);

        return jsonObject.toJSONString();
    }

    public static String unpackMsg(String msg) {
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            obj = parser.parse(msg);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject.get(MSG_CLIENT_NAME) + "\n" + jsonObject.get(MSG_TIME) + "\n" + jsonObject.get(MSG_TEXT) + "\n";
    }

    //метод для ловли /exit
    public static String getOnlyText(String msg) {
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            obj = parser.parse(msg);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject.get(MSG_TEXT).toString();
    }
}
