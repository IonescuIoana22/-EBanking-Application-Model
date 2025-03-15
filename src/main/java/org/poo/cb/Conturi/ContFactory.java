package org.poo.cb.Conturi;

public class ContFactory {
    public enum ContType {
        EUR,GBP,JPY,CAD,USD
    }

    private static ContFactory factory;

    private ContFactory() {}

    public static ContFactory getInstance() {
        if (factory == null) {
            factory = new ContFactory();
        }
        return factory;
    }

    public Cont createCont(ContType contType, String tip, double suma) {
        switch (contType) {
            case EUR:
                return new ContEUR(tip, suma);
            case GBP:
                return new ContGBP(tip, suma);
            case JPY:
                return new ContJPY(tip, suma);
            case CAD:
                return new ContCAD(tip, suma);
            case USD:
                return new ContUSD(tip, suma);
            default:
                return null;
        }
    }
}
