package sma.grupo3.Retailer.Agents.Controller.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.util.List;

public class ServiceUpdateFromLocality extends DataBESA {
    Localities fromLocality;
    String service;
    List<String> update;

    public ServiceUpdateFromLocality(Localities fromLocality, String service, List<String> update) {
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

    public List<String> getUpdate() {
        return update;
    }
}
