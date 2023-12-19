package ru.raccoon;

public class Main {

    protected static String myName;
    public static void main(String[] args) {

        Client client = new Client();
        client.start();
    }

    public static String getMyName() {
        return myName;
    }
}