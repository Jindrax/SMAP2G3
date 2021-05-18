package sma.grupo3.Retailer.Agents.Warehouse.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.Agents.Customer.Data.CustomerOrderWrapper;

public class WarehouseAuctionBid extends DataBESA {
    String warehouseId;
    CustomerOrderWrapper customerOrderWrapper;
    boolean orderAvailable;
    double timeBid;

    public WarehouseAuctionBid(String warehouseId, CustomerOrderWrapper customerOrderWrapper, boolean orderAvailable) {
        this.warehouseId = warehouseId;
        this.customerOrderWrapper = customerOrderWrapper;
        this.orderAvailable = orderAvailable;
        this.timeBid = 0;
    }

    public WarehouseAuctionBid(String warehouseId, CustomerOrderWrapper customerOrderWrapper, boolean orderAvailable, double timeBid) {
        this.warehouseId = warehouseId;
        this.customerOrderWrapper = customerOrderWrapper;
        this.orderAvailable = orderAvailable;
        this.timeBid = timeBid;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public CustomerOrderWrapper getCustomerOrderWrapper() {
        return customerOrderWrapper;
    }

    public boolean isOrderAvailable() {
        return orderAvailable;
    }

    public double getTimeBid() {
        return timeBid;
    }
}
