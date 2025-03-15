package org.poo.cb.Stocks;

import org.poo.cb.AplicatieBanking;
import org.poo.cb.Conturi.Cont;
import org.poo.cb.Conturi.ContFactory;
import org.poo.cb.Utilizator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BuyStockStrategy implements StockStrategy{
    private AplicatieBanking app = AplicatieBanking.getInstance();
    private ContFactory factory = ContFactory.getInstance();
    private Integer cantitate;
    private String email;
    private String companie;

    public BuyStockStrategy(Integer cantitate, String email, String companie) {
        this.cantitate = cantitate;
        this.email = email;
        this.companie = companie;
    }
    public Integer getCantitate() {
        return cantitate;
    }
    public String getCompanie() {
        return companie;
    }

    public void executaStrategy(String stocks) {
        String valuta = "USD";
        Double pret = 0.0;
        Double[][] stocuri = new Double[100][10];
        String[] stocuriNume = new String[100];
        Utilizator u = null;
        for (Utilizator utilizator : app.getUtilizatoriApp()) {
            if (utilizator.getEmail().equals(email)) {
                u = utilizator;
            }
        }
        creareMatriceStocuri(stocks, stocuriNume, stocuri);
        pret = calcularePret(stocuriNume, pret, stocuri);
        if (u.isPremium()) {
            pret = pret - pret * 0.05;
        }
        for (Cont cont : u.getConturi()) {
            if (cont.tip.equals(valuta)) {
                if (cont.suma < pret) {
                    System.out.println("Insufficient amount in account for buying stock");
                } else {
                    cont.scadeSuma(pret);
                    u.getStocks().add(this);
                }
            }
        }
    }

    private Double calcularePret(String[] stocuriNume, Double pret, Double[][] stocuri) {
        for (int i = 0; i < stocuriNume.length; i++) {
            if (stocuriNume[i] != null && stocuriNume[i].equals(companie)) {
                pret = Double.parseDouble(String.format("%.2f", stocuri[i][9] * cantitate));
            }
        }
        return pret;
    }

    private static void creareMatriceStocuri(String stocks, String[] stocuriNume, Double[][] stocuri) {
        int j = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(stocks))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                stocuriNume[j] = parts[0];
                for (int i = 1; i < parts.length; i++) {
                    stocuri[j][i - 1] = Double.parseDouble(parts[i]);
                }
                j++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
