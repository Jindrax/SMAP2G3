package sma.grupo3.Retailer.Agents.Transporter;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.Agents.Customer.Behavior.OnOrderDeliveredCustomerGuard;
import sma.grupo3.Retailer.Agents.Customer.Behavior.OnTransporterResponseCustomerGuard;
import sma.grupo3.Retailer.Agents.Transporter.Data.CustomerOrderDelivery;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;

import java.util.*;

public class TransporterState extends StateBESA {
    private Localities currentLocality;
    private final Set<CustomerOrder> currentLoad;
    private final List<TransportCommand> commandList;
    private final Timer currentMovement;

    public TransporterState(Localities currentLocality) {
        this.currentLocality = currentLocality;
        this.currentLoad = new HashSet<>();
        this.commandList = new ArrayList<>();
        this.currentMovement = new Timer();
    }

    public List<TransportCommand> getCommandList() {
        return commandList;
    }

    public void addCommand(TransportCommand command) {
        this.commandList.add(command);
    }

    public EventBESA executeCommand(TransportCommand command) {
        EventBESA customerNotification = null;
        if (command.isDelivery()) {
            this.currentLoad.remove(command.getOrder());
            CustomerOrderDelivery delivery = command.getDelivery();
            delivery.delivered();
            customerNotification = new EventBESA(OnOrderDeliveredCustomerGuard.class.getName(), delivery);
        }
        if (command.isPickup()) {
            CustomerOrder order = command.getOrder();
            this.currentLoad.add(command.getOrder());
            CustomerOrderDelivery delivery = new CustomerOrderDelivery(ConnectionMap.getTimeFromTo(command.getDestination(), order.getCustomerLocality()), order);
            commandList.add(new TransportCommand(
                    order.getCustomerLocality(),
                    order,
                    true,
                    delivery
            ));
            customerNotification = new EventBESA(OnTransporterResponseCustomerGuard.class.getName(), delivery);
        }
        command.fullFiled();
        return customerNotification;
    }

    public Timer getCurrentMovement() {
        return currentMovement;
    }

    public Localities getCurrentLocality() {
        return currentLocality;
    }

    public void setCurrentLocality(Localities currentLocality) {
        this.currentLocality = currentLocality;
    }

    public void scheduleMovement(TimerTask movementTask, long start) {
        this.currentMovement.schedule(movementTask, start);
    }
}
