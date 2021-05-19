package sma.grupo3.Retailer.Agents.Transporter.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class TransporterMovement extends DataBESA {
    Localities localityArrived;

    public TransporterMovement(Localities localityArrived) {
        this.localityArrived = localityArrived;
    }

    public Localities getLocalityArrived() {
        return localityArrived;
    }
}
