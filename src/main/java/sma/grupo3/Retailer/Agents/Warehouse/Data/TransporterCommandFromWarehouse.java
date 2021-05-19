package sma.grupo3.Retailer.Agents.Warehouse.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;

public class TransporterCommandFromWarehouse extends DataBESA {
    String warehouseId;
    TransportCommand command;

    public TransporterCommandFromWarehouse(String warehouseId, TransportCommand command) {
        this.warehouseId = warehouseId;
        this.command = command;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public TransportCommand getCommand() {
        return command;
    }
}
