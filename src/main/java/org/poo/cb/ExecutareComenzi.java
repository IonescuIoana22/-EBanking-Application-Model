package org.poo.cb;

import kotlin.jvm.internal.MutablePropertyReference0Impl;
import org.poo.cb.Conturi.Cont;
import org.poo.cb.Conturi.ContFactory;
import org.poo.cb.Stocks.BuyStockStrategy;
import org.poo.cb.Stocks.RecommendStocksStrategy;
import org.poo.cb.Stocks.StockStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecutareComenzi {
    public ExecutareComenzi() {}
    private AplicatieBanking app = AplicatieBanking.getInstance();
    private ContFactory factory = ContFactory.getInstance();
    private StockStrategy strategy;
    public void createUser(String[] params) {
        String email = params[0];
        String prenume = params[1];
        String nume = params[2];
        String adresa = null;
        for (Utilizator u : app.getUtilizatoriApp()) {
            if (u.getEmail().equals(email)) {
                System.out.println("User with " + email + " already exists");
            }
        }
        for (int i = 3; i < params.length; i++) {
            if (adresa == null) {
                adresa = params[i];
            } else {
                adresa = adresa + " " + params[i];
            }
        }
        Utilizator u = new Utilizator(nume, prenume, adresa, email);
        app.getUtilizatoriApp().add(u);
    }
    public void addFriend(String[] params) {
        String email1 = params[0];
        String email2 = params[1];
        Utilizator u1 = null;
        Utilizator u2 = null;
        for (Utilizator u : app.getUtilizatoriApp()) {
            if (u.getEmail().equals(email1)) {
                u1 = u;
            }
            if (u.getEmail().equals(email2)) {
                u2 = u;
            }
        }
        if (u1 == null) {
            System.out.println("User with " + email1 + " doesn't exist");
        }
        if (u2 == null) {
            System.out.println("User with " + email2 + " doesn't exist");
        }
        if (u1 != null) {
            for (Utilizator utilizator : u1.getPrieteni()) {
                if (utilizator.getEmail().equals(email2)) {
                    System.out.println("User with " + email2 + " is already a friend");
                }
            }
        }
        if (u1 != null && u2 != null) {
            u1.getPrieteni().add(u2);
            u2.getPrieteni().add(u1);
        }
    }
    public void addAccount(String[] params) {
        String email = params[0];
        String tip = params[1];
        double suma = 0;
        Utilizator u = null;
        for (Utilizator utilizator : app.getUtilizatoriApp()) {
            if (utilizator.getEmail().equals(email)) {
                u = utilizator;
            }
        }
        for (Cont cont : u.getConturi()) {
            if (cont.tip.equals(tip)) {
                System.out.println("Account in currency " + tip + " already" + " exists for users");
            }
        }
        Cont cont = null;
        cont = creareCont(tip, cont, suma);
        u.getConturi().add(cont);
    }
    private Cont creareCont(String tip, Cont cont, double suma) {
        switch (tip) {
            case "EUR":
                cont = factory.createCont(ContFactory.ContType.EUR, tip, suma);
                break;
            case "GBP":
                cont = factory.createCont(ContFactory.ContType.GBP, tip, suma);
                break;
            case "JPY":
                cont = factory.createCont(ContFactory.ContType.JPY, tip, suma);
                break;
            case "CAD":
                cont = factory.createCont(ContFactory.ContType.CAD, tip, suma);
                break;
            case "USD":
                cont = factory.createCont(ContFactory.ContType.USD, tip, suma);
                break;
            default:
                System.out.println("Invalid account type");
                break;
        }
        return cont;
    }

    public void addMoney(String[] params) {
        String email = params[0];
        String tip = params[1];
        Double suma = Double.parseDouble(params[2]);
        Utilizator u = null;
        for (Utilizator utilizator : app.getUtilizatoriApp()) {
            if (utilizator.getEmail().equals(email)) {
                u = utilizator;
            }
        }
        for (Cont cont : u.getConturi()) {
            if (cont.tip.equals(tip)) {
                cont.adaugaSuma(suma);
            }
        }
    }
    public void exchangeMoney(String[] params, String exchange) {
        String email = params[0];
        String tipS = params[1];
        String tipD = params[2];
        String[] valuta = new String[5];
        Double[][] cursValutar = new Double[5][5];
        Double suma = Double.parseDouble(params[3]);
        Double valoare = 0.0;
        int j = 0;
        creareMatriceCursValutar(exchange, valuta, j, cursValutar);
        Utilizator u = null;
        for (Utilizator utilizator : app.getUtilizatoriApp()) {
            if (utilizator.getEmail().equals(email)) {
                u = utilizator;
            }
        }
        int pozS = 0;
        int pozD = 0;
        for (int i = 0; i < valuta.length; i++) {
            if (valuta[i].equals(tipS)) {
                pozS = i;
            }
            if (valuta[i].equals(tipD)) {
                pozD = i;
            }
        }
        valoare = suma * cursValutar[pozD][pozS];
        schimbValutar(u, tipS, suma, valoare, tipD);
    }

    private static void creareMatriceCursValutar(String exchange, String[] valuta, int j, Double[][] cursValutar) {
        try (BufferedReader br = new BufferedReader(new FileReader(exchange))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                valuta[j] = parts[0];
                for (int i = 1; i < parts.length; i++) {
                    cursValutar[j][i - 1] = Double.parseDouble(parts[i]);
                }
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void schimbValutar(Utilizator u, String tipS, Double suma, Double valoare, String tipD) {
        for (Cont cont : u.getConturi()) {
            if (cont.tip.equals(tipS)) {
                if (cont.suma < suma) {
                    System.out.println("Insufficient amount in account " + tipS + " for exchange");
                } else if (u.isPremium() == false && cont.suma * 0.5 < valoare) {
                    valoare = valoare + valoare * 0.01;
                    cont.scadeSuma(valoare);
                } else {
                    cont.scadeSuma(valoare);
                }
            }
        }
        for (Cont cont1 : u.getConturi()) {
            if (cont1.tip.equals(tipD)) {
                cont1.adaugaSuma(suma);
            }
        }
    }

    public void transferMoney(String[] params) {
        String emailS = params[0];
        String emailD = params[1];
        String tip = params[2];
        Double suma = Double.parseDouble(params[3]);
        Utilizator uS = null;
        Utilizator uD = null;
        for (Utilizator utilizator : app.getUtilizatoriApp()) {
            if (utilizator.getEmail().equals(emailS)) {
                uS = utilizator;
            }
            if (utilizator.getEmail().equals(emailD)) {
                uD = utilizator;
            }
        }
        for (Utilizator u : uS.getPrieteni()) {
            if (!u.getEmail().equals(emailD)) {
                System.out.println("You are not allowed to transfer money to " + emailD);
            }
        }
        transfer(uS, tip, suma, uD);

    }

    private static void transfer(Utilizator uS, String tip, Double suma, Utilizator uD) {
        for (Cont c : uS.getConturi()) {
            if (c.tip.equals(tip)) {
                if (c.suma < suma) {
                    System.out.println("Insufficient amount in account " + tip + " for transfer");
                } else {
                    c.scadeSuma(suma);
                    for (Cont cont : uD.getConturi()) {
                        if (cont.tip.equals(tip)) {
                            cont.adaugaSuma(suma);
                        }
                    }
                }
            }
        }
    }

    public void buyStocks(String[] params, String stock) {
        String email = params[0];
        String companie = params[1];
        Integer cantitate = Integer.parseInt(params[2]);
        strategy = new BuyStockStrategy(cantitate, email, companie);
        strategy.executaStrategy(stock);
    }
    public void recommendStocks(String stock) {
        strategy = new RecommendStocksStrategy();
        strategy.executaStrategy(stock);
    }
    public void listUser(String[] params) {
        String email = params[0];
        Utilizator u = null;
        for (Utilizator utilizator : app.getUtilizatoriApp()) {
            if (utilizator.getEmail().equals(email)) {
                u = utilizator;
            }
        }
        if (u == null) {
            System.out.println("User with " + email + " doesn't exist");
        } else {
            System.out.print("{\"email\":\"" + u.getEmail() + "\",\"firstname\":\"" + u.getPrenume() + "\",\"lastname\":\"" + u.getNume() + "\",\"address\":\"" + u.getAdresa() + "\",\"friends\":[");
            if (u.getPrieteni().size() == 0) {
                System.out.println("]}");
            }
            for (int i = 0; i < u.getPrieteni().size(); i++) {
                if (i == u.getPrieteni().size() - 1) {
                    System.out.println("\"" + u.getPrieteni().get(i).getEmail() + "\"]}");
                } else {
                    System.out.print("\"" + u.getPrieteni().get(i).getEmail() + "\",\"");
                }
            }
        }
    }
    public void listPortfolio(String[] params, String stock) {
        String email = params[0];
        Utilizator utilizator = null;
        for (Utilizator u : app.getUtilizatoriApp()) {
            if (u.getEmail().equals(email)) {
                utilizator = u;
            }
        }
        System.out.print("{\"stocks\":[");
        for (BuyStockStrategy stock1 : utilizator.getStocks()) {
            System.out.print("{\"stockName\":\"" + stock1.getCompanie() + "\",\"amount\":" + stock1.getCantitate() + "}");
            if (utilizator.getStocks().indexOf(stock1) != utilizator.getStocks().size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print("],\"accounts\":[");
        for (Cont c : utilizator.getConturi()) {
            System.out.print("{\"currencyName\":\"" + c.tip + "\",\"amount\":\"" + String.format("%.2f", c.suma) + "\"}");
            if (utilizator.getConturi().indexOf(c) != utilizator.getConturi().size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]}");
    }
    public void buyPremium(String[] params) {
        String email = params[0];
        Utilizator u = null;
        for (Utilizator utilizator : app.getUtilizatoriApp()) {
            if (utilizator.getEmail().equals(email)) {
                u = utilizator;
            }
        }
        if (u == null) {
            System.out.println("User with " + email + " doesn't exist");
        }
        for (Cont c : u.getConturi()) {
            if (c.tip.equals("USD")) {
                if (c.suma < 100) {
                    System.out.println("Insufficient amount in account for buying premium option");
                } else {
                    c.scadeSuma(100);
                    u.setPremium(true);
                }
            }
        }
    }
}
