package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterCommandFromWarehouse;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.SharedDomain.Annotations.TransporterGuard;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;

import java.util.Optional;

@TransporterGuard
public class OnCommandFromWarehouseTransporterGuard extends GuardBESA {

    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        TransporterCommandFromWarehouse command = (TransporterCommandFromWarehouse) eventBESA.getData();
        TransporterState state = (TransporterState) this.getAgent().getState();
        state.addCommand(command.getCommand());
    }

    @Deprecated
    private void commandToAnotherTransporter(TransportCommand command, AgentBESA agent, TransporterCommandFromWarehouse transporterCommand) throws ExceptionBESA {
        Optional<String> availableTransport = Services.getLocalityServiceProviders(command.getDestination(), StandardServices.TRANSPORTER.value).stream().findFirst();
        CustomerOrder order = command.getOrder();
        AgHandlerBESA customerHandler = agent.getAdmLocal().getHandlerByAlias(order.getCustomerAlias());
        if (availableTransport.isPresent()) {
            AgHandlerBESA transporterHandler = agent.getAdmLocal().getHandlerByAid(availableTransport.get());
            EventBESA commandToTransporter = new EventBESA(OnCommandFromWarehouseTransporterGuard.class.getName(), transporterCommand);
            transporterHandler.sendEvent(commandToTransporter);
        } else {
            Optional<String> globalTransport = Services.getGlobalServiceProviders(StandardServices.TRANSPORTER.value).stream().findFirst();
            if (globalTransport.isPresent()) {
                AgHandlerBESA transporterHandler = agent.getAdmLocal().getHandlerByAid(globalTransport.get());
                EventBESA commandToTransporter = new EventBESA(OnCommandFromWarehouseTransporterGuard.class.getName(), transporterCommand);
                transporterHandler.sendEvent(commandToTransporter);
            }
        }
    }
}
