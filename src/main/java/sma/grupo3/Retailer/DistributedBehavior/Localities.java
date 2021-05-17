package sma.grupo3.Retailer.DistributedBehavior;

public enum Localities {
    CHAPINERO("CHAPINERO", true, 15),
    BARRIOSUNIDOS("BARRIOSUNIDOS", false, 0),
    TEUSAQUILLO("TEUSAQUILLO", true, 4),
    USAQUEN("USAQUEN", true, 6),
    SUBA("SUBA", false, 10),
    ENGATIVA("ENGATIVA", true, 5);

    public final String value;
    public final boolean isThereWarehouse;
    public final int fleetSize;

    Localities(String value, boolean isThereWarehouse, int fleetSize) {
        this.value = value;
        this.isThereWarehouse = isThereWarehouse;
        this.fleetSize = fleetSize;
    }
}
