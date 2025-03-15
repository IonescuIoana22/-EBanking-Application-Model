package org.poo.cb.Conturi;

public class ContJPY extends Cont {
    public ContJPY(String tip, double suma) {
        this.tip = "JPY";
        this.suma = 0;
    }
    public void adaugaSuma(double suma) {
        this.suma += suma;
    }
    public void scadeSuma(double suma) {
        this.suma -= suma;
    }
}
