package org.poo.cb.Conturi;

public class ContGBP extends Cont {
    public ContGBP(String tip, double suma) {
        this.tip = "GBP";
        this.suma = 0;
    }
    public void adaugaSuma(double suma) {
        this.suma += suma;
    }
    public void scadeSuma(double suma) {
        this.suma -= suma;
    }
}
