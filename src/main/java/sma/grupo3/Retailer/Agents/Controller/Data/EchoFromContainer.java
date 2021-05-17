package sma.grupo3.Retailer.Agents.Controller.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class EchoFromContainer extends DataBESA {
    private final Localities from;

    public EchoFromContainer(Localities from) {
        this.from = from;
    }

    public Localities getFrom() {
        return from;
    }
}
