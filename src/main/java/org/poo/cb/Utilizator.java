package org.poo.cb;

import org.poo.cb.Conturi.Cont;
import org.poo.cb.Stocks.BuyStockStrategy;

import java.util.ArrayList;

public class Utilizator {
    private String nume, prenume, adresa, email;
    private boolean isPremium = false;
    private ArrayList<Utilizator> prieteni = new ArrayList<Utilizator>();
    private ArrayList<Cont> conturi = new ArrayList<Cont>();
    private ArrayList<BuyStockStrategy> stocks = new ArrayList<BuyStockStrategy>();

    public Utilizator(String nume, String prenume, String adresa, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.email = email;
    }

    public String getNume() {
        return nume;
    }
    public String getPrenume() {
        return prenume;
    }
    public String getAdresa() {
        return adresa;
    }
    public String getEmail() {
        return email;
    }
    public ArrayList<Utilizator> getPrieteni() {
        return prieteni;
    }
    public ArrayList<Cont> getConturi() {
        return conturi;
    }
    public ArrayList<BuyStockStrategy> getStocks() {
        return stocks;
    }
    public boolean isPremium() {
        return isPremium;
    }
    public void setPremium(boolean premium) {
        isPremium = premium;
    }
}