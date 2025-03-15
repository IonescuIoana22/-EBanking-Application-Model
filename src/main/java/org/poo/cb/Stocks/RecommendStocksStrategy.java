package org.poo.cb.Stocks;

import org.poo.cb.AplicatieBanking;
import org.poo.cb.Conturi.ContFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RecommendStocksStrategy implements StockStrategy{
    private AplicatieBanking app = AplicatieBanking.getInstance();
    private ContFactory factory = ContFactory.getInstance();

    public RecommendStocksStrategy() {
    }

    public void executaStrategy(String stocks) {
        String[] stocuriNume = new String[100];
        Double[][] stocuri = new Double[100][10];
        ArrayList<String> recomandari = new ArrayList<String>();

        creareMatriceStocuri(stocks, stocuriNume, stocuri);
        gasireRecomandari(stocuriNume, stocuri, recomandari);
        System.out.print("{\"stocksToBuy\":[");
        for (int i = 0; i < recomandari.size(); i++) {
            if (i == recomandari.size() - 1) {
                System.out.println("\"" + recomandari.get(i) + "\"]}");
            } else {
                System.out.print("\"" + recomandari.get(i) + "\",");
            }
        }
    }

    private static void gasireRecomandari(String[] stocuriNume, Double[][] stocuri, ArrayList<String> recomandari) {
        for (int i = 0; i < stocuriNume.length; i++) {
            if (stocuriNume[i] == null) {
                break;
            }
            Double medie10 = 0.0;
            Double medie5 = 0.0;
            Double suma10 = 0.0;
            Double suma5 = 0.0;
            for (int j = 0; j < 10; j++) {
                suma10 += stocuri[i][j];
                if (j >= 5) {
                    suma5 += stocuri[i][j];
                }
            }
            medie10 = Double.parseDouble(String.format("%.2f", suma10 / 10));
            medie5 = Double.parseDouble(String.format("%.2f", suma5 / 5));
            if (medie5 > medie10) {
                 recomandari.add(stocuriNume[i]);
            }
        }
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
