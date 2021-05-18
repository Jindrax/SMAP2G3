package sma.grupo3.Retailer.Agents.Controller.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.util.Set;

public class ServiceUpdateFromLocality extends DataBESA {
    Localities fromLocality;
    String service;
    Set<String> update;

    public ServiceUpdateFromLocality(Localities fromLocality, String service, Set<String> update) {
        this.fromLocality = fromLocality;
        this.service = service;
        this.update = update;
    }

    public Localities getFromLocality() {
        return fromLocality;
    }

    public String getService() {
        return service;
    }

    public Set<String> getUpdate() {
        return update;
    }
}
