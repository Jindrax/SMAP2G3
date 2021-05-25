package sma.grupo3.Retailer.Agents.Controller;

import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.Agents.Transporter.TransporterAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.Utils.GUI.LocalityDashboard;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class ControllerState extends StateBESA {
    private final Localities locality;
    private final Set<String> knownLocalities;
    private WarehouseAgent warehouse;
    private final Map<String, TransporterAgent> fleet;
    private final LocalityDashboard dashboard;

    public ControllerState(Localities locality, Set<String> knownLocalities) {
        this.locality = locality;
        this.knownLocalities = knownLocalities;
        this.fleet = new Hashtable<String, TransporterAgent>();
        this.dashboard = new LocalityDashboard(locality.value);
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

    public LocalityDashboard getDashboard() {
        return dashboard;
    }
}
