package sma.grupo3.Retailer.DistributedBehavior;

public enum Localities {
    CHAPINERO("CHAPINERO", true, 15, true),
    BARRIOSUNIDOS("BARRIOSUNIDOS", false, 0, false),
    TEUSAQUILLO("TEUSAQUILLO", true, 4, false),
    USAQUEN("USAQUEN", true, 0, true),
    SUBA("SUBA", false, 10, false),
    ENGATIVA("ENGATIVA", true, 5, false);

    public final String value;
    public final boolean isThereWarehouse;
    public final int fleetSize;
    public final boolean enable;

    Localities(String value, boolean isThereWarehouse, int fleetSize, boolean enable) {
        this.value = value;
        this.isThereWarehouse = isThereWarehouse;
        this.fleetSize = fleetSize;
        this.enable = enable;
    }
}
