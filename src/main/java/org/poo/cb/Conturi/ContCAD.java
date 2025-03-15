package org.poo.cb.Conturi;

public class ContCAD extends Cont {
    public ContCAD(String tip, double suma) {
        this.tip = "CAD";
        this.suma = 0;
    }
    public void adaugaSuma(double suma) {
        this.suma += suma;
    }
    public void scadeSuma(double suma) {
        this.suma -= suma;
    }
}
