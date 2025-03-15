package org.poo.cb.Conturi;

public class ContEUR extends Cont {
    public ContEUR(String tip, double suma) {
        this.tip = "EUR";
        this.suma = 0;
    }
    public void adaugaSuma(double suma) {
        this.suma += suma;
    }
    public void scadeSuma(double suma) {
        this.suma -= suma;
    }
}
