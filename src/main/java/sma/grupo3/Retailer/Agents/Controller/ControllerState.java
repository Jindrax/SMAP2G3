package sma.grupo3.Retailer.Agents.Controller;

import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.Agents.Transporter.TransporterAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class ControllerState extends StateBESA {
    private Localities locality;
    private Set<String> knownLocalities;
    private WarehouseAgent warehouse;
    private Map<String, TransporterAgent> fleet;

    public ControllerState(Localities locality, Set<String> knownLocalities) {
        this.locality = locality;
        this.knownLocalities = knownLocalities;
        this.fleet = new Hashtable<String, TransporterAgent>();
    }

    public Localities getLocality() {
        return locality;
    }

    public Set<String> getKnownLocalities() {
        return knownLocalities;
    }

    public void addLocality(String alias) {
        this.knownLocalities.add(alias);
    }

    public WarehouseAgent getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseAgent warehouse) {
        this.warehouse = warehouse;
    }

    public Map<String, TransporterAgent> getFleet() {
        return fleet;
    }

    public void addTransporterToFleet(TransporterAgent transporterAgent) {
        this.fleet.put(transporterAgent.getAid(), transporterAgent);
    }
}
