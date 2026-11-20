package ua.edu.nuos.lab6_restapi.client;

import ua.edu.nuos.lab6_restapi.client.controller.ConsoleController;

public class Main {

    public static void main(String[] args) {
        ConsoleController cc = new ConsoleController();
        cc.menu();
    }
}
