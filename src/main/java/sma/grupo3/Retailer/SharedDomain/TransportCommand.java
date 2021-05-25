package sma.grupo3.Retailer.SharedDomain;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.util.Objects;

public class TransportCommand extends DataBESA {
    Localities destination;
    CustomerOrder order;
    TransportCommandType commandType;
    boolean fulfilled;
    String originalOwner;

    public TransportCommand(Localities destination, CustomerOrder order, TransportCommandType commandType) {
        this.destination = destination;
        this.order = order;
        this.commandType = commandType;
        this.fulfilled = false;
        this.originalOwner = "";
    }

    public Localities getDestination() {
        return destination;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public TransportCommandType getCommandType() {
        return commandType;
    }

    public void fullFiled() {
        this.fulfilled = true;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void prepareToTransfer(Localities transferDestination, String originalOwner) {
        this.commandType = TransportCommandType.TRANSFER;
        this.destination = transferDestination;
        this.originalOwner = originalOwner;
    }

    public void cancelTransfer() {
        this.commandType = TransportCommandType.DELIVERY;
        this.destination = order.getCustomerLocality();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportCommand that = (TransportCommand) o;
        return destination == that.destination && Objects.equals(order, that.order) && commandType == that.commandType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, order, commandType, fulfilled);
    }

    @Override
    public String toString() {
        return "TransportCommand{" +
                "destination=" + destination +
                ", order=" + order +
                ", commandType=" + commandType +
                ", fulfilled=" + fulfilled +
                '}';
    }

    public String getOriginalOwner() {
        return originalOwner;
    }
}
