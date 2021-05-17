package sma.grupo3.Retailer.Agents.Controller;

import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.util.Map;

public class ControllerState extends StateBESA {
    private Localities locality;
    private Map<Localities, String> knownLocalities;

    public ControllerState(Localities locality, Map<Localities, String> knownLocalities) {
        this.locality = locality;
        this.knownLocalities = knownLocalities;
    }

    public Localities getLocality() {
        return locality;
    }

    public Map<Localities, String> getKnownLocalities() {
        return knownLocalities;
    }

    public void addLocality(Localities locality, String alias) {
        this.knownLocalities.put(locality, alias);
    }
}
