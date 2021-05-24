package sma.grupo3.Retailer.Agents.Transporter.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class TransporterMovement extends DataBESA {
    private final Localities localityArrived;
    private final long movementTimeCost;

    public TransporterMovement(Localities localityArrived, long movementTimeCost) {
        this.localityArrived = localityArrived;
        this.movementTimeCost = movementTimeCost;
    }

    public Localities getLocalityArrived() {
        return localityArrived;
    }

    public long getMovementTimeCost() {
        return movementTimeCost;
    }
}
