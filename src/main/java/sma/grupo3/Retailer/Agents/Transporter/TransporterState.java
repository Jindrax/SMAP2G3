package sma.grupo3.Retailer.Agents.Transporter;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.Agents.Customer.Behavior.OnOrderDeliveredCustomerGuard;
import sma.grupo3.Retailer.Agents.Customer.Behavior.OnTransporterResponseCustomerGuard;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnOrderTransferredTransporterGuard;
import sma.grupo3.Retailer.Agents.Transporter.Data.CustomerOrderDelivery;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterCommandAuction;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;
import sma.grupo3.Retailer.SharedDomain.TransportCommandType;
import sma.grupo3.Retailer.Utils.GUI.LocalityDashboard;

import java.util.*;

public class TransporterState extends StateBESA {
    private Localities baseLocality;
    private Localities currentLocality;
    private final Set<CustomerOrder> currentLoad;
    private final List<TransportCommand> commandList;
    private final Timer currentMovement;
    private final double maxWeightCapacity;
    Map<TransportCommand, TransporterCommandAuction> transporterAuctions;
    private final LocalityDashboard dashboard;

    public TransporterState(Localities currentLocality, LocalityDashboard dashboard) {
        this.baseLocality = currentLocality;
        this.currentLocality = currentLocality;
        this.currentLoad = new HashSet<>();
        this.commandList = new ArrayList<>();
        this.currentMovement = new Timer();
        this.maxWeightCapacity = 30000;
        this.transporterAuctions = new Hashtable<>();
        this.dashboard = dashboard;
    }

    public List<TransportCommand> getCommandList() {
        return commandList;
    }

    public void addCommand(TransportCommand command) {
        this.commandList.add(command);
    }

    public EventBESA executeCommand(TransportCommand command) {
        EventBESA customerNotification;
        switch (command.getCommandType()) {
            case DELIVERY:
                this.currentLoad.remove(command.getOrder());
                customerNotification = new EventBESA(OnOrderDeliveredCustomerGuard.class.getName(), command.getOrder());
                break;
            case PICKUP:
                this.currentLoad.add(command.getOrder());
                CustomerOrderDelivery delivery = new CustomerOrderDelivery(ConnectionMap.getTimeFromTo(command.getDestination(), command.getOrder().getCustomerLocality()), command.getOrder());
                commandList.add(new TransportCommand(
                        command.getOrder().getCustomerLocality(),
                        command.getOrder(),
                        TransportCommandType.DELIVERY
                ));
                customerNotification = new EventBESA(OnTransporterResponseCustomerGuard.class.getName(), delivery);
                break;
            case TRANSFER:
                this.currentLoad.add(command.getOrder());
                commandList.add(new TransportCommand(
                        command.getOrder().getCustomerLocality(),
                        command.getOrder(),
                        TransportCommandType.DELIVERY
                ));
                customerNotification = new EventBESA(OnOrderTransferredTransporterGuard.class.getName(), command);
                break;
            default:
                customerNotification = new EventBESA("", null);
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

    public Localities getBaseLocality() {
        return baseLocality;
    }

    public Set<CustomerOrder> getCurrentLoad() {
        return currentLoad;
    }

    public double getMaxWeightCapacity() {
        return maxWeightCapacity;
    }

    public Map<TransportCommand, TransporterCommandAuction> getTransporterAuctions() {
        return transporterAuctions;
    }

    public void startTransporterAuction(TransportCommand transportCommand, TransporterCommandAuction auction) {
        this.transporterAuctions.put(transportCommand, auction);
    }

    public void finishTransporterAuction(CustomerOrder order) {
        this.transporterAuctions.remove(order);
    }

    public LocalityDashboard getDashboard() {
        return dashboard;
    }

    public void removeTransferCommandOnOrder(CustomerOrder order) {
        TransportCommand commandToBeRemoved = this.commandList.stream().filter(transportCommand -> transportCommand.getOrder() == order).findFirst().orElse(null);
        if (commandToBeRemoved != null) {
            this.commandList.remove(commandToBeRemoved);
        }
    }
}
