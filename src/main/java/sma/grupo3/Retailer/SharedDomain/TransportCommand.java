package sma.grupo3.Retailer.SharedDomain;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.util.Objects;

public class TransportCommand extends DataBESA {
    Localities destination;
    CustomerOrder order;
    TransportCommandType commandType;
    boolean fulfilled;

    public TransportCommand(Localities destination, CustomerOrder order, TransportCommandType commandType) {
        this.destination = destination;
        this.order = order;
        this.commandType = commandType;
        this.fulfilled = false;
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

    public void prepareToTransfer() {
        this.commandType = TransportCommandType.TRANSFER;
    }

    public void cancelTransfer() {
        this.commandType = TransportCommandType.DELIVERY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportCommand that = (TransportCommand) o;
        return fulfilled == that.fulfilled && destination == that.destination && Objects.equals(order, that.order) && commandType == that.commandType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, order, commandType, fulfilled);
    }
}
