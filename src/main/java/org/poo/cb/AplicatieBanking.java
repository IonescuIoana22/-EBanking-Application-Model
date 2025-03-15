package org.poo.cb;

import org.poo.cb.Conturi.ContFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AplicatieBanking {
    private static AplicatieBanking app;
    private static ContFactory fabrica = ContFactory.getInstance();
    public ArrayList<Utilizator> utilizatoriApp = new ArrayList<Utilizator>();     //utilizatorii aplicatiei
    private AplicatieBanking() {
    }
    public ArrayList<Utilizator> getUtilizatoriApp() {
        return utilizatoriApp;
    }

    public static AplicatieBanking getInstance() {
        if (app == null) {
            app = new AplicatieBanking();
        }
        return app;
    }

    public void run(String[] args) {
        String exchange = args[0];
        String stock = args[1];
        String commands = args[2];
        APIBanca api = new APIBanca();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + commands))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    String[] parts = line.split(" ");
                    String command = parts[0] + " " + parts[1];
                    String[] params = new String[parts.length - 2];
                    for (int i = 2; i < parts.length; i++) {
                        params[i - 2] = parts[i];
                    }
                    api.executeCommand(command, params, exchange, stock);
                }
            }
            utilizatoriApp.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
