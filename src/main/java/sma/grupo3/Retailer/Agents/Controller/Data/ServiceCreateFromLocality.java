package sma.grupo3.Retailer.Agents.Controller.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class ServiceCreateFromLocality extends DataBESA {
    Localities locality;
    String service;

    public ServiceCreateFromLocality(Localities locality, String service) {
        this.locality = locality;
        this.service = service;
    }

    public Localities getLocality() {
        return locality;
    }

    public String getService() {
        return service;
    }
}
