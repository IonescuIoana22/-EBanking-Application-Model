package org.poo.cb;

public class Main {
    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Running Main");
        } else {
            AplicatieBanking app = AplicatieBanking.getInstance();
            app.run(args);
        }
    }
}