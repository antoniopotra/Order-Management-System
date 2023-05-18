package org.example;

import org.example.presentation.*;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        ClientView clientView = new ClientView();
        ProductView productView = new ProductView();
        OrderView orderView = new OrderView();

        Controller controller = new Controller(mainView, clientView, productView, orderView);
        controller.init();
    }
}