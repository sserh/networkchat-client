package ru.raccoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread{

    public void run() {
        Scanner scanner = new Scanner(System.in);

        final Logger logger = Logger.getInstance();

        if (!logger.isLogFileExists()) {
            logger.createLogFile("client.log");
        }

        SettingsFileWorker settingsFileWorker = new SettingsFileWorker();
        if (!settingsFileWorker.isSettingsFileExists()) {
            settingsFileWorker.createSettingsFile();
            System.out.println("Укажите ip-адрес сервера: ");
            settingsFileWorker.setParamValue(Parameter.SERVER_IP, scanner.nextLine());
            System.out.println("Укажите порт сервера: ");
            settingsFileWorker.setParamValue(Parameter.SERVER_PORT, scanner.nextLine());
            System.out.println("Укажите своё имя: ");
            settingsFileWorker.setParamValue(Parameter.CLIENT_NAME, scanner.nextLine());
        }
        Main.myName = settingsFileWorker.getParamValue(Parameter.CLIENT_NAME);

        String serverIP = settingsFileWorker.getParamValue(Parameter.SERVER_IP);
        int port = Integer.parseInt(settingsFileWorker.getParamValue(Parameter.SERVER_PORT));
        try (
                Socket clientSocket = new Socket(serverIP, port);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            logger.log("Подключились к серверу");
            new Thread(() -> {
                while (true) {
                    String outString = scanner.nextLine();
                    System.out.println();
                    out.println(Msg.packMsg(outString));
                }
            }).start();

            while (true) {
                if (in.ready()) {
                    String resp = Msg.unpackMsg(in.readLine());
                    System.out.println(resp);
                    logger.log(resp);
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
