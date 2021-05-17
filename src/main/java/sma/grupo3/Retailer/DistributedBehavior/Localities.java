package sma.grupo3.Retailer.DistributedBehavior;

public enum Localities {
    CHAPINERO("CHAPINERO"),
    BARRIOSUNIDOS("BARRIOSUNIDOS"),
    TEUSAQUILLO("TEUSAQUILLO"),
    USAQUEN("USAQUEN"),
    SUBA("SUBA"),
    ENGATIVA("ENGATIVA");

    public final String value;

    private Localities(String value){
        this.value = value;
    }
}
