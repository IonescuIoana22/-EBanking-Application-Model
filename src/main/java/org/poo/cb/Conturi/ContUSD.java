package org.poo.cb.Conturi;

public class ContUSD extends Cont {
    public ContUSD(String tip, double suma) {
        this.tip = "USD";
        this.suma = 0;
    }
    public void adaugaSuma(double suma) {
        this.suma += suma;
    }
    public void scadeSuma(double suma) {
        this.suma -= suma;
    }
}
