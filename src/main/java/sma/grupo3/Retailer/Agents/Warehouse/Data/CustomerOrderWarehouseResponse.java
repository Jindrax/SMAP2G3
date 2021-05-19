package sma.grupo3.Retailer.Agents.Warehouse.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

public class CustomerOrderWarehouseResponse extends DataBESA {
    String warehouseId;
    CustomerOrder order;
    boolean isCanceled;
    long timeToShip;

    public CustomerOrderWarehouseResponse(String warehouseId, CustomerOrder order, boolean isCanceled) {
        this.warehouseId = warehouseId;
        this.order = order;
        this.isCanceled = isCanceled;
        this.timeToShip = 0;
    }

    public CustomerOrderWarehouseResponse(String warehouseId, CustomerOrder order, boolean isCanceled, long timeToShip) {
        this.warehouseId = warehouseId;
        this.order = order;
        this.isCanceled = isCanceled;
        this.timeToShip = timeToShip;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public long getTimeToShip() {
        return timeToShip;
    }
}
